<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<!-- <script src="../../../resources/js/work.js"></script> -->
<script type="text/javascript">

window.onload = function() {
	var startDate = getSunday(new Date())
	var endDate   = getSatday(new Date())
	var start     = startDate.toISOString().split("T")[0];
	var end       =   endDate.toISOString().split("T")[0];
	if (!$("#startday").val()) {
		$("#startday").val(start);
	}
	if (!$("#endday").val()) {
		$("#endday").val(end);
	}
}

function getSunday(d) {
	  var day = d.getDay(),
	    diff = d.getDate() - day; 
	  return new Date(d.setDate(diff));
	}
function getSatday(d) {
	  var day = d.getDay(),
	    diff = d.getDate() - day + (day == 0 ? -1 : 6); // adjust when day is sunday
	  return new Date(d.setDate(diff));
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
		<!-- = 토탈정보 시작 ============================================================================= -->
		<c:if test="${ loginUser.duty != 0 }">
		<%@ include file="../../include/work_total.jsp" %>
		</c:if>
		<!-- == /토탈정보 종료 ========================================================================== -->

		<!-- == 메인정보 시작 ========================================================================== -->
		<section id="main_warp">
			<!-- == 서브메뉴 시작 ===================================================================== -->
			<nav id="nav_sub">
				<ul class="menu_sub">
					<c:if test="${ loginUser.duty ne 0 }">
						<li class="active">
							<a href="<%= request.getContextPath() %>/work/list_mine.do">내 근무</a>
						</li>
						<li>
							<a href="<%= request.getContextPath() %>/work/list_over_mine.do">내 초과근무</a>
						</li>
					</c:if>
					<c:if test="${ loginUser.duty lt 4 }">
						<li>
							<a href="<%= request.getContextPath() %>/work/list_work.do">근무 조회</a>
						</li>
					</c:if>
<%-- 					<c:if test="${ loginUser.duty eq 0 }"> --%>
<!-- 						<li> -->
<%-- 							<a href="<%= request.getContextPath() %>/work/list_over.do">초과근무 조회</a> --%>
<!-- 						</li> -->
<%-- 					</c:if> --%>
				</ul>
			</nav>
			<!-- == /서브메뉴 종료 ===================================================================== -->

			<!-- == 서브타이틀 시작 ===================================================================== -->
			<form action="list_mine.do" method="get">
			<div class="tit_sub">
				<div class="date_warp">
					<!-- <i class="fi fi-rr-calendar"></i> -->
						<input type="date" value="${param.startday}" name="startday" id="startday" title="시작 날짜" onchange="this.form.submit()">
					<span class="slash"> ~ </span>
						<input type="date" value="${param.endday}" name="endday" id="endday" title="종료 날짜" onchange="this.form.submit()">
				</div>
				<a href="write_over.do" class="btn_write"><i class="fi fi-sr-time-add"></i>초과근무 신청</a>
			</div>
			</form>
			<!-- == 서브타이틀 종료 ===================================================================== -->

			<!-- == 목록 테이블 시작 ===================================================================== -->
			<table class="tb_list">
				<caption class="blind">내 근무 조회</caption>
				<thead>
					<tr>
						<th>날짜</th>
						<th>출근</th>
						<th>퇴근</th>
						<th>초과</th>
						<th>총 근무</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${ list }" >
					<fmt:parseDate var="date" value="${ item.date }" pattern="yyyy-MM-dd"/>
						<tr>
							<td><fmt:formatDate value="${ date }" type="date" pattern="yyyy-MM-dd (E)"/></td>
							<td>${ item.start }</td>
							<td>
							<c:if test="${ empty item.end }">-</c:if>
							${ item.end }
							</td>
							<td>
							<c:if test="${ item.overtime eq '00:00:00' }">-</c:if>
							<c:if test="${ item.overtime ne '00:00:00' }">${ item.overtime }</c:if>
							</td>
							<td>
							<c:if test="${ empty item.totaltime }">-</c:if>
							${ item.totaltime }
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${ empty list }">
				<div class="td_nodata">
					<img alt="데이터가 없습니다" src="<%=request.getContextPath() %>/resources/img/nodata.png">
				</div>
			</c:if>
			<!-- == /목록 테이블 종료 ===================================================================== -->
		</section>
		<!-- == /메인정보 종료 ========================================================================== -->
	</main>
	<!-- == /정보영역 종료 ================================================================================= -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>