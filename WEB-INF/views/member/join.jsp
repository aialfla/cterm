<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script>
	/* 각 입력 폼에 값이 있는지 확인 */
	function DoSubmit()
	{
		if( $("input[name=pw]").val() == null )
		{
// 			alert("비번을 입력해주세요");
			Swal.fire('비번을 입력해주세요"');
			$("input[name=pw]").focus();
			return false;
		}

		return true;
	}
	
	// 현재 날짜 기본 세팅
	document.getElementById('nowDate').value = new Date().toISOString().substring(0, 10);
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
			<h3 class="title">사원 등록</h3>
			<!-- == 서브타이틀 종료 ===================================================================== -->
			
			<!-- == 작성 폼 시작 ===================================================================== -->
			<form method="POST" action="join.do">
				<div class="write_wrap">
					<ul class="write_container">
						<li>
							<strong class="tit">비밀번호<span class="color_red"> *</span></strong>
							<div class="content">
								<input type="password" name="pw" required>
							</div>
						</li>
						<li>
							<strong class="tit">이름<span class="color_red"> *</span></strong>
							<div class="content">
								<input type="text" name="name"  required>
							</div>
						</li>
						<li>
							<ul class="list_two">
								<li>
									<strong class="tit">부서<span class="color_red"> *</span></strong>
									<div class="content">
										<select name="dept">
											<option value="1">기획팀</option>
											<option value="2">디자인부</option>
											<option value="3">개발부</option>
										</select>
									</div>
								</li>
								<li>
                                    <strong class="tit">직급<span class="color_red"> *</span></strong>
                                    <div class="content">
                                        <select name="duty">
                                            <option value="1">대표</option>
                                            <option value="2">부장</option>
                                            <option value="3">팀장</option>
                                            <option value="4" selected>사원</option>
                                        </select>
                                    </div>
								</li>
							</ul>
						</li>
						<li>
							<ul class="list_two">
								<li>
									<strong class="tit">입사일<span class="color_red"> *</span></strong>
									<div class="content">
										<input type="date" name="joindate" id="nowDate" required>
										<script>
											document.getElementById('nowDate').value = new Date().toISOString().substring(0, 10);
										</script>
									</div>
								</li>
							    <li>
                                    <strong class="tit">상태<span class="color_red"> *</span></strong>
                                    <div class="content">
                                        <select name="state" id="state">
                                            <option value="1">재직</option>
                                            <option value="2">휴직</option>
                                            <option value="0">퇴사</option>
                                        </select>
                                    </div>
                                </li>
							</ul>
						</li>
						<li>
							<strong class="tit">이메일</strong>
							<div class="content">
								<input type="email" name="mail">
							</div>
						</li>
						<li>
							<strong class="tit">연락처</strong>
							<div class="content">
								<input type="text" name="tel">
							</div>
						</li>
						<li>
							<strong class="tit">주소</strong>
							<div class="content">
								<input type="text" name="addr">
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
							<button type="submit" class="btn_primary">사원 등록</button>
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