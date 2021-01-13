package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/User")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("UserController");

		String action = request.getParameter("action");

		if ("joinForm".equals(action)) {
			System.out.println("회원가입용");
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("join".equals(action)) {
			System.out.println("회원가입");

			// dao --> insert() 저장

			// 파라미터 값 꺼내기
			// http://localhost:8088/mysite2/User?uid=jus&pw=1234&uname=서웅기&gender=male&action=join
			String id = request.getParameter("uid");
			String password = request.getParameter("pw");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");

			// vo 로 묶기 -> vo 만들기 생성자 추가
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo.toString());

			// dao 클래스 insert(vo) 사용 --> 저장-->회원가입
			UserDao userDao = new UserDao();
			userDao.insert(userVo);

			// 포워드 --> joinOk.jsp
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinOk.jsp");

		} else if ("loginForm".equals(action)) {
			System.out.println("로그인 폼");
			WebUtil.forword(request, response, "/WEB-INF/views/user/loginForm.jsp");

		} else if ("login".equals(action)) {
			System.out.println("로그인");

			// 파라미터 id, pw
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");

			// dao --> getUser();
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, pw);
			System.out.println(authVo); // id pw --> no, name

			if (authVo == null) {
				System.out.println("로그인 실패");
				// 리다이렉트 --> 로그인폼
				WebUtil.redirect(request, response, "/mysite2/User?action=loginForm&result=fail");

			} else {
				System.out.println("로그인 성공");
				// 성공일때
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo);

				WebUtil.redirect(request, response, "/mysite2/Main");

			}

		} else if ("logout".equals(action)) {
			System.out.println("로그아웃");

			// 세션영역에 있는 vo를 삭제해야함
			HttpSession session = request.getSession();
			session.invalidate();

			WebUtil.redirect(request, response, "/mysite2/Main");

		} else if ("modifyForm".equals(action)) {
			System.out.println("회원정보 수정 폼");
			
			//세션에 있는 no
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			int no = authUser.getNo();
			
			//회원정보 가져오기
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(no);
			
			System.out.println("getUser(no)" + userVo);
			
			//userVo 전달 폼
			request.setAttribute("userVo", userVo);

			//포워드
			WebUtil.forword(request, response, "/WEB-INF/views/user/modifyForm.jsp");

		} else if("modify".equals(action)) {
			System.out.println("회원정보 수정");
			
			//파라미터 값 꺼내오기
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			//세션에서 no 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//UserVO 로 만들기
			UserVo userVo = new UserVo(no, pw, name, gender);
			
			//dao --> update() 실행
			UserDao userDao = new UserDao();
			userDao.Update(userVo);
			
			//session의 정보도 update
			//session의 name값만 변경
			authUser.setName(name);
			
			WebUtil.redirect(request, response, "/mysite2/Main");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
