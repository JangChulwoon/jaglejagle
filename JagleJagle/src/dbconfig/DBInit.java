package dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBInit {

	public static Connection DB_Init() throws SQLException, ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/jaglejagle?useSSL=false";
		String id = "root";
		String pw = "dv4503149";

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, id, pw);
		return conn;

	}

	public void init() {
		//System.out.println("DB");
		String value = "lusiue";

		String count = null;

		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DB_Init();

			pstmt = conn.prepareStatement("select id  from user where id = ?");
			pstmt.setString(1, value);

			System.out.println(pstmt.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getString("id");
			}

			System.out.println(count);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 실행을 돌린다든가 .. ~ 
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
				if (pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
