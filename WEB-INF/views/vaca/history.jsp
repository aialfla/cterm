<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<!-- == 연차 정보 popup 시작 ===================================================================== -->
		<!-- 팝업 활성화 active -->
		<div id="popup" class="active">
			<div class="popup_warp popup_member">
				<div class="top_warp">
					<strong>김사원님의 연차 내역</strong>
					<button type="button" class="btn_close">
						<i class="fi fi-sr-cross"><span class="blind">팝업창 닫기</span></i>
					</button>
				</div>
				<div class="info_warp">
					<ul>
						<li>
							<strong class="tit">23.01.07</strong>
							<div class="content">
								가족 경조사
							</div>
							<span class="day">1일</span>
						</li>
						<li>
							<strong class="tit">23.07.22 ~ 23.07.24</strong>
							<div class="content">
								여름휴가
							</div>
							<span class="day">3일</span>
						</li>
						<li>
							<strong class="tit">23.08.05</strong>
							<div class="content">
								병가
							</div>
							<span class="day">1일</span>
						</li>
						<li>
							<strong class="tit">23.10.21</strong>
							<div class="content">
								예비군 소집
							</div>
							<span class="day">1일</span>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- = /연차 정보 popup 종료 ===================================================================== -->
	</main>
	<!-- == /정보영역 종료 ================================================================================= -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>