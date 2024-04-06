<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script>

function pwcheck() {
	var pw = $("input[name=pw]");
	var npw = $("input[name=npw]");
	var npwc = $("input[name=npwc]");
	var check = false;
	
	$.ajax ({
		url : "pwcheck.do",
		data : "pw="+pw.val(),
		async: false,
		type : "get",
		success : function(data) {
			if (data != 1) {
// 				alert("비밀번호를 확인해주세요");
				Swal.fire('비밀번호를 확인해주세요');
				pw.val("");
				check = true;
			}
		}
	})
	if (check == true) {
		return false;
	} 
	if (npw.val() && !npwc.val()) {
// 		alert("새 비밀번호를 다시 한 번 입력해주세요");
		npwc.focus();
		Swal.fire('새 비밀번호를 한 번 더 입력해주세요');
		return false;
	}
	else if (npw.val() != npwc.val()) {
		npw.focus();
		npw.val("");
		npwc.val("");
// 		alert("새 비밀번호 입력을 확인해주세요");
		Swal.fire('새 비밀번호가 일치하지 않습니다');
		return false;
	}
	return true;
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
			<h3 class="title">내 정보 수정</h3>
			<!-- == 서브타이틀 종료 ===================================================================== -->
			
			<!-- == 작성 폼 시작 ===================================================================== -->
			<form method="POST" action="modify_mine.do" onsubmit="return pwcheck()">
				<div class="write_wrap">
					<ul class="write_container">
						<li>
							<strong class="tit">사원번호</strong>
							<div class="content">
								<input type="hidden" name="id" value="${ view.id }">
								${ view.id }
							</div>
						</li>
<!-- 						<li> -->
<!-- 							<strong class="tit">이름</strong> -->
<!-- 							<div class="content"> -->
<%-- 								${ view.name } --%>
<!-- 							</div> -->
<!-- 						</li> -->
                        <li>
							<strong class="tit">현재 비밀번호<span class="color_red"> *</span></strong>
							
                            <div class="content">
                                <input type="password" name="pw" value="">
                            </div>
<!--                             <a href="javascript:pwcheck()">체크</a> -->
                        </li>
                        <li>
							<strong class="tit">새 비밀번호</span></strong>
                            <div class="content">
                                <input type="password" name="npw" value="" >
                            </div>
						</li>
                        <li>
							<strong class="tit">새 비밀번호 확인</span></strong>
                            <div class="content">
                                <input type="password" name="npwc" value="" >
                            </div>
						</li>
						<li>
							<strong class="tit">연락처</strong>
                            <div class="content">
                                <input type="text" name="tel" value="${ view.tel }">
                            </div>
						</li>
						<li>
							<strong class="tit">이메일</strong>
                            <div class="content">
                                <input type="email" name="mail" value="${ view.mail }">
                            </div>
						</li>
						<li>
							<strong class="tit">주소</strong>
                            <div class="content">
                                <input type="text" name="addr"  value="${ view.addr }">
                            </div>
						</li>
					</ul>
				</div>
				<!-- == 버튼 시작 ===================================================================== -->
				<div class="btn_wrap MT60">
					<ul>
						<li>
							<a href="<%= request.getContextPath() %>/member/list.do" class="btn_light">목록으로</a>
						</li>
						<li>
							<button type="submit" class="btn_primary">수정하기</button>
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