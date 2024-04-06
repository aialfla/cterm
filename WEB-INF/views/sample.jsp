<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%-- <%@ include file="../../include/head.jsp"%> --%>
<!-- ==== script 시작 ==================================================================================== -->
<script type="text/javascript">
</script>
<!-- ==== /script 종료 ==================================================================================== -->
</head>
<!-- ==== /head 종료 ==================================================================================== -->

<!-- ==== body 시작 ===================================================================================== -->
<body>
	<!-- 상단바 -->
<%-- 	<%@ include file="../../include/header.jsp"%> --%>
	<!-- 네비 -->
<%-- 	<%@ include file="../../include/nav.jsp"%> --%>
	<!-- == 정보영역 시작 ================================================================================= -->
	<main id="main">
	
	</main>
	<!-- == /정보영역 종료 ================================================================================= -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>

			<!-- section 아래 -->
			<!-- == 서브메뉴 시작 ===================================================================== -->
			<nav id="nav_sub">
				<ul class="menu_sub">
					<li class="active">
						<a href="#">#</a>
					</li>
					<li>
						<a href="#">#</a>
					</li>
					<li>
						<a href="#">#</a>
					</li>
				</ul>
			</nav>
			<!-- == /서브메뉴 종료 ===================================================================== -->

<a href="<%= request.getContextPath() %>">로그인</a>
<a href="<%= request.getContextPath() %>/member/join.do">사원 등록</a>
<a href="<%= request.getContextPath() %>/member/list.do">사원 조회</a>
<a href="<%= request.getContextPath() %>/member/modify_mine.do">내 정보 수정</a>
<a href="<%= request.getContextPath() %>/member/modify_mine.do">내 정보 수정</a>
<a href="<%= request.getContextPath() %>/member/modify_admin.do">사원 정보 수정</a>
<a href="<%= request.getContextPath() %>/member/info.do">사원 정보 조회</a>


<a href="<%= request.getContextPath() %>/notice/list.do">공지사항</a>
<a href="<%= request.getContextPath() %>/notice/view.do">공지사항 조회</a>
<a href="<%= request.getContextPath() %>/notice/modify.do">공지사항 수정</a>
<a href="<%= request.getContextPath() %>/notice/write.do">공지사항 작성</a>


<a href="<%= request.getContextPath() %>/msg/list_r.do">수신 쪽지 목록</a>
<a href="<%= request.getContextPath() %>/msg/list_s.do">발신 쪽지 목록</a>
<a href="<%= request.getContextPath() %>/msg/write.do">쪽지 쓰기</a>
<a href="<%= request.getContextPath() %>/msg/view.do">쪽지 읽기</a>
<a href="<%= request.getContextPath() %>/msg/sendlist.do">수신 목록</a>


<a href="<%= request.getContextPath() %>/work/list_mine.do">내 근무</a>
<a href="<%= request.getContextPath() %>/work/list_work.do">근무 목록 조회</a>

<a href="<%= request.getContextPath() %>/work/list_over.do">내 초과근무</a>
<a href="<%= request.getContextPath() %>/work/write_over.do">초과근무 신청</a>
<a href="<%= request.getContextPath() %>/work/list_over_admin.do">초과근무 조회</a>
<a href="<%= request.getContextPath() %>/work/sendlist.do">결재자 목록</a>


<a href="<%= request.getContextPath() %>/docu/list_mine.do">내 기안</a>
<a href="<%= request.getContextPath() %>/docu/list.do">기안 목록 조회</a>
<a href="<%= request.getContextPath() %>/docu/view.do">기안 조회</a>
<a href="<%= request.getContextPath() %>/docu/write.do">기안 작성</a>
<a href="<%= request.getContextPath() %>/docu/sendlist.do">결재자 목록</a>

<a href="<%= request.getContextPath() %>/auth/list_over.do">초과근무</a>
<a href="<%= request.getContextPath() %>/auth/list_vaca.do">연차</a>
<a href="<%= request.getContextPath() %>/auth/list_docu.do">기안</a>


<a href="<%= request.getContextPath() %>/vaca/list_mine.do">내 연차</a>
<a href="<%= request.getContextPath() %>/vaca/view.do">연차 보기</a>
<a href="<%= request.getContextPath() %>/vaca/list.do">연차 조회</a>
<a href="<%= request.getContextPath() %>/vaca/history.do">연차 내역</a>
<a href="<%= request.getContextPath() %>/vaca/sendlist.do">결재자 목록</a>