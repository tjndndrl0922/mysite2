<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.javaex.vo.UserVo"%>
<%
	UserVo authUser = (UserVo)session.getAttribute("authUser");
%>


		
		
		<div id="header">
			<h1><a href="/mysite2/Main">MySite</a></h1>
			
			<c:choose>
				<c:when test="${empty authUser}">
					<ul>
						<li><a href="/mysite2/User?action=loginForm">로그인</a></li>
						<li><a href="/mysite2/User?action=joinForm">회원가입</a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul>
						<li>${authUser.name } 님 안녕하세요^^</li>
						<li><a href="/mysite2/User?action=logout">로그아웃</a></li>
						<li><a href="/mysite2/User?action=modifyForm">회원정보수정</a></li>
					</ul>
				</c:otherwise>	
			</c:choose>
		</div>
		<!-- //header -->
		
		<div id="nav">
			<ul>
				<li><a href="/mysite2/gbc?action=addList">방명록</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="">게시판</a></li>
				<li><a href="">입사지원서</a></li>
			</ul>
			<div class="clear"></div>
		</div>