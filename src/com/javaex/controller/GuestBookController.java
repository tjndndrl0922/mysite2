package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;

@WebServlet("/gbc")
public class GuestBookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("GuestBookController");

		String action = request.getParameter("action");

		if ("addList".equals(action)) {
			System.out.println("방명록 리스트");

			GuestBookDao guestDao = new GuestBookDao();	
			List<GuestBookVo> guestList = guestDao.getList();

			request.setAttribute("gList", guestList);
			
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/addList.jsp");

		} else if ("add".equals(action)) {
			System.out.println("방명록 추가");
			// http://localhost:8088/mysite2/gbc?name=&pass=1234&content=1234&action=add

			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");

			GuestBookVo guestVo = new GuestBookVo(name, password, content);
			GuestBookDao guestDao = new GuestBookDao();
			guestDao.insert(guestVo);

			WebUtil.redirect(request, response, "/mysite2/gbc?action=addList");

		} else if ("deleteForm".equals(action)) {
			System.out.println("리스트 삭제 폼");

			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");

		} else if ("delete".equals(action)) {
			System.out.println("삭제");

			int no = Integer.parseInt(request.getParameter("no"));
			String pass = request.getParameter("pass");

			GuestBookDao guestDao = new GuestBookDao();
			GuestBookVo guestVo = new GuestBookVo(no, pass);

			guestDao.delete(guestVo);

			WebUtil.redirect(request, response, "/mysite2/gbc?action=addList");

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
