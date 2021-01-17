package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestBookVo;

public class BoardDao {
	
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
		
		//게시판 리스트
		public List<BoardVo> getList(){
			getConnection();
			List<BoardVo> boardList = new ArrayList<BoardVo>();
			
			try {
				// SQL문 준비/ 바인딩 / 실행
				String query = "";
				query += " SELECT b.no, ";
				query += " 		  b.title, ";
				query += " 		  u.name, ";
				query += " 		  b.hit, ";
				query += " 		  reg_date  ";			
				query += " FROM board b, users u";
				query += " where b.user_no = u.no ";
				
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				// 결과처리
				while (rs.next()) {
					int no = rs.getInt("no");
					String title = rs.getString("title");
					String name = rs.getString("name");
					int hit = rs.getInt("hit");
					String regDate = rs.getString("reg_date");
			
					BoardVo vo = new BoardVo(no, title, name, hit, regDate);
					boardList.add(vo);
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			// 자원정리
			close();

			return boardList;
			
		}
		
		//read
		public int read(BoardVo vo){
			getConnection();
			int count = 0;
			
			try {
				// SQL문 준비/ 바인딩 / 실행
				String query = "";
				query += " SELECT u.name, ";
				query += " 		  b.hit, ";
				query += " 		  b.reg_date, ";
				query += " 		  b.title, ";
				query += " 		  b.content ";
				query += " FROM board b, users u ";			
				query += " where b.user_no = u.no ";
				query += " and u.no = ? ";
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, vo.getNo());
				rs = pstmt.executeQuery();
				// 결과처리
				while (rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					int hit = rs.getInt("hit");
					String regDate = rs.getString("reg_date");
					String title = rs.getString("title");
					String content = rs.getString("content");
					
					vo = new BoardVo(no, name, hit, regDate, title, content);
					
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			// 자원정리
			close();

			return count;
		}
		
		
}
