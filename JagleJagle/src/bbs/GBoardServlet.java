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

import util.MyUtil;

/**
 * Servlet implementation class BoardServlet
 */
@WebServlet("/bbs/*")
public class GBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			process(req, resp);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		req.setCharacterEncoding("utf-8");
		MyUtil myUtil = new MyUtil();
		BoardDAO dao = new BoardDAO();
		String cp = req.getContextPath();
		int count = 0;
		// 해야 할 일
		String uri = req.getRequestURI();
		if (uri.indexOf("list.do") != -1) {
			String pageNum = req.getParameter("pageNum");
			int current_page = 1;
			if (pageNum != null)
				current_page = Integer.parseInt(pageNum);
			String searchKey = req.getParameter("searchKey");
			String searchValue = req.getParameter("searchValue");
			if (searchKey == null) {
				searchKey = "title";
				searchValue = "";
			}
			if (req.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "utf-8");
			}
			// 전체 데이터 개수 구하기
			int dataCount;
			if (searchValue.length() != 0)
				dataCount = dao.dataCount(searchKey, searchValue);
			else
				dataCount = dao.dataCount();
			int numPerPage = 10; // 한페이지 표시할 데이터 개수

			int total_page = myUtil.getPageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;
			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;
			List<BoardDTO> list;
			
			String kind =  req.getParameter("sort");
			
			if (searchValue.length() != 0)
				list = dao.listBoard(start, end, kind, searchKey, searchValue);
			else
				list = dao.listBoard(start, end, kind);
			// 리스트 글번호 만들기
			int listNum, n = 0;
			Iterator<BoardDTO> it = list.iterator();
			while (it.hasNext()) {
				BoardDTO dto = it.next();
				listNum = dataCount - (start + n - 1);
				dto.setListNum(listNum);
				n++;
			}
			String params = "";
			if (searchValue != null && searchValue.length() != 0) {
				searchValue = URLEncoder.encode(searchValue, "utf-8");
				params = "searchKey=" + searchKey + "&searchValue=" + searchValue;
			}
			// 페이징처리
			String listUrl = cp + "/bbs/list.do";
			String articleUrl = cp + "/bbs/article.do?pageNum=" + current_page;
			if (params.length() != 0) {
				listUrl += "?" + params;
				articleUrl = articleUrl + "&" + params;
			}
			String pageIndexList = myUtil.pageIndexList(current_page, total_page, listUrl);
			// list.jsp 파일에 데이터를 넘겨 준다.
			req.setAttribute("list", list); // 데이터
			req.setAttribute("pageIndexList", pageIndexList); // 페이지리스트
			req.setAttribute("pageNum", current_page); // 현페이지
			req.setAttribute("dataCount", dataCount); // 전체개수
			req.setAttribute("articleUrl", articleUrl);
			forward(req, resp, "/views/bbs/list.jsp");

		} else if (uri.indexOf("created.do") != -1) {
			// 입력 폼
			req.setAttribute("mode", "created");// ?? 이부분 뭐지?
			forward(req, resp, "/views/bbs/created.jsp");
		} else if (uri.indexOf("created_ok.do") != -1) {
			// 게시물 저장
			BoardDTO dto = new BoardDTO();
			ImgDTO Idto = new ImgDTO();
			ServletContext context = getServletContext();
			String uploadPath =context.getRealPath("image"); 
			String filename = null;
			String filePath = null;
			File file=null;
			int size = 10*1024*1024;    
			MultipartRequest multi  = null;
		
				
			multi = new MultipartRequest(req, uploadPath, size, "utf-8", new DefaultFileRenamePolicy());
			Vector vec = new Vector();		
            Enumeration files = multi.getFileNames();
            int i = 0;
            while(files.hasMoreElements()){
            	String name = (String)files.nextElement();
            	vec.add(name);
            }
            String[] formName= new String[vec.size()];
            vec.copyInto(formName);
            Arrays.sort(formName);
            filename = multi.getFilesystemName(formName[0]); 
        	System.out.println("filename : " + filename);
            
 
            filePath = "/image/" + filename; 
              
			dto.setGroup_date(multi.getParameter("group_date"));// 그룹 시간도 필요할듯
			dto.setTitle(multi.getParameter("title"));
			dto.setContents(multi.getParameter("contents"));
			System.out.println(multi.getParameter("limit_count"));
			dto.setLimit_count(Integer.parseInt(multi.getParameter("limit_count")));
			dto.setGroup_location(multi.getParameter("group_location"));
			dto.setIntroduce_comment(multi.getParameter("introduce_comment"));
			dto.setGroup_time(multi.getParameter("group_time"));
			Idto.setFile_path(filePath);
			dao.insertBoard(dto);
			dao.insertImg(Idto);
			resp.sendRedirect(cp + "/bbs/list.do");
			
		} else if (uri.indexOf("article.do") != -1) {
			// 게시물보기
			int idx = Integer.parseInt(req.getParameter("idx"));
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
			dao.updateHitCount(idx);
			// 게시물 가져오기
			BoardDTO dto = dao.readBoard(idx);
			ImgDTO Idto = dao.readImg(idx);
			if (dto == null) { // 게시물이 없으면
				resp.sendRedirect(cp + "/bbs/list.do?pageNum=" + pageNum);
				return;
			}
			int linesu = dto.getRegistration_date().split("\n").length;

			dto.setContents(dto.getContents().replaceAll("\n", "<br/>"));
			//댓글 
			int dataCount;
	         dataCount = dao.repleCount();
	   
	         //리스트
	         List<repleDTO> list;
	         list = dao.listReple(dto.getIdx());
	         
	         int listNum, n = 0;
	         Iterator<repleDTO> it = list.iterator();
	         while (it.hasNext()) {
	            repleDTO Rdto = it.next();
	            listNum = dataCount;
	            //dto.setIdx(listNum);
	            n++;
	         }

			String params = "pageNum=" + pageNum;
			if (!searchValue.equals("")) {
				params += "&searchKey=" + searchKey + "&searchValue=" + URLEncoder.encode(searchValue, "utf-8");
			}
			req.setAttribute("dto", dto);
	        req.setAttribute("Idto", Idto);
	        req.setAttribute("list", list);
	        req.setAttribute("dataCount", dataCount);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("linesu", linesu);
			req.setAttribute("params", params);
			forward(req, resp, "/views/bbs/article.jsp");
		} else if (uri.indexOf("reple_ok.do") != -1) {
	        
	         repleDTO dto = new repleDTO();
	         System.out.println("체크확인"+req.getParameter("replecontent"));
	         dto.setContents(req.getParameter("replecontent"));
	         dao.insertReple(dto, req.getParameter("b_idx"));
	         dao.minusView(Integer.parseInt(req.getParameter("b_idx")));
	         resp.sendRedirect(cp + "/bbs/article.do?pageNum="+req.getParameter("PageNum")+"&idx="+req.getParameter("b_idx"));
	    } else if (uri.indexOf("update.do") != -1) {
			// 수정 폼
			int idx = Integer.parseInt(req.getParameter("idx"));
			String pageNum = req.getParameter("pageNum");
			BoardDTO dto = dao.readBoard(idx);
			if (dto == null) { // 게시물이 없으면
				resp.sendRedirect(cp + "/bbs/list.do?pageNum=" + pageNum);
				return;
			}
			req.setAttribute("mode", "update");
			req.setAttribute("dto", dto);
			req.setAttribute("pageNum", pageNum);
			forward(req, resp, "/views/bbs/created.jsp");
		} else if (uri.indexOf("update_ok.do") != -1) {
			BoardDTO dto = new BoardDTO();
			
			ServletContext context = getServletContext();
			String uploadPath =context.getRealPath("image"); 
			String filename = null;
			String filePath = null;
			File file=null;
			int size = 10*1024*1024;    
			MultipartRequest multi  = null;
		
				
			multi = new MultipartRequest(req, uploadPath, size, "utf-8", new DefaultFileRenamePolicy());
			Vector vec = new Vector();		
            Enumeration files = multi.getFileNames();
            int i = 0;
            while(files.hasMoreElements()){
            	String name = (String)files.nextElement();
            	vec.add(name);
            }
            String[] formName= new String[vec.size()];
            vec.copyInto(formName);
            Arrays.sort(formName);
            filename = multi.getFilesystemName(formName[0]); 
        	System.out.println("filename : " + filename);
            
 
            filePath = "/image/" + filename; 
            
			String pageNum = multi.getParameter("pageNum");
			dto.setIdx(Integer.parseInt(multi.getParameter("idx")));
			
			dto.setGroup_date(multi.getParameter("group_date"));// 그룹 시간도 필요할듯
			dto.setTitle(multi.getParameter("title"));
			dto.setContents(multi.getParameter("contents"));
			System.out.println(multi.getParameter("limit_count"));
			dto.setLimit_count(Integer.parseInt(multi.getParameter("limit_count")));
			dto.setGroup_location(multi.getParameter("group_location"));
			dto.setIntroduce_comment(multi.getParameter("introduce_comment"));
			dto.setGroup_time(multi.getParameter("group_time"));
			//dto.setFile_path(filePath);
			dao.updateBoard(dto);
			//dao.insertImg(dto);
			//resp.sendRedirect(cp + "/bbs/list.do");
			resp.sendRedirect(cp + "/bbs/list.do?pageNum=" + pageNum);
		} else if (uri.indexOf("delete.do") != -1) {
			// 게시물삭제
			int idx = Integer.parseInt(req.getParameter("idx"));
			String pageNum = req.getParameter("pageNum");
			dao.deleteBoard(idx);
			resp.sendRedirect(cp + "/bbs/list.do?pageNum=" + pageNum);
		} else if (uri.indexOf("recommend.do") != -1) {
			// 추천수 증가
			int idx = Integer.parseInt(req.getParameter("idx"));
			String pageNum = req.getParameter("pageNum");
			dao.updateRecommend(idx);
			resp.sendRedirect(cp + "/bbs/article.do?pageNum=" + pageNum + "&idx="+ idx);
		} else if (uri.indexOf("participate.do") != -1){
			
			int idx = Integer.parseInt(req.getParameter("idx"));
			String pageNum = req.getParameter("pageNum");
			dao.updateParticipate(idx);
			count = dao.countParticipate(idx);
			req.setAttribute("count", count);
			resp.sendRedirect(cp + "/bbs/article.do?pageNum=" + pageNum + "&idx="+ idx);
		}
	}
}
