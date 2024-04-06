<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script type="text/javascript">
	window.onload = function() {
		var startDate = new Date(new Date().getFullYear(), new Date().getMonth(), +2);
		var endDate   = new Date(new Date().getFullYear(), new Date().getMonth()+1, +1);
		var start     = startDate.toISOString().split("T")[0];
		var end       =   endDate.toISOString().split("T")[0];
		if (!$("#startday").val()) {
			$("#startday").val(start);
		}
		if (!$("#endday").val()) {
			$("#endday").val(end);
		}
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
						<li>
							<a href="<%= request.getContextPath() %>/work/list_mine.do">내 근무</a>
						</li>
						<li>
							<a href="<%= request.getContextPath() %>/work/list_over_mine.do">내 초과근무</a>
						</li>
					</c:if>
					<c:if test="${ loginUser.duty lt 4 }">
						<li class="active">
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
				<form method="get" action="list_work.do">
			<div class="tit_sub">
				<div class="date_warp">
						<input type="date" value="${param.startday}" name="startday" id="startday" title="시작 날짜" onchange="this.form.submit()">
					<span class="slash"> ~ </span>
						<input type="date" value="${param.endday}" name="endday" id="endday" title="종료 날짜" onchange="this.form.submit()">
				</div>
				<!-- 검색 필터 -->
				<div class="search_warp">
				<c:if test="${ loginUser.duty ==0 }">
					<select name="dept" id="dept" onchange="this.form.submit()">
						<option value="0"<c:if test="${param.dept eq 0}">selected</c:if>>전체</option>
						<option value="1"<c:if test="${param.dept eq 1}">selected</c:if>>기획부</option>
						<option value="2"<c:if test="${param.dept eq 2}">selected</c:if>>디자인부</option>
						<option value="3"<c:if test="${param.dept eq 3}">selected</c:if>>개발부</option>
					</select>
					<select name="duty" id="duty" onchange="this.form.submit()">
						<option value="0"<c:if test="${param.duty eq 0}">selected</c:if>>전체</option>
						<option value="1"<c:if test="${param.duty eq 1}">selected</c:if>>대표</option>
						<option value="2"<c:if test="${param.duty eq 2}">selected</c:if>>부장</option>
						<option value="3"<c:if test="${param.duty eq 3}">selected</c:if>>팀장</option>
						<option value="4"<c:if test="${param.duty eq 4}">selected</c:if>>사원</option>
					</select>
				</c:if>
					<div class="search_box">
						<input type="text" placeholder="이름을 입력해주세요" name="name" id="name" value="${ param.name }">
						<button type="submit">
							<i class="fi fi-br-search"></i>
						</button>
					</div>
				</div>
			</div>
				
			<!-- == 서브타이틀 종료 ===================================================================== -->

			<!-- == 목록 테이블 시작 ===================================================================== -->
			<table class="tb_list">
				<caption class="blind">근무 조회</caption>
				<thead>
					<tr>
						<th>날짜</th>
						<th>이름</th>
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
							<td class="msg_name">
								<span class="dept">${ item.deptName }</span>
								<strong>${ item.name }<span class="duty">${ item.dutyName }</span></strong>
							</td>
							<td>${ item.start }</td>
							<td>
							<c:if test="${ empty item.end }">
								<c:choose>
									<c:when test="${ loginUser.duty eq 0 }">
										<a href="${pageContext.request.contextPath}/work/set_end_admin.do?id=${item.id}&date=${item.date}" class="btn_table red" title="퇴근하기">
											<i class="fi fi fi-sr-alarm-plus"></i>
										</a>
<%-- 										<a href="${pageContext.request.contextPath}/work/set_end_admin.do?id=${item.id}&date=${item.date}">퇴근</a> --%>
									</c:when>
									<c:otherwise>
									-
									</c:otherwise>
								</c:choose>
							</c:if>
							${ item.end }
							</td>
							<td>
							<c:if test="${ empty item.overtime }">-</c:if>
							${ item.overtime }
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
		
			<!-- == 페이징 시작 ===================================================================== -->
			<c:if test="${ not empty list }">
			<div class="paging">
				<ul>
					<c:if test="${ MaxPage > 10 }">
						<li>
							<a href="${pageContext.request.contextPath}/work/list_work.do?pageNO=1
								<c:if test="${ not empty param.startday}">&startday=${param.startday}</c:if>
								<c:if test="${ not empty param.endday}">&endday=${param.endday}</c:if>
								<c:if test="${ not empty param.dept}">&dept=${param.dept}</c:if>
								<c:if test="${ not empty param.duty}">&duty=${param.duty}</c:if>
								<c:if test="${ not empty param.name}">&name=${param.name}</c:if>" 
								title="처음 페이지로 이동" 
								<c:choose>
									<c:when test="${ startBlock == 1 }">class="btn_first" onclick="return false;"</c:when>
									<c:when test="${ startBlock > 1 }">class="btn_first active"</c:when>
								</c:choose>
							>
								<i class="fi fi-br-angle-double-small-left"></i>
								<span class="hidden">처음</span>
							</a> 
						</li>
					</c:if>
					<li>
						<a href="${pageContext.request.contextPath}/work/list_work.do?pageNO=${ pageNO-1 }
							<c:if test="${ not empty param.startday}">&startday=${param.startday}</c:if>
							<c:if test="${ not empty param.endday}">&endday=${param.endday}</c:if>
							<c:if test="${ not empty param.dept}">&dept=${param.dept}</c:if>
							<c:if test="${ not empty param.duty}">&duty=${param.duty}</c:if>
							<c:if test="${ not empty param.name}">&name=${param.name}</c:if>" 
							title="이전 페이지로 이동" 
							<c:choose>
								<c:when test="${ pageNO == 1 }">class="btn_prev" onclick="return false;"</c:when>
								<c:when test="${ pageNO != 1 }">class="btn_prev active"</c:when>
							</c:choose>
						>
							<i class="fi fi-br-angle-small-left"></i>
							<span class="hidden">이전</span>
						</a>
					</li>
					<c:forEach var="i" begin="${startBlock}" end="${endBlock}" step="1">
						<li>
							<a href="${pageContext.request.contextPath}/work/list_work.do?pageNO=${ i }
								<c:if test="${ not empty param.startday}">&startday=${param.startday}</c:if>
								<c:if test="${ not empty param.endday}">&endday=${param.endday}</c:if>
								<c:if test="${ not empty param.dept}">&dept=${param.dept}</c:if>
								<c:if test="${ not empty param.duty}">&duty=${param.duty}</c:if>
								<c:if test="${ not empty param.name}">&name=${param.name}</c:if>" 
								<c:choose>
									<c:when test="${ pageNO == i }">class="btn_num active"</c:when>
									<c:when test="${ pageNO != i }">class="btn_num"</c:when>
								</c:choose>
							>
								${ i }
							</a>
						</li>
					</c:forEach>
					<li>
						<a href="${pageContext.request.contextPath}/work/list_work.do?pageNO=${ pageNO+1 }
							<c:if test="${ not empty param.startday}">&startday=${param.startday}</c:if>
							<c:if test="${ not empty param.endday}">&endday=${param.endday}</c:if>
							<c:if test="${ not empty param.dept}">&dept=${param.dept}</c:if>
							<c:if test="${ not empty param.duty}">&duty=${param.duty}</c:if>
							<c:if test="${ not empty param.name}">&name=${param.name}</c:if>" 
							title="다음 페이지로 이동" 
							<c:choose>
								<c:when test="${ pageNO == endBlock && MaxPage == endBlock }">class="btn_next" onclick="return false;"</c:when>
								<c:when test="${ pageNO < MaxPage }">class="btn_next active"</c:when>
							</c:choose>
						>
							<i class="fi fi-br-angle-small-right"></i>
							<span class="hidden">다음</span>
						</a>
					</li>
					<c:if test="${ MaxPage > 10 }">
						<li>
							<a href="${pageContext.request.contextPath}/work/list_work.do?pageNO=${ MaxPage }
								<c:if test="${ not empty param.startday}">&startday=${param.startday}</c:if>
								<c:if test="${ not empty param.endday}">&endday=${param.endday}</c:if>
								<c:if test="${ not empty param.dept}">&dept=${param.dept}</c:if>
								<c:if test="${ not empty param.duty}">&duty=${param.duty}</c:if>
								<c:if test="${ not empty param.name}">&name=${param.name}</c:if>" 
								title="끝 페이지로 이동" 
								<c:choose>
									<c:when test="${ endBlock == MaxPage }">class="btn_last" onclick="return false;"</c:when>
									<c:when test="${ endBlock != MaxPage }">class="btn_last active"</c:when>
								</c:choose>
							>
								<i class="fi fi-br-angle-double-small-right"></i>
								<span class="hidden">끝</span>
							</a>
						</li>
					</c:if>
				</ul>
			</div>
			</c:if>
			</form>
			<!-- == 페이징 종료 ===================================================================== -->
			
		</section>
		<!-- == /메인정보 종료 ========================================================================== -->
	</main>
	<!-- == /정보영역 종료 ================================================================================= -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>