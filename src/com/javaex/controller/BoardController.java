package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/bc")
public class BoardController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("BoardController");

		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			System.out.println("게시판 리스트");
			
			BoardDao boardDao = new BoardDao();
			List<BoardVo> bList = boardDao.getList();
			
			request.setAttribute("bList", bList);
			
			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
			
		}else if("read".equals(action)) {
			System.out.println("게시판 읽기");
	
			WebUtil.forword(request, response, "/WEB-INF/views/board/read.jsp");
			
		}else if("insert".equals(action)) {
			
			
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
