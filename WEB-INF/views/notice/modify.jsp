<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script type="text/javascript">
function fileDelete(fileName, obj) {
	
	Swal.fire({
		title: '첨부파일을 삭제하시겠습니까?',
// 		text: '',
		showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
		confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
		cancelButtonText: '취소', // cancel 버튼 텍스트 지정
		
		reverseButtons: false, // 버튼 순서 거꾸로
		
		}).then(result => {
		// 만약 Promise리턴을 받으면,
		if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
			let file = "<input type='hidden' value='"+fileName+"' name='fileIds'>";
			$("form").append(file);
			
			console.log($(obj).parent());
			$(obj).parent().remove();
		}
	});
	
	
// 	if(confirm("첨부파일을 삭제하시겠습니까?")) {
// 		let file = "<input type='hidden' value='"+fileName+"' name='fileIds'>";
// 		$("form").append(file);
		
// 		console.log($(obj).parent());
// 		$(obj).parent().remove();
// 	}
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
			<!-- == 서브타이틀 시작 ===================================================================== -->
			<h3 class="title">공지 수정하기</h3>
			<!-- == 서브타이틀 종료 ===================================================================== -->
			
			<!-- == 작성 폼 시작 ===================================================================== -->
			<form method="post" action="modify.do" enctype="multipart/form-data">
				<div class="write_wrap">
					<input type="hidden" name="id" value="${ loginUser.id }" readonly>
					<input type="hidden" name="nNO" value="${ view.nNO }" readonly>
					<ul class="write_container">
						<li>
							<strong class="tit">제목</strong>
							<div class="content">
								<input type="text" name="nTitle" value="${ view.nTitle }">
							</div>
						</li>
						<li>
							<strong class="tit tit_note">내용</strong>
							<div class="content">
								<textarea name="nNote">${ view.nNote }</textarea>
							</div>
						</li>
						<li>
							<strong class="tit tit_file">첨부파일</strong>
							<div class="col_w100">
								<div class="file_box">
									<input type="file" name="file" multiple="multiple">
								</div>
								<input type="hidden" id='objDelete'>
								<ul class="file_upload">
								<c:forEach var="item" items="${ Alist }">
									<li id='fileObj'>
										<i class="fi fi-sr-clip"></i>
										<span>${ item.orgName }</span>
										<button type="button" class="btn_del" onclick="fileDelete('${ item.newName }',this)">
											<i class="fi fi-rr-cross-small"><span class="blind">첨부파일 삭제</span></i>
										</button>
									</li>
								</c:forEach>
								</ul>
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
							<button type="submit" class="btn_primary">수정완료</button>
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