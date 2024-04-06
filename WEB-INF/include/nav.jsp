<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%
	String key = request.getRequestURL().toString();
	key = key.split("WEB-INF/views/")[1].split("/")[0];
%>
	<!-- == 메인메뉴 시작 ================================================================================== -->
	<nav id="nav">
		<ul class="menu">
			<li class="<%= (key.equals("main.jsp")) ? "active" : "" %>">
				<a href="<%= request.getContextPath() %>/main.do">
					<div class="icon_menu"><i class="fi fi-sr-home PT1"></i></div>
					<div class="txt">홈</div>
				</a>
			</li>
			<li class="<%= (key.equals("work")) ? "active" : "" %>">
				<a href="<%= request.getContextPath() %>/work/list_mine.do">
					<div class="icon_menu"><i class="fi fi-sr-briefcase PT1"></i></div>
					<div class="txt">근무</div>
				</a>
			</li>
			<li class="<%= (key.equals("vaca")) ? "active" : "" %>">
				<a href="<%= request.getContextPath() %>/vaca/list_mine.do">
					<div class="icon_menu"><i class="fi fi-sr-umbrella-beach PT2"></i></div>
					<div class="txt">연차</div>
				</a>
			</li>
			<li class="<%= (key.equals("docu")) ? "active" : "" %>">
				<a href="<%= request.getContextPath() %>/docu/list_mine.do">
					<div class="icon_menu"><i class="fi fi-sr-file-edit PT1 PL2"></i></div>
					<div class="txt">기안</div>
				</a>
			</li>
			<c:if test="${ loginUser.duty != 4}">
			<li class="<%= (key.equals("auth")) ? "active" : "" %>">
				<a href="<%= request.getContextPath() %>/auth/list_docu.do">
					<div class="icon_menu"><i class="fi fi-sr-stamp PT2 PL1 fts18"></i></div>
					<div class="txt">결재</div>
				</a>
			</li>
			</c:if>
			<li class="<%= (key.equals("member")) ? "active" : "" %>">
				<a href="<%= request.getContextPath() %>/member/list.do">
					<div class="icon_menu"><i class="fi fi-sr-users-alt PT5 fts20"></i></div>
					<div class="txt">사원</div>
				</a>
			</li>
			<li class="<%= (key.equals("notice")) ? "active" : "" %>">
				<a href="<%= request.getContextPath() %>/notice/list.do">
					<div class="icon_menu"><i class="fi fi-sr-bullhorn PT2"></i></div>
					<div class="txt">공지</div>
				</a>
			</li>
		</ul>
		<a href="<%= request.getContextPath() %>/logout.do" title="로그아웃" class="btn_logout">
			<i class="fi fi-sr-address-card"></i>
		</a>
	</nav>
	<!-- == /메인메뉴 종료 ================================================================================== -->
