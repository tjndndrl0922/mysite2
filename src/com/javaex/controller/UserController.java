package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/User")
public class UserController extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("UserController");
		
		String action = request.getParameter("action");
		
		if("joinForm".equals(action)) {
			System.out.println("회원가입용");
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
		}else if("join".equals(action)) {
			System.out.println("회원가입");
			
			//dao --> insert() 저장
			
			//파라미터 값 꺼내기
			//http://localhost:8088/mysite2/User?uid=jus&pw=1234&uname=서웅기&gender=male&action=join
			String id = request.getParameter("uid");
			String password = request.getParameter("pw");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");
			
			//vo 로 묶기 -> vo 만들기 생성자 추가
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo.toString());
			
			//dao 클래스 insert(vo) 사용 --> 저장-->회원가입
			UserDao userDao = new UserDao();
			userDao.insert(userVo);
			
			//포워드 --> joinOk.jsp
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinOk.jsp");
		
		}else if("loginForm".equals(action)) {
			System.out.println("로그인");
			WebUtil.forword(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
