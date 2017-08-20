package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import DTO.BoardDTO;
import DTO.repleDTO;
import DTO.imgDTO;
import dbconfig.DBInit;

public class BoardDAO {
	
	private static int boardIdx;
	
	public String nickname(int u_idx) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		//UserDTO dto = new UserDTO();
		String result = null;
		PreparedStatement pstmt = null;
		String sql;
		sql = "SELECT nickname FROM user WHERE idx = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,u_idx);
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return result;
	}
	
	public int insertBoard(BoardDTO dto) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		//String sql = "select ifnull(max(serial), 0)+1 from board";
		String fields = "User_idx, Board_Pool_idx, registration_date,  title, price, contents";
		String sql;
		PreparedStatement pstmt = null;
		sql = "INSERT INTO board (" + fields + ") VALUES ( ?, ?, now(), ?, ?, ?)";
		try{

		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, 1);
		pstmt.setInt(2,dto.getBoard_Pool_idx() );
		pstmt.setString(3, dto.getTitle());
		pstmt.setInt(4, dto.getPrice());
		pstmt.setString(5, dto.getContents());
		result = pstmt.executeUpdate();
		pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return result;
	}
	public int insertReple(repleDTO dto,String b_idx, String Pool) throws ClassNotFoundException, SQLException {
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
		pstmt.setInt(3, Integer.parseInt(Pool));
		pstmt.setString(4, dto.getContents());
		result = pstmt.executeUpdate();
		pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return result;
	}
	
	public int insertImg(imgDTO dto) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		try{
		String fields = "path, Board_idx, Board_Board_Pool_idx, Board_User_idx";
		String sql = "INSERT INTO img (" + fields + ") VALUES ( ?, ?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
	
		pstmt.setString(1, dto.getPath());
		pstmt.setInt(2, dto.getBoard_idx());
		pstmt.setInt(3, dto.getBoard_Board_Pool_idx());
		pstmt.setInt(4, 1);
		result = pstmt.executeUpdate();
		pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return result;
	}
	
	public int dataCount(int Pool) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		sql = "SELECT IFNULL(COUNT(idx), 0) cnt FROM board WHERE Board_Pool_idx = ? ";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Pool);
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
	
	public int dataCount(String searchKey, String searchValue, int Pool) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try{
			if(searchKey.equalsIgnoreCase("registration_date"))
				sql = "SELECT IFNULL(COUNT(*), 0) FROM board WHERE DATE_FORMAT(registration_date, 'YYYY-MM-DD') = ? AND Board_Pool_idx = ? ";
			else
				sql = "SELECT IFNULL(COUNT(*), 0) FROM board WHERE "+ searchKey + " LIKE ? AND Board_Pool_idx = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"%"+searchValue+"%");
			pstmt.setInt(2, Pool);
			rs = pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);
			rs.close();
			pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return result;
	}
	private String sortString(String kind){
		StringBuffer sb = new StringBuffer();
		if("recommand".equals(kind)){
			sb.append("select b.idx, b.User_idx, b.title, b.recommand, b.price, b.view_count,b.registration_date, u.nickname ")
			.append("from board b, user u WHERE b.User_idx = u.idx AND b.Board_Pool_idx = ? AND b.delete_station = ? ")
			.append("ORDER BY recommand DESC limit ?,10;");
			return sb.toString();
		}else if("registration_date".equals(kind)){
			sb.append("select b.idx, b.User_idx, b.title, b.recommand, b.price, b.view_count,b.registration_date, u.nickname ")
			.append("from board b, user u WHERE b.User_idx = u.idx AND b.Board_Pool_idx = ? AND b.delete_station = ? ")
			.append("ORDER BY registration_date DESC limit ?,10;");
			return sb.toString();
		}else if("view_count".equals(kind)){
			sb.append("select b.idx, b.User_idx, b.title, b.recommand, b.price, b.view_count,b.registration_date, u.nickname ")
			.append("from board b, user u WHERE b.User_idx = u.idx AND b.Board_Pool_idx = ? AND b.delete_station = ? ")
			.append("ORDER BY view_count DESC limit ?,10;");
			return sb.toString();
		}else{
			sb.append("select b.idx, b.User_idx, b.title, b.recommand, b.price, b.view_count, b.registration_date, u.nickname ")
			.append("from board b, user u WHERE b.User_idx = u.idx AND b.Board_Pool_idx = ? AND b.delete_station = ? ")
			.append("ORDER BY idx DESC limit ?,10;");
			return sb.toString();
		}
	}
	
	public List<BoardDTO> listBoard(int start, int end, String kind, int Pool)throws ClassNotFoundException, SQLException{
		//Connection conn = dbconn.getConnection();
		Connection conn = DBInit.DB_Init();
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try{
			sql  = sortString(kind);
			pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, end);
			pstmt.setInt(1, Pool);
			pstmt.setString(2, "n");
			pstmt.setInt(3, start);
			
			rs = pstmt.executeQuery();
			int max = 0;
			while(rs.next()){
				BoardDTO dto = new BoardDTO();
				//System.out.println("idx :: " + rs.getInt("idx"));
				dto.setIdx(rs.getInt("idx"));
				dto.setUser_idx(rs.getInt("User_idx"));
				dto.setTitle(rs.getString("title"));
				dto.setRecommand(rs.getInt("recommand"));
				dto.setPrice(rs.getInt("price"));
				dto.setRegistration_date(rs.getString("registration_date"));
				dto.setView_count(rs.getInt("view_count"));
				dto.setNickname(rs.getString("nickname"));
				if(dto.getIdx() > max){
					max = dto.getIdx();
					idxSet(dto.getIdx());
				}
				list.add(dto);
			}
		rs.close();
		pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		System.out.println(idxGet());
		return list;
	}
	
	public List<BoardDTO> listBoard(int start, int end, String searchKey, String searchValue, int Pool) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		StringBuffer sb = new StringBuffer();
		try{
			
			sb.append("select b.idx, b.User_idx, b.title, b.recommand, b.price, b.view_count, u.nickname, "
					+ "b.registration_date from board b, user u ");
			if(searchKey.equalsIgnoreCase("registration_date"))
				sb.append("WHERE registration_date = ?  AND b.User_idx = u.idx AND b.Board_Pool_idx = ? AND b.delete_station = ? ");
			else
				sb.append("WHERE " + searchKey + " LIKE ? AND b.User_idx = u.idx AND b.Board_Pool_idx = ? AND b.delete_station = ? ");
			sb.append("ORDER BY idx DESC limit ?,10;");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, "%"+searchValue+"%");
		//	pstmt.setInt(2, end);
			pstmt.setInt(2, Pool);
			pstmt.setString(3, "n");
			pstmt.setInt(4, start);
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				BoardDTO dto = new BoardDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setUser_idx(rs.getInt("User_idx"));
				dto.setTitle(rs.getString("title"));
				dto.setRecommand(rs.getInt("recommand"));
				dto.setPrice(rs.getInt("price"));
				dto.setRegistration_date(rs.getString("registration_date"));
				dto.setView_count(rs.getInt("view_count"));
				dto.setNickname(rs.getString("nickname"));
				
				list.add(dto);
			}
		rs.close();
		pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return list;
	}
	
	public List<repleDTO> listReple(int b_idx)throws ClassNotFoundException, SQLException{
		Connection conn = DBInit.DB_Init();
		List<repleDTO> list = new ArrayList<repleDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try{
			sql = "SELECT r.idx, r.registration_date, r.contents, r.Board_User_idx, u.nickname FROM reple r, user u"
					+" where (r.Board_idx = ?) AND (r.Board_User_idx = u.idx) ORDER BY r.registration_date DESC";
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
				Rdto.setNickname(rs.getString("nickname"));
				
				list.add(Rdto);
			}
		rs.close();
		pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return list;
	}

	public List<BoardDTO> listPop(int start, int end)throws ClassNotFoundException, SQLException{
		//Connection conn = dbconn.getConnection();
		Connection conn = DBInit.DB_Init();
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try{
			sql  = "select idx, title from board ORDER BY recommand DESC limit ?,3;"; //WHERE date_format(registration_date, YYYY-MM-DD) between date(now())-7 and date(now())+1";
			pstmt = conn.prepareStatement(sql);
			//pstmt.setInt(1, end);
			pstmt.setInt(1, start);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				BoardDTO dto = new BoardDTO();
				//System.out.println("idx :: " + rs.getInt("idx"));
				dto.setIdx(rs.getInt("idx"));
				//dto.setUser_idx(rs.getInt("user_idx"));
				dto.setTitle(rs.getString("title"));
				//dto.setRecommand(rs.getInt("recommand"));
				//dto.setPrice(rs.getInt("price"));
				//dto.setRegistration_date(rs.getString("registration_date"));
				//dto.setView_count(rs.getInt("view_count"));
				
				list.add(dto);
			}
		rs.close();
		pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return list;
	}
	
	public BoardDTO readBoard(int idx)throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		BoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		sql = "SELECT b.idx, b.title, b.view_count, b.contents, b.recommand, b.registration_date, b.price, b.User_idx, u.nickname ";
		sql += " FROM board b, user u WHERE (b.User_idx = u.idx) AND (b.idx = ?) ";
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,idx);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				dto = new BoardDTO();
				dto.setPrice(rs.getInt("price"));
				dto.setPrice(rs.getInt("User_idx"));
				dto.setIdx(rs.getInt("idx"));
				dto.setTitle(rs.getString("title"));
				dto.setView_count(rs.getInt("view_count"));
				dto.setContents(rs.getString("contents"));
				dto.setRecommand(rs.getInt("recommand"));
				dto.setRegistration_date(rs.getString("registration_date"));
				dto.setNickname(rs.getString("nickname"));
			}
			rs.close();
			pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return dto;
	}
	
	public imgDTO readImg(int idx)throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		imgDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		//List<imgDTO> list = new ArrayList<imgDTO>();
		
		sql = "SELECT path FROM img WHERE Board_idx = ?";
		
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,idx);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				dto = new imgDTO();
				dto.setPath(rs.getString("path"));
			}
			rs.close();
			pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return dto;
	}
	
	public repleDTO readreple(int idx)throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		repleDTO dto = null;
		BoardDTO bdto = new BoardDTO();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		sql = "SELECT r.idx, r.contents, r.registration_date, r.Board_idx, r.Board_Board_Pool_idx, r.Board_User_idx, u.nickname ";
		sql += " FROM reple r, user u WHERE (r.idx=?) AND (r.Board_User_idx = u.idx)";
		
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
				dto.setBoard_Board_Pool_idx(2);
				dto.setBoard_User_idx(1);
				dto.setNickname(rs.getString("nickname"));
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
	
	public int updateRecommand(int idx) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		sql = "UPDATE board SET recommand = recommand+1 WHERE idx = ? ";

		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			result = pstmt.executeUpdate();
			
			pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		minusView(idx);
		
		return result;
	}
	
	public int updateView_count(int idx) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql = "UPDATE board SET view_count = view_count+1 WHERE idx = ?";
		
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
	
	public int updateBoard(BoardDTO dto) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		sql = "UPDATE board SET idx=?, title=?, price=?, contents=?";
		sql += " WHERE idx=?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getIdx());
			pstmt.setString(2, dto.getTitle());
			pstmt.setInt(3, dto.getPrice());
			pstmt.setString(4, dto.getContents());
			pstmt.setInt(5, dto.getIdx());
			result = pstmt.executeUpdate();
			
			pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return result;
	}
	
	public int deleteBoard(int idx) throws ClassNotFoundException, SQLException {
		Connection conn = DBInit.DB_Init();
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		sql = "UPDATE board SET delete_station = ? WHERE idx=? ";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "y");
			pstmt.setInt(2,  idx);
			result = pstmt.executeUpdate();
		
			pstmt.close();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return result;
	}
	
	
	public void idxSet(int Idx){
		this.boardIdx = Idx;
	}
	
	public int idxGet(){
		return boardIdx;
	}
}
