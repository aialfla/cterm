<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script type="text/javascript">
function vacaBack(vacaNO) {
	var checkOkCount = $("#checkOkCount").val();
	var checkcount = $("#checkCount").val();
	if(checkcount == checkOkCount)
	{
		alert("결재가 승인되어 신청서를 철회 할 수 없습니다.");
		return false;	
	} else if (checkOkCount != 0) {
		alert("연차 신청서가 결재 진행중입니다.");
		return false;	
	}
	
	if(confirm("연차를 철회하시겠습니까?")){
// 		alert(vacaNO);
		window.location.href = "<%= request.getContextPath() %>/vaca/back.do?vacaNO="+vacaNO+"";
	}
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
			<!-- == 상세정보 시작 ===================================================================== -->
			<div class="view_wrap cf">
				<!-- 글 내용 -->
				<div class="info_warp">
					<h3 class="view_tit">
						<span class="type">연차</span>
						[${ writer.deptName }] ${ writer.name } ${ writer.dutyName } 연차 신청서
					</h3>
					<ul class="view_info">
						<li class="view_name">
							<strong>작성자</strong>
							<span>${ writer.name }</span>
						</li>
						<li class="view_date">
							<strong>등록일</strong>
							<span>${ view.wdate }</span></li>
					</ul>
					<ul class="view_info">
						<li class="view_name">
							<strong>연차 신청일</strong>
							<c:if test="${ view.sday != view.eday }">
								
								<span>${ view.sday }</span>
								<span> ~ </span>
								<span>${ view.eday }</span>
							</c:if>
							<c:if test="${ view.sday == view.eday }">
								<span>${ view.eday }</span>
							</c:if>
							<span>(${ view.day }일)</span>
						</li>
					</ul>
					<div class="view_note">
						<p>${ view.why }</p>
					</div>
				</div>
				<!-- 결재 현황 -->
				<div class="auth_warp">
					<h4 class="auth_tit">
					<input type='hidden' id="checkOkCount" value=${ checkOkCount }>
					<input type='hidden' id="checkCount" value=${ checkCount }>
						결재 현황 <span>${ checkOkCount }/${ checkCount }</span>
						<c:choose>
							<c:when test="${ view.state == 0 }"><span class="dstate_wait">대기</span></c:when>
							<c:when test="${ view.state == 1 }"><span class="dstate_ing">진행</span></c:when>
							<c:when test="${ view.state == 2 }"><span class="dstate_ok">승인</span></c:when>
							<c:when test="${ view.state == 8 }"><span class="dstate_no">반려</span></c:when>
						</c:choose>
					</h4>
					<ul class="auth_list">
						<c:forEach var="item" items="${ checklist }">
							<li class="
							<c:choose>
								<c:when test="${ item.state == 0 || item.state == 8 }">
								state_wait
								</c:when>
								<c:when test="${ item.state == 1 }">
								state_wait2
								</c:when>
								<c:when test="${ item.state == 2 }">
								state_ok
								</c:when>
								<c:when test="${ item.state == 9 }">
								state_no
								</c:when>
							</c:choose>
							">
								<strong>${ item.name }<span class="duty">${ item.duty }</span></strong>
								<span class="state_txt">
								<c:choose>
									<c:when test="${ item.state == 0 }">
									대기
									</c:when>
									<c:when test="${ item.state == 8 }">
									-
									</c:when>
									<c:when test="${ item.state == 1 }">
									결재 대기
									</c:when>
									<c:when test="${ item.state == 2 }">
									승인
									</c:when>
									<c:when test="${ item.state == 9 }">
									반려
									</c:when>
								</c:choose>
								</span>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<!-- == /상세정보 종료 ===================================================================== -->
			
			<!-- == 버튼 시작 ===================================================================== -->
			<div class="btn_wrap MT60">
				<ul>
					<li>
						<a href="<%= request.getContextPath() %>/auth/list_vaca.do" class="btn_light">목록으로</a>
					</li>
					
					<c:if test="${ login.state == 1 }">
					<li>
						<a href="<%= request.getContextPath() %>/auth/approve.do?vacaNO=${ view.vacaNO }" class="btn_primary">승인하기</a>
					</li>
					<li>
						<a href="<%= request.getContextPath() %>/auth/reject.do?vacaNO=${ view.vacaNO }" class="btn_secon">반려하기</a>
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