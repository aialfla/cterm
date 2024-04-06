<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script type="text/javascript">
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
			<!-- section 아래 -->
			<!-- == 서브메뉴 시작 ===================================================================== -->
			<nav id="nav_sub">
				<ul class="menu_sub">
					<li>
						<a href="<%= request.getContextPath() %>/member/list.do">사원 조회</a>
					</li>
					<li class="active">
						<a href="<%= request.getContextPath() %>/member/mine.do">내 정보</a>
					</li>
				</ul>
			</nav>
			<!-- == /서브메뉴 종료 ===================================================================== -->

			<!-- == 내 정보 시작 ===================================================================== -->	
			<div class="minfo_wrap">
				<div class="minfo_container">
					<div class="info_box">
						<ul>
							<li>
								<ul class="list_two">
									<li>
										<strong class="tit">사원번호</strong>
										<div class="content">
											<input type="hidden" name="id" value="${ view.id }">
											${ map.id }
										</div>
									</li>
									<li>
										<strong class="tit">이름</strong>
										<div class="content">
											${ map.name }
										</div>
									</li>
								</ul>
							</li>
							<li>
								<ul class="list_two">
									<li>
										<strong class="tit">부서</strong>
										<div class="content">
											${ map.deptName }
										</div>
									</li>
									<li>
										<strong class="tit">직급</strong>
										<div class="content">
											${ map.dutyName }
										</div>
									</li>
								</ul>
							</li>
							<li>
								<ul class="list_two">
									<li>
										<strong class="tit">입사일</strong>
										<div class="content">
											<fmt:parseDate var="joindate" value="${ map.joindate }" pattern="yyyy-MM-dd'T'HH:mm"/>
											<fmt:formatDate value="${ joindate }" pattern="yyyy-MM-dd"/>
										</div>
									</li>
									<li>
										<strong class="tit">상태</strong>
										<div class="content">
											${ map.stateName }
										</div>
									</li>
								</ul>
							</li>
							<li>
								<strong class="tit">연락처</strong>
								<div class="content">
									<c:if test="${ empty map.tel }">-</c:if>
									${ map.tel }
								</div>
							</li>
							<li>
								<strong class="tit">이메일</strong>
								<div class="content">
									<c:if test="${ empty map.mail }">-</c:if>
									${ map.mail }
								</div>
							</li>
							<li>
								<strong class="tit">주소</strong>
								<div class="content">
									<c:if test="${ empty map.addr }">-</c:if>
									${ map.addr }
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- == 내 정보 종료 ===================================================================== -->
			
			
			<!-- == 버튼 시작 ===================================================================== -->
			<div class="btn_wrap MT60">
				<ul>
					<li>
						<a href="<%= request.getContextPath() %>/member/modify_mine.do"  class="btn_primary">수정하기</a>
					</li>
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