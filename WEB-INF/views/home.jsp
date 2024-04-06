<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script type="text/javascript">

// function blankCheck() {
// 	var id = $("#id").val();
// 	var pw = $("#pw").val();
// 	if (!id) {
// 		Swal.fire("사원번호를 입력해주세요");
// 		$("#id").focus();
// 		return false;
// 	}
// 	if (pw == null || pw == "") {
// 		swal("비밀번호를 입력해주세요");
// 		$("#pw").focus();
// 		return false;
// 	}
// 	return true;
// }

</script>
<!-- ==== /script 종료 ==================================================================================== -->
</head>
<!-- ==== /head 종료 ==================================================================================== -->
<!-- ==== body 시작 ===================================================================================== -->
<body>
	<!-- == 상단창 시작 =============================================================================== -->
	<header id="header">
		<h1 class="logo">
			<a href="<%= request.getContextPath() %>/main.do">
				<span class="blind">cterm 인사관리시스템</span>
			</a>
		</h1>
	</header>
	<!-- == /상단창 종료 ================================================================================== -->

	<!-- == 로그인 시작 ================================================================================= -->
	<main id="login_warp">
		<section id="login_box">
			<h2>로그인</h2>
			<form method="post" action="login.do">
			<input type="number" placeholder="사원번호를 입력해주세요" name="id" value="" id="id" required>
			<input type="password" placeholder="비밀번호를 입력해주세요" name="pw" value="" id="pw" required>
			<button type="submit">로그인</button>
		</form>
		</section>
	</main>
		<!-- == /로그인 종료 ================================================================================= -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>