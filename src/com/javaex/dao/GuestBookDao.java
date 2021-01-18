package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestBookVo;

public class GuestBookDao {

	// 필드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

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
		
		//guestbook 삭제
		public int delete(GuestBookVo vo) {
			getConnection();
			int count = 0;
			try {
				// SQL문 준비/ 바인딩 / 실행
				String query = "";
				query += " delete from guestbook ";
				query += " where no = ? ";
				query += " and password = ? ";

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, vo.getNo());
				pstmt.setString(2, vo.getPassword());
				
				count = pstmt.executeUpdate();
				
				// 결과처리
				System.out.println("(dao)" + count + "건이 삭제");
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			// 자원정리
			close();

			return count;
		}
		
		//guestbook 저장
		public int insert(GuestBookVo guestBookVo) {
			getConnection();
			int count = 0;

			try {
				// SQL문 준비/ 바인딩 / 실행
				String query = "";
				query += " insert into guestbook ";
				query += " values(seq_no.nextval, ?, ?, ?, sysdate) ";

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, guestBookVo.getName());
				pstmt.setString(2, guestBookVo.getPassword());
				pstmt.setString(3, guestBookVo.getContent());

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
		
		//guestbook 리스트
		public List<GuestBookVo> getList(){
			getConnection();
			List<GuestBookVo> guestList = new ArrayList<GuestBookVo>();
			
			try {
				// SQL문 준비/ 바인딩 / 실행
				String query = "";
				query += " SELECT no, ";
				query += " 		  name, ";
				query += " 		  password, ";
				query += " 		  content, ";
				query += " 		  to_char(reg_date, 'yyyy-mm-dd hh24:mi:ss') reg_date ";
				query += " FROM guestbook ";
				
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				// 결과처리
				while (rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					String password = rs.getString("password");
					String content = rs.getString("content");
					String regDate = rs.getString("reg_date");
					
					GuestBookVo vo = new GuestBookVo(no, name, password, content, regDate);
					guestList.add(vo);
					
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			// 자원정리
			close();

			return guestList;
			
		}
		
}
