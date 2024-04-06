<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script>

// 퇴사 옵션 선택 시 퇴사일 선택지 표출
$(document).ready(function() {
	$('.retiredate').hide();
	$('#state').change(function() {
		var result = $('#state option:selected').val();
		if (result == '0') {
			$('.retiredate').show();
		} else {
			$('.retiredate').hide();
		}
	}); 
}); 

function pwcheck() {
	var npw = $("input[name=npw]");
	var npwc = $("input[name=npwc]");
	
	if (npw.val() != npwc.val()) {
// 		alert("새 비밀번호 입력을 확인해주세요");
		Swal.fire('새 비밀번호 입력을 확인해주세요');
		npw.val("");
		npwc.val("");
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
			<h3 class="title">사원 정보 수정</h3>
			<!-- == 서브타이틀 종료 ===================================================================== -->
			
			<!-- == 작성 폼 시작 ===================================================================== -->
			<form method="POST" action="modify_admin.do" onsubmit="return pwcheck();">
				<div class="write_wrap">
					<ul class="write_container">
						<li>
							<strong class="tit">사원번호</strong>
							<div class="content">
								<input type="hidden" name="id" value="${ view.id }">
								${ view.id }
							</div>
						</li>
                        <li>
							<strong class="tit">새 비밀번호</strong>
                            <div class="content">
                                <input type="password" name="npw" value="" placeholder="변경할 비밀번호를 입력해주세요">
                            </div>
						</li>
                        <li>
							<strong class="tit">비밀번호 확인</strong>
                            <div class="content">
                                <input type="password" name="npwc" value="" placeholder="새 비밀번호를 다시 한 번 입력해주세요">
                            </div>
						</li>
                        <li>
							<strong class="tit">이름<span class="color_red"> *</span></strong>
                            <div class="content">
                                <input type="text" name="name" value="${ view.name }" required>
                            </div>
						</li>
						<li>
							<ul class="list_two">
								<li>
									<strong class="tit">부서<span class="color_red"> *</span></strong>
									<div class="content">
										<select name="dept">
											<option value="1" 
												<c:if test ="${view.dept eq '1'}"> selected="selected"</c:if> 
											>기획팀</option>
											<option value="2" 
												<c:if test ="${view.dept eq '2'}"> selected="selected"</c:if> 
											>디자인부</option>
											<option value="3" 
												<c:if test ="${view.dept eq '3'}"> selected="selected"</c:if> 
											>개발부</option>
										</select>
									</div>
								</li>
								<li>
									<strong class="tit">직급<span class="color_red"> *</span></strong>
									<div class="content">
										<select name="duty">
											<option value="1" 
												<c:if test ="${view.duty eq '1'}"> selected="selected"</c:if> 
											>대표</option>
											<option value="2" 
												<c:if test ="${view.duty eq '2'}"> selected="selected"</c:if> 
											>부장</option>
											<option value="3" 
												<c:if test ="${view.duty eq '3'}"> selected="selected"</c:if> 
											>팀장</option>
											<option value="4" 
												<c:if test ="${view.duty eq '4'}"> selected="selected"</c:if> 
											>사원</option>
										</select>
									</div>
								</li>
							</ul>
						</li>
						<li>
							<ul class="list_two">
								<li>
                                    <strong class="tit">상태<span class="color_red"> *</span></strong>
                                    <div class="content">
                                        <select name="state" id="state">
                                            <option value="1" 
												<c:if test ="${view.state eq '1'}"> selected="selected"</c:if> 
											>재직</option>
                                            <option value="2" 
												<c:if test ="${view.state eq '2'}"> selected="selected"</c:if> 
											>휴직</option>
                                            <option value="0" 
												<c:if test ="${view.state eq '0'}"> selected="selected"</c:if> 
											>퇴사</option>
                                        </select>
                                    </div>
                                </li>
                                <li class="retiredate">
									<strong class="tit">퇴사일</strong>
									<div class="content">
										<input type="date" name="retiredate" value="${ view.retiredate }">
									</div>
								</li>
							</ul>
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