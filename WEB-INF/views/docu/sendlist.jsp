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
	<!-- = 사원목록 popup 시작 = -->
	<!-- <div id="popup" class="active">
		<div class="popup_warp popup_sendlist"> -->
			<div class="top_warp">
				<button type="button" class="btn_close">
					<i class="fi fi-br-cross"><span class="blind">팝업창 닫기</span></i>
				</button>
			</div>
			<div class="info_warp">
				<div class="tit_container">
					<strong>사원 목록</strong>
					<div class="search_box">
						<input type="text" placeholder="이름을 입력해주세요">
						<button type="submit">
							<i class="fi fi-br-search"></i>
						</button>
					</div>
				</div>
				<div class="info_container">
					<div class="dept_wrap">
						<ul>
							<li class="active">
								<strong class="dept">기획부</strong>
								<span class="num">6</span>
							</li>
							<li>
								<strong class="dept">디자인부</strong>
								<span class="num">6</span>
							</li>
							<li>
								<strong class="dept">개발부</strong>
								<span class="num">6</span>
							</li>
						</ul>
					</div>
					<div class="check_wrap">
						<div class="checkbox_wrap tit_check">
							<input type="checkbox" id="select_all" class="btn_checkbox blind">
							<label for="select_all">
								<span class="icon_checkbox"></span>
								<strong class="all">전체 선택</strong>
							</label>
						</div>
						<ul>
							<li>
								<div class="checkbox_wrap">
									<input type="checkbox" id="select_1" class="btn_checkbox blind">
									<label for="select_1">
										<span class="icon_checkbox"></span>
										<strong class="name">김철수</strong><span class="duty">부장</span>
									</label>
								</div>
							</li>
							<li>
								<div class="checkbox_wrap">
									<input type="checkbox" id="select_2" class="btn_checkbox blind">
									<label for="select_2">
										<span class="icon_checkbox"></span>
										<strong class="name">김철수</strong><span class="duty">부장</span>
									</label>
								</div>
							</li>
						</ul>
					</div>
					<div class="select_wrap">
						<div class="tit_select">
							<strong>결재자</strong>
							<span>2</span>
						</div>
						<div class="list_select">
							<ul>
								<li>
									<div class="person">
										김철수<span>부장</span>
									</div>
									<button type="button" class="btn_del">
										<i class="fi fi-rr-cross-small"><span class="blind">결재자 삭제</span></i>
									</button>
								</li>
								<li>
									<div class="person">
										김철수<span>부장</span>
									</div>
									<button type="button" class="btn_del">
										<i class="fi fi-rr-cross-small"><span class="blind">결재자 삭제</span></i>
									</button>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="btn_wrap MT10">
				<ul>
					<li>
						<a href="#" class="btn_light">닫기</a>
					</li>
					<li>
						<button type="" class="btn_primary">추가</button>
					</li>
				</ul>
			</div>
		<!-- </div>
	</div> -->
	<!-- = 사원목록 popup 종료 = -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>