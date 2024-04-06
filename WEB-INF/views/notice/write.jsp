<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script type="text/javascript">
function text() {
// 	alert('call');
	var checnktit = $("#tit").val();
	var checnkwhy = $("#why").val();
	if( checnktit == null || checnktit == "" )
	{
// 		alert("공지사항 제목을 입력하세요");
		Swal.fire('공지사항 제목을 입력하세요');
		return false;
	}
	if( checnkwhy == null || checnkwhy == "" )
	{
// 		alert("공지사항 내용을 입력하세요");
		Swal.fire('공지사항 내용을 입력하세요');
		return false;
	}
	document.frm.submit();
};
</script>
<!-- ==== /script 종료 ==================================================================================== -->
</head>
<!-- ==== /head 종료 ==================================================================================== -->

<!-- ==== body 시작 ===================================================================================== -->
<body>
	<!-- 상단바 -->
	<%@ include file="../../include/header.jsp"%>
	<!-- 네비 -->
	<%@ include file="../../include/nav.jsp"%>
	<!-- == 정보영역 시작 ================================================================================= -->
	<main id="main">
		<!-- == 메인정보 시작 ========================================================================== -->
		<section id="main_warp">
			<!-- == 서브타이틀 시작 ===================================================================== -->
			<h3 class="title">공지 작성</h3>
			<!-- == 서브타이틀 종료 ===================================================================== -->
			
			<!-- == 작성 폼 시작 ===================================================================== -->
			<form method="post" action="write.do" enctype="multipart/form-data" name='frm'>
				<div class="write_wrap">
					<input type="hidden" name="id" value="${ loginUser.id }" readonly>
					<ul class="write_container">
						<li>
							<strong class="tit">제목</strong>
							<div class="content">
								<input type="text" name="nTitle" placeholder="제목을 입력해주세요" id="tit">
							</div>
						</li>
						<li>
							<strong class="tit tit_note">내용</strong>
							<div class="content">
								<textarea name="nNote" placeholder="내용을 입력해주세요" id="why"></textarea>
							</div>
						</li>
						<li>
							<strong class="tit tit_file">첨부파일</strong>
							<div class="file_box">
								<input type="file" name="file" multiple="multiple">
							</div>
						</li>
					</ul>
				</div>
				<!-- == 버튼 시작 ===================================================================== -->
				<div class="btn_wrap MT60">
					<ul>
						<li>
							<a href="<%= request.getContextPath() %>/notice/list.do" class="btn_light">목록으로</a>
							
						</li>
						<li>
							<button type="button" class="btn_primary" onclick="text()">작성완료</button>
						</li>
					</ul>
				</div>
				<!-- == /버튼 종료 ===================================================================== -->
			</form>
			<!-- == /작성 폼 종료 ===================================================================== -->
		</section>
		<!-- == /메인정보 종료 ========================================================================== -->
	</main>
	<!-- == /정보영역 종료 ================================================================================= -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>