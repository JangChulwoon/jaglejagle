package bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dbconfig.DBInit;

public class BoardDAO {
	public int insertBoard(BoardDTO dto) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		String fields = "User_idx, Board_Pool_idx, group_location, title, group_date, group_time , introduce_comment, contents, limit_count, registration_date";
		sql = "INSERT INTO board (" + fields + ") VALUES (1,1,?, ?, ?, ?, ?, ?,?, now())";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getGroup_location());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getGroup_date());
			pstmt.setString(4, dto.getGroup_time());
			pstmt.setString(5, dto.getIntroduce_comment());
			pstmt.setString(6, dto.getContents());
			pstmt.setInt(7, dto.getLimit_count());

			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return result;
	}

	public int insertImg(ImgDTO dto) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		ResultSet rs = null;
		int index = 0;
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		sql = "SELECT MAX(idx) as num FROM board";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				index = rs.getInt(1);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		sql = "insert into img (path, Board_idx, Board_Board_Pool_idx, Board_User_idx) values (?,?,1,1)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getFile_path());
			pstmt.setInt(2, index);

			result = pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return result;
	}

	public int dataCount() throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		sql = "SELECT ifnull(COUNT(idx), 0) cnt FROM board";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1); // result = rs.getInt("cnt");
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return result;
	}

	// 검색 모드에서 전체의 개수 구하기
	public int dataCount(String searchKey, String searchValue) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			if (searchKey.equalsIgnoreCase("registration_date"))
				sql = "SELECT ifnull(COUNT(*), 0) FROM board WHERE DATE_FORMAT(registraion_date,'YYYY-MM-DD') = ? ";
			else
				sql = "SELECT ifnull(COUNT(*), 0) FROM board WHERE " + searchKey + " LIKE ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+searchValue+"%");
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return result;
	}

	// 리스트
	public List<BoardDTO> listBoard(int start, int end, String kind) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = sortString(kind);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start-1);
			System.out.println(start);
			System.out.println(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setNickname(rs.getString("nickname"));
				dto.setTitle(rs.getString("title"));
				dto.setView_count(rs.getInt("view_count"));
				//dto.setRegistration_date(rs.getString("registration_date"));
				dto.setGroup_date(rs.getString("group_date"));
				dto.setGroup_location(rs.getString("group_location"));
				dto.setRecommend(rs.getInt("recommend"));
				dto.setLimit_count(rs.getInt("limit_count"));

				list.add(dto);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return list;
	}

	// 검색에서 리스트
	public List<BoardDTO> listBoard(int start, int end, String kind, String searchKey, String searchValue)
			throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = sortStringSearch(kind, searchKey, searchValue);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + searchValue + "%");
			pstmt.setInt(2, start-1);
			System.out.println(pstmt.toString());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setNickname(rs.getString("nickname"));
				dto.setTitle(rs.getString("title"));
				dto.setView_count(rs.getInt("view_count"));
				// dto.setRegistration_date(rs.getString("registration_date"));
				dto.setGroup_date(rs.getString("group_date"));
				dto.setGroup_location(rs.getString("group_location"));
				dto.setRecommend(rs.getInt("recommend"));
				dto.setLimit_count(rs.getInt("limit_count"));
				list.add(dto);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return list;
	}
	public ImgDTO readImg(int idx) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		ImgDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		sql = "select b.idx, path from board b, img i where i.Board_idx = b.idx";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new ImgDTO();
				dto.setFile_path(rs.getString("path"));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return dto;
				
	}
	public BoardDTO readBoard(int idx) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		BoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		sql = "SELECT b.idx, nickname, title, contents, recommend, introduce_comment, ";
		sql += " view_count, DATE_FORMAT(b.registration_date, '%Y-%m-%d') registration_date, limit_count";
		sql += " from board b , user u WHERE User_idx = u.idx and b.idx = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new BoardDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setNickname(rs.getString("nickname"));
				dto.setTitle(rs.getString("title"));
				dto.setContents(rs.getString("contents"));
				dto.setView_count(rs.getInt("view_count"));
				dto.setRegistration_date(rs.getString("registration_date"));
				dto.setRecommend(rs.getInt("recommend"));
				dto.setIntroduce_comment(rs.getString("introduce_comment"));
				dto.setLimit_count(rs.getInt("limit_count"));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return dto;
	}

	
	public int updateHitCount(int idx) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		sql = "UPDATE board SET view_count=view_count+1 WHERE idx=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return result;
	}

	public int updateRecommend(int idx) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		sql = "UPDATE board SET recommend = recommend + 1, view_count = view_count - 1 WHERE idx=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return result;
	}

	public int updateParticipate(int idx) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		sql = "insert into join_group (User_idx, registration_date, Board_idx, Board_Board_Pool_idx, Board_User_idx) values (2,now(),?,1,1)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}

		return result;
	}

	public int countParticipate(int idx) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		sql = "select count (User_idx) from join_group where Board_idx = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return result;
	}

	public int updateBoard(BoardDTO dto) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;

		String sql;
		sql = "UPDATE board SET group_location=?, title=?, group_date=?, group_time=?, limit_count=?, introduce_comment=?, contents=?";
		sql += " WHERE idx=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getGroup_location());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getGroup_date());
			pstmt.setString(4, dto.getGroup_time());
			pstmt.setInt(5, dto.getLimit_count());
			pstmt.setString(6, dto.getIntroduce_comment());
			pstmt.setString(7, dto.getContents());
			pstmt.setInt(8, dto.getIdx());
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return result;
	}

	public int deleteBoard(int idx) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		sql = "DELETE FROM board WHERE idx=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally{
			 /*This block should be added to your code
			  * You need to release the resources like connections
			  */
			 if(conn!=null)
			  conn.close();
			}
		return result;
	}

	private String sortString(String kind) {
		StringBuffer sb = new StringBuffer();
		
		if ("recommend".equals(kind)){
			sb.append("select b.idx, title, view_count, DATE_FORMAT(group_date, '%Y-%m-%d') group_date, group_location, limit_count, recommend, nickname ")
					.append("from board b , user u ").append("WHERE User_idx = u.idx ").append("ORDER BY recommend DESC limit ?,10");
			return sb.toString();
		} else if ("registration_date".equals(kind)) {
			sb.append("select b.idx, title, view_count, DATE_FORMAT(group_date, '%Y-%m-%d') group_date, group_location, limit_count, recommend, nickname ")
					.append("from board b , user u ").append("WHERE User_idx = u.idx ").append("ORDER BY b.registration_date DESC limit ?,10");
			return sb.toString();
		} else if ("view_count".equals(kind)) {
			sb.append("select b.idx, title, view_count, DATE_FORMAT(group_date, '%Y-%m-%d') group_date, group_location, limit_count, recommend, nickname ")
					.append("from board b , user u ").append("WHERE User_idx = u.idx ").append("ORDER BY view_count DESC limit ?,10");
			return sb.toString();
		} else {
			sb.append("select b.idx, title, view_count, DATE_FORMAT(group_date, '%Y-%m-%d') group_date, group_location, limit_count, recommend, nickname ")
					.append("from board b , user u ").append("WHERE User_idx = u.idx ").append("ORDER BY b.idx DESC limit ?,10");
			return sb.toString();
		}
	}
	private String sortStringSearch(String kind, String searchKey, String searchValue) {
		StringBuffer sb = new StringBuffer();
		if ("recommand".equals(kind)){
			sb.append("select b.idx, title, view_count, DATE_FORMAT(group_date, '%Y-%m-%d') group_date, group_location, limit_count, recommend, nickname ").append("from board b , user u ");
			if (searchKey.equalsIgnoreCase("registration_date"))
				sb.append("WHERE DATE_FORMAT(registration_date, 'YYYY-MM-DD') = ? ");
			else
				sb.append("WHERE " + searchKey + " LIKE ? ");	
			sb.append("and User_idx = u.idx ").append("ORDER BY recommend DESC limit ?,10;");
			return sb.toString();
		} else if ("registration_date".equals(kind)) {
			sb.append("select b.idx, title, view_count, DATE_FORMAT(group_date, '%Y-%m-%d') group_date, group_location, limit_count, recommend, nickname ").append("from board b , user u ");
			if (searchKey.equalsIgnoreCase("registration_date"))
				sb.append("WHERE DATE_FORMAT(registration_date, 'YYYY-MM-DD') = ? ");
			else
				sb.append("WHERE " + searchKey + " LIKE ? ");
			sb.append("and User_idx = u.idx ").append("ORDER BY registration_date DESC limit ?,10;");
			return sb.toString();
		} else if ("view_count".equals(kind)) {
			sb.append("select b.idx, title, view_count, DATE_FORMAT(group_date, '%Y-%m-%d') group_date, group_location, limit_count, recommend, nickname ").append("from board b , user u ");
			if (searchKey.equalsIgnoreCase("registration_date"))
				sb.append("WHERE DATE_FORMAT(registration_date, 'YYYY-MM-DD') = ? ");
			else
				sb.append("WHERE " + searchKey + " LIKE ? ");
			sb.append("and User_idx = u.idx ").append("ORDER BY view_count DESC limit ?,10;");
			return sb.toString();
		} else {
			sb.append("select b.idx, title, view_count, DATE_FORMAT(group_date, '%Y-%m-%d') group_date, group_location, limit_count, recommend, nickname ").append("from board b , user u ");
			if (searchKey.equalsIgnoreCase("registration_date"))
				sb.append("WHERE DATE_FORMAT(registration_date, 'YYYY-MM-DD') = ? ");
			else
				sb.append("WHERE " + searchKey + " LIKE ? ");
			sb.append("and User_idx = u.idx ").append("ORDER BY b.idx DESC limit ?,10;");
			return sb.toString();
		}
	}
	public int insertReple(repleDTO dto,String b_idx) throws ClassNotFoundException, SQLException {
	      Connection conn = DBInit.DB_Init();
	      int result = 0;
	      
	      String fields = "Board_idx, Board_User_idx, Board_Board_Pool_idx, registration_date, contents ";
	      String sql;
	      
	      PreparedStatement pstmt = null;
	      sql = "INSERT INTO reple (" + fields + ") VALUES ( ?, ?, ?, now(), ?)";
	      try{
	      System.out.println(dto.getContents());
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setInt(1, Integer.parseInt(b_idx));
	      pstmt.setInt(2, 1);
	      pstmt.setInt(3, 1);
	      pstmt.setString(4, dto.getContents());
	      result = pstmt.executeUpdate();
	      pstmt.close();
	      }catch(Exception e){
	         System.out.println(e.toString());
	      }
	      return result;
	   }

	public int repleCount() throws ClassNotFoundException, SQLException {
	      Connection conn = DBInit.DB_Init();
	      int result = 0;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql;
	      sql = "SELECT IFNULL(COUNT(idx), 0) cnt FROM reple";
	      try{
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         if(rs.next())
	            result = rs.getInt(1);
	         rs.close();
	         pstmt.close();
	      }catch(Exception e){
	         System.out.println(e.toString());
	      }
	      return result;
	   }

	public List<repleDTO> listReple(int b_idx)throws ClassNotFoundException, SQLException{
	      Connection conn = DBInit.DB_Init();
	      List<repleDTO> list = new ArrayList<repleDTO>();
	      
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql = null;
	      try{
	         sql = "SELECT idx, registration_date, contents, Board_User_idx FROM reple where Board_idx = ? ORDER BY registration_date DESC";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, b_idx);
	         //pstmt.setInt(1, end);
	         //pstmt.setInt(1, start);
	         
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()){
	            repleDTO Rdto = new repleDTO();
	            //System.out.println("idx :: " + rs.getInt("idx"));            
	            Rdto.setIdx(rs.getInt("idx"));
	            Rdto.setRegistration_date(rs.getString("registration_date"));
	            Rdto.setContents(rs.getString("contents"));
	            Rdto.setBoard_User_idx(rs.getInt("Board_User_idx"));
	            
	            list.add(Rdto);
	         }
	      rs.close();
	      pstmt.close();
	      }catch(Exception e){
	         System.out.println(e.toString());
	      }
	      return list;
	   }

	public repleDTO readreple(int idx)throws ClassNotFoundException, SQLException {
	      Connection conn = DBInit.DB_Init();
	      repleDTO dto = null;
	      BoardDTO bdto = new BoardDTO();
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql;
	      
	      sql = "SELECT idx, contents, registration_date, Board_idx, Board_Board_Pool_idx, Board_User_idx ";
	      sql += " FROM reple WHERE idx=?";
	      
	      try{
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1,idx);
	         
	         rs = pstmt.executeQuery();
	         dto = new repleDTO();
	         if(rs.next()){
	            //dto = new repleDTO();
	            
	            dto.setIdx(rs.getInt("idx"));
	            dto.setContents(rs.getString("contents"));
	            dto.setRegistration_date(rs.getString("registration_date"));
	            dto.setBoard_idx(bdto.getIdx());
	            dto.setBoard_Board_Pool_idx(1);
	            dto.setBoard_User_idx(1);
	            System.out.println(dto.getIdx()+dto.getContents());
	         }
	         rs.close();
	         pstmt.close();
	      }catch(Exception e){
	         System.out.println(e.toString());
	      }
	      return dto;
	   }
	
	public int minusView(int idx)throws ClassNotFoundException, SQLException{
	      Connection conn = DBInit.DB_Init();
	      int result = 0;
	      PreparedStatement pstmt = null;
	      String sql;
	      sql = "UPDATE board SET view_count = view_count-1 WHERE idx = ?";
	      try{
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, idx);
	         result = pstmt.executeUpdate();
	         
	         pstmt.close();
	      }catch(Exception e){
	         System.out.println(e.toString());
	      }
	      return result;
	   }

}