package bbs;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DTO.BoardDTO;
import DTO.imgDTO;
import dao.BoardDAO;
import util.MyUtil;

/**
 * Servlet implementation class BoardServlet
 */
@WebServlet("/bbs/yy/*")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// public BoardServlet() {
	// TODO Auto-generated constructor stub
	// }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			process(req, resp);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			process(req, resp);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, ClassNotFoundException, SQLException {

		req.setCharacterEncoding("UTF-8");

		MyUtil myUtil = new MyUtil();
		BoardDAO dao = new BoardDAO();

		String cp = req.getContextPath();

		String uri = req.getRequestURI();
		// System.out.println("url ::" +uri);
		if (uri.indexOf("list.do") != -1) {
			String Pool = req.getParameter("Pool");
			int currentPool = 2;
			if (Pool != null)
				currentPool = Integer.parseInt(Pool);

			// 게시물 리스트
			String pageNum = req.getParameter("pageNum");
			int current_page = 1;
			if (pageNum != null)
				current_page = Integer.parseInt(pageNum);

			String searchKey = req.getParameter("searchKey");
			String searchValue = req.getParameter("searchValue");
			// System.out.println("search value : " + searchValue);
			if (searchKey == null) {
				searchKey = "title";
				searchValue = "";
			}
			if (req.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
			// 전체 데이터 개수 구하기
			int dataCount;
			if (searchValue.length() != 0) {
				dataCount = dao.dataCount(searchKey, searchValue, currentPool);
			} else
				dataCount = dao.dataCount(currentPool);

			int numPerPage = 10;// 한 페이지에 표시할 데이터 개수
			int total_page = myUtil.getPageCount(numPerPage, dataCount);

			if (current_page > total_page)
				current_page = total_page;

			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;

			// //주간 인기글
			// List<BoardDTO> popList;
			//
			// String Pkind = req.getParameter("sort");
			// //System.out.println("Test :: "+Pkind);
			//
			// popList = dao.listPop(start - 1, end);
			// int rank = 1;
			// // 리스트 글번호 만들기
			// int PlistNum, pNum = 0;
			// Iterator<BoardDTO> Pit = popList.iterator();
			// while (Pit.hasNext()) {
			// BoardDTO dto = Pit.next();
			// PlistNum = dataCount - (start + pNum - 1);
			// //dto.setIdx(listNum);
			// pNum++;
			// }

			// 리스트
			List<BoardDTO> list;

			String kind = req.getParameter("sort");
			// System.out.println("Test :: "+kind);

			if (searchValue.length() != 0)
				// list = dao.listBoard(start, end, searchKey, searchValue);
				list = dao.listBoard(start - 1, end, searchKey, searchValue, currentPool);

			else
				// list = dao.listBoard(start - 1, end);
				list = dao.listBoard(start - 1, end, kind, currentPool);
			// 리스트 글번호 만들기
			int listNum, n = 0;
			Iterator<BoardDTO> it = list.iterator();
			while (it.hasNext()) {
				BoardDTO dto = it.next();
				listNum = dataCount - (start + n - 1);
				// dto.setIdx(listNum);
				n++;
			}
			// 페이징 처리
			String params = "";
			if (searchValue != null && searchValue.length() != 0) {
				searchValue = URLEncoder.encode(searchValue, "utf-8");
				params = "searchKey =" + searchKey + "&searchValue=" + searchValue;
			}

			String listUrl = cp + "/bbs/list.do" + "?" + currentPool;
			String articleUrl = cp + "/bbs/article.do?Pool=" + currentPool + "&pageNum=" + current_page;
			// String articleUrl = cp+"/bbs/article.do?pageNum="+current_page;
			if (params.length() != 0) {
				listUrl += "?" + params;
				articleUrl = articleUrl + "&" + params;
			}

			String pageIndexList = myUtil.pageIndexList(current_page, total_page, listUrl);

			req.setAttribute("list", list);
			req.setAttribute("pageIndexList", pageIndexList);
			// req.setAttribute("idx", idx);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("params", params);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("currentPool", currentPool);

			forward(req, resp, "/view/market_bbs/list.jsp");

		} else if (uri.indexOf("created.do") != -1) {
			// 입력 폼
			int Pool = Integer.parseInt(req.getParameter("Pool"));
			req.setAttribute("mode", "created");
			req.setAttribute("Pool", Pool);
			forward(req, resp, "/view/market_bbs/created.jsp");
		} else if (uri.indexOf("created_ok.do") != -1) {
			// System.out.println("created !!!!!");
			// System.out.println(req.getParameter("Pool"));
			// int Pool = Integer.parseInt(req.getParameter("Pool"));
			BoardDTO dto = new BoardDTO();
			imgDTO idto = new imgDTO();

			String filename = "";
			String filePath = "";
			File file = null;
			ServletContext context = getServletContext();
			String uploadPath = context.getRealPath("image");
			int size = 10 * 1024 * 1024;
			MultipartRequest multi = null;

			multi = new MultipartRequest(req, uploadPath, size, "utf-8", new DefaultFileRenamePolicy());
			int Pool = Integer.parseInt(multi.getParameter("Pool"));
			// System.out.println("Pool : "+Pool);
			Vector vec = new Vector();
			Enumeration files = multi.getFileNames();
			int i = 0;
			while (files.hasMoreElements()) {
				String name = (String) files.nextElement();
				vec.add(name);
			}

			String[] formName = new String[vec.size()];
			vec.copyInto(formName);
			Arrays.sort(formName);
			filename = multi.getFilesystemName(formName[0]);
			System.out.println("filename : " + filename);

			filePath = "/image/" + filename;
			// filePath = uploadPath + "\\" + filename;

			dto.setTitle(multi.getParameter("title"));
			dto.setContents(multi.getParameter("contents"));
			if (Pool == 2)
				dto.setPrice(Integer.parseInt(multi.getParameter("price")));
			dto.setBoard_Pool_idx(Pool);

			dao.insertBoard(dto);

			idto.setPath(filePath);
			idto.setBoard_idx(dao.idxGet() + 1);
			idto.setBoard_Board_Pool_idx(Pool);
			idto.setBoard_User_idx(1);
			System.out.println(
					idto.getBoard_idx() + " " + idto.getBoard_Board_Pool_idx() + " " + idto.getBoard_User_idx());

			dao.insertImg(idto);
			resp.sendRedirect(cp + "/bbs/list.do?Pool=" + Pool);
		} else if (uri.indexOf("reple_ok.do") != -1) {
			// System.out.println("created !!!!!");
			// req.setCharacterEncoding("utf-8");
			repleDTO dto = new repleDTO();
			// System.out.println("@@@ = " + req.getAttribute("replecontent"));
			System.out.println("체크확인" + req.getParameter("replecontent"));
			dto.setContents(req.getParameter("replecontent"));
			dao.insertReple(dto, req.getParameter("b_idx"), req.getParameter("Pool"));
			dao.minusView(Integer.parseInt(req.getParameter("b_idx")));
			resp.sendRedirect(cp + "/bbs/article.do?Pool=" + req.getParameter("Pool") + "&pageNum="
					+ req.getParameter("PageNum") + "&idx=" + req.getParameter("b_idx"));
		} else if (uri.indexOf("article.do") != -1) {
			// 게시물 보기
			int idx = Integer.parseInt(req.getParameter("idx"));
			int Pool = Integer.parseInt(req.getParameter("Pool"));
			String pageNum = req.getParameter("pageNum");
			String searchKey = req.getParameter("searchKey");
			String searchValue = req.getParameter("searchValue");
			if (searchKey == null) {
				searchKey = "title";
				searchValue = "";
			}
			if (req.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "utf-8");
			}
			// 조회수 증가
			dao.updateView_count(idx);
			// 게시물 가져오기
			BoardDTO dto = dao.readBoard(idx);
			imgDTO Idto = dao.readImg(idx);
			if (dto == null) {
				resp.sendRedirect(cp + "/bbs/list.do?pageNum=" + pageNum);
				System.out.println("체크체크");
				return;
			}

			dto.setContents(dto.getContents().replaceAll("\n", "</br>"));

			// 댓글

			if (req.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
			int dataCount;
			dataCount = dao.repleCount();

			// 리스트
			List<repleDTO> list;
			list = dao.listReple(dto.getIdx());

			int listNum, n = 0;
			Iterator<repleDTO> it = list.iterator();
			while (it.hasNext()) {
				repleDTO Rdto = it.next();
				listNum = dataCount;
				// dto.setIdx(listNum);
				n++;
			}

			String params = "pageNum=" + pageNum;
			if (!searchValue.equals("")) {
				params += "&searchKey=" + searchKey + "&searchValue" + URLEncoder.encode(searchValue, "utf-8");
			}

			req.setAttribute("Pool", Pool);
			req.setAttribute("dto", dto);
			req.setAttribute("Idto", Idto);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("params", params);
			req.setAttribute("list", list);
			// req.setAttribute("Ilist", Ilist);
			req.setAttribute("dataCount", dataCount);

			forward(req, resp, "/view/market_bbs/article.jsp");
		} else if (uri.indexOf("update.do") != -1) {
			// 수정폼
			System.out.println("update!");
			int num = Integer.parseInt(req.getParameter("idx"));
			String pageNum = req.getParameter("pageNum");// ******

			BoardDTO dto = dao.readBoard(num);
			if (dto == null) {
				resp.sendRedirect(cp + "/bbs/list.do?pageNum=" + pageNum);
				return;
			}

			req.setAttribute("mode", "update");
			req.setAttribute("dto", dto);
			req.setAttribute("pageNum", pageNum);

			forward(req, resp, "/view/market_bbs/created.jsp");
		} else if (uri.indexOf("update_ok.do") != -1) {

			BoardDTO dto = new BoardDTO();
			String filename;
			String uploadPath = "C:\\Users\\cokto\\Desktop\\crossit";
			int size = 10 * 1024 * 1024;
			MultipartRequest multi = null;
			// img
			try {
				// 파일 업로드. 폼에서 가져온 인자값을 얻기 위해request 객체 전달,
				// 업로드 경로, 파일 최대 크기, 한글처리, 파일 중복처리
				multi = new MultipartRequest(req, uploadPath, size, "utf-8", new DefaultFileRenamePolicy());
				// 업로드한 파일들을 Enumeration 타입으로 반환
				// Enumeration형은 데이터를 뽑아올때 유용한 인터페이스 Enumeration객체는 java.util
				// 팩키지에 정의 되어있으므로
				// java.util.Enumeration을 import 시켜야 한다.
				Enumeration files = multi.getFileNames();
				// 업로드한 파일들의 이름을 얻어옴
				String file = (String) files.nextElement();
				filename = multi.getFilesystemName(file);
			} catch (Exception e) {
				// 예외처리
				e.printStackTrace();
			}

			// BoardDTO dto = new BoardDTO();
			String idx = multi.getParameter("idx");

			dto.setIdx(Integer.parseInt(multi.getParameter("idx")));
			dto.setTitle(multi.getParameter("title"));
			dto.setContents(multi.getParameter("contents"));
			dto.setPrice(Integer.parseInt(multi.getParameter("price")));
			dao.updateBoard(dto);
			resp.sendRedirect(cp + "/bbs/list.do?idx=" + idx);
		} else if (uri.indexOf("delete.do") != -1) {
			int num = Integer.parseInt(req.getParameter("idx"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));// **********
			// String Pool = req.getParameter("Pool");
			int Pool = Integer.parseInt(req.getParameter("Pool"));
			// System.out.println(Pool);
			dao.deleteBoard(num);

			resp.sendRedirect(cp + "/bbs/list.do?Pool=" + Pool + "&PageNum=" + pageNum);
		} else if (uri.indexOf("recommand.do") != -1) {
			int num = Integer.parseInt(req.getParameter("idx"));
			String idx = req.getParameter("idx");// **********
			String pageNum = req.getParameter("pageNum");
			int Pool = Integer.parseInt(req.getParameter("Pool"));

			dao.updateRecommand(num);

			resp.sendRedirect(cp + "/bbs/article.do?Pool=" + Pool + "&pageNum=" + pageNum + "&idx=" + idx);
		}

	}
}