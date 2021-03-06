package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 생성자
	// 메소드g.s
	// 메소드일반
	// DB접속
	private void getConnection() {

		try {
			// JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원 정리
	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public int insert(UserVo userVo) {
		int count = 0;
		getConnection();

		try {
			// SQL문 준비/ 바인딩 / 실행
			/*
			 * insert into users values(seq_users_no.nextval, ?, ?, ?, ?);
			 */
			String query = "";
			query += " insert into users ";
			query += " values(seq_users_no.nextval, ?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			count = pstmt.executeUpdate();

			// 결과처리
			System.out.println("(dao)" + count + "건이 저장");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		return count;
	}

	//로그인할때 세션 저장용
	public UserVo getUser(String id, String pw) {
		getConnection();
		UserVo userVo = null;
		try {
			// SQL문 준비/ 바인딩 / 실행
			
			String query = "";
			query += " SELECT no, ";
			query += " 		  name ";
			query += " FROM users ";
			query += " where id = ? ";
			query += " and password = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery(); // 쿼리문 실행

			// 결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				
				userVo = new UserVo(no, name); 
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return userVo;
	}

	// 사용자 정보 수정 메소드
	public int Update(UserVo vo) {
		getConnection();
		int count = 0;
		try {
			// SQL문 준비/ 바인딩 / 실행
			
			String query = "";
			query += " update users ";
			query += " set  password = ?,  ";
			query += " 		name = ?, ";
			query += " 		gender = ? ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setString(1, vo.getPassword());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getGender());
			pstmt.setInt(4, vo.getNo());

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 결과처리
			System.out.println("(dao)" + count + "건이 수정");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return count;
	}
	
	// 사용자 정보 가져오기(회원정보 수정시 사용)
	public UserVo getUser(int no) {
		getConnection();
		UserVo userVo = null;
		try {
			// SQL문 준비/ 바인딩 / 실행
			
			String query = "";
			query += " select no, ";
			query += "  	  id,	   ";
			query += "  	  password,	   ";
			query += " 	 	  name, ";
			query += " 		  gender ";
			query += " from users ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setInt(1, no);


			rs = pstmt.executeQuery(); // 쿼리문 실행

			// 결과처리
			while(rs.next()) {
				int uNo = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				userVo = new UserVo(uNo, id, password, name, gender); 
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return userVo;
	}
}
