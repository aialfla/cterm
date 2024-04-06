<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->

<%
	// 줄바꿈 변경
	pageContext.setAttribute("cn", "\n");
%>
<script type="text/javascript">
// function Delete(nNO) {
// 	if(confirm("게시글을 삭제하시겠습니까?")) {
// 		alert(nNO);
<%-- 		location.href="<%= request.getContextPath() %>/notice/delete.do?nNO="+nNO; --%>
// 	}
// }

function Delete(nNO) {
	Swal.fire({
		title: '게시글 삭제하시겠습니까?',
//		text: '다시 되돌릴 수 없습니다. 신중하세요.',
//		icon: 'warning',
		
		showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
//		confirmButtonColor: '#ef4e7b', // confrim 버튼 색깔 지정
//		cancelButtonColor: '#ecebf1', // cancel 버튼 색깔 지정
		confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
		cancelButtonText: '취소', // cancel 버튼 텍스트 지정
		
		reverseButtons: false, // 버튼 순서 거꾸로
		
		}).then(result => {
		// 만약 Promise리턴을 받으면,
		if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
			location.href="<%= request.getContextPath() %>/notice/delete.do?nNO="+nNO;
		}
	});
}
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
			<!-- == 상세정보 시작 ===================================================================== -->
			<div class="view_wrap">
				<h3 class="view_tit">
					<span class="type">공지</span>${ view.nTitle }
				</h3>
				<ul class="view_info">
					<li class="view_date">
						<strong>등록일</strong>
						<span><td>${ view.wdate }</td></span></li>
					<!-- 첨부파일 없을 시 숨김 처리-->
					<c:if test="${ not empty Alist }">
						<li class="view_file">
							<strong>첨부파일</strong>
							<ul>
								<c:forEach var="item" items="${ Alist }">
								<li>
									<a href="<%= request.getContextPath() %>/resources/noticeUpload/${ item.newName }" title="첨부파일 다운" download="${ item.orgName }">
										<i class="fi fi-rr-file-download"></i>
										<span>${ item.orgName }</span>
									</a>
								</li>
								</c:forEach>
							</ul>
						</li>
					</c:if>
				</ul>
				<div class="view_note">
					<p>${ fn:replace( view.nNote, cn,"<br/>" ) }</p>
				</div>
			</div>
			<!-- == /상세정보 종료 ===================================================================== -->
			
			<!-- == 버튼 시작 ===================================================================== -->
			<div class="btn_wrap MT60">
				<ul>
					<li>
						<a href="<%= request.getContextPath() %>/notice/list.do" class="btn_light">목록으로</a>
					</li>
					<c:if test="${ loginUser.getDept() == 0 }">
						<li>
							<a href="<%= request.getContextPath() %>/notice/modify.do?nNO=${ view.nNO }" class="btn_primary">수정하기</a>
						</li>
						<li>
							<button type="button" class="btn_secon" onclick="Delete('${ view.nNO }', this)">삭제하기</button>
						</li>
					</c:if>
				</ul>
			</div>
			<!-- == /버튼 종료 ===================================================================== -->
		</section>
		<!-- == /메인정보 종료 ========================================================================== -->
	</main>
	<!-- == /정보영역 종료 ================================================================================= -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>