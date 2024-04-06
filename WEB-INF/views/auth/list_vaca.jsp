<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<!-- == 메인정보 시작 ========================================================================== -->
		<section id="main_warp">
			<!-- == 서브메뉴 시작 ===================================================================== -->
			<nav id="nav_sub">
				<ul class="menu_sub">
					<li>
						<a href="<%= request.getContextPath() %>/auth/list_docu.do">기안
							<c:if test="${ countDocu ne 0 }"><span class="ML3">${ countDocu }</span></c:if>
						</a>
					</li>
					<li class="active">
						<a href="<%= request.getContextPath() %>/auth/list_vaca.do">연차
							<c:if test="${ countVaca ne 0 }"><span class="ML3">${ countVaca }</span></c:if>
						</a>
					</li>
					<li>
						<a href="<%= request.getContextPath() %>/auth/list_over.do">초과근무
							<c:if test="${ countOver ne 0 }"><span class="ML3">${ countOver }</span></c:if>
						</a>
					</li>
				</ul>
			</nav>
			<!-- == /서브메뉴 종료 ===================================================================== -->

			<!-- == 서브타이틀 시작 ===================================================================== -->
			<form action="list_vaca.do" method="get">
			<div class="tit_sub">
				<div class="date_warp">
					<input type="date" value="${param.startday}" name="startday" id="startday" title="시작 날짜" onchange="this.form.submit()">
					<span class="slash"> ~ </span>
					<input type="date" value="${param.endday}" name="endday" id="endday" title="종료 날짜" onchange="this.form.submit()">
				</div>
			
				<!-- === 검색 필터 == -->
				<div class="search_warp">
					<!-- 관리자용 -->
					<c:if test="${ loginUser.duty == 0 }">
						<select name="dept" id="dept" onchange="this.form.submit()">
								<option value="0"<c:if test="${param.dept eq 0}">selected</c:if>>전체</option>
								<option value="1"<c:if test="${param.dept eq 1}">selected</c:if>>기획부</option>
								<option value="2"<c:if test="${param.dept eq 2}">selected</c:if>>디자인부</option>
								<option value="3"<c:if test="${param.dept eq 3}">selected</c:if>>개발부</option>
						</select>
						<select name="duty" id="duty" onchange="this.form.submit()">
							<option value="0"<c:if test="${param.duty eq 0}">selected</c:if>>전체</option>
<%-- 							<option value="1"<c:if test="${param.duty eq 1}">selected</c:if>>대표</option> --%>
							<option value="2"<c:if test="${param.duty eq 2}">selected</c:if>>부장</option>
							<option value="3"<c:if test="${param.duty eq 3}">selected</c:if>>팀장</option>
							<option value="4"<c:if test="${param.duty eq 4}">selected</c:if>>사원</option>
						</select>
						<select name="state" id="state" onchange="this.form.submit()">
							<option value="0"<c:if test="${param.state eq 0}">selected</c:if>>전체</option>
							<option value="1"<c:if test="${param.state eq 1}">selected</c:if>>대기</option>
							<option value="2"<c:if test="${param.state eq 2}">selected</c:if>>진행</option>
							<option value="3"<c:if test="${param.state eq 3}">selected</c:if>>승인</option>
							<option value="4"<c:if test="${param.state eq 4}">selected</c:if>>반려</option>
							<option value="5"<c:if test="${param.state eq 5}">selected</c:if>>철회</option>
						</select>
					</c:if>
					
					<!-- 사원용 -->
					<c:if test="${ loginUser.duty != 0 }">
						<select name="astate" id="astate" onchange="this.form.submit()">
							<option value="0"<c:if test="${param.astate eq 0}">selected</c:if>>전체</option>
							<option value="1"<c:if test="${param.astate eq 1}">selected</c:if>>미결재</option>
							<option value="2"<c:if test="${param.astate eq 2}">selected</c:if>>승인</option>
							<option value="3"<c:if test="${param.astate eq 3}">selected</c:if>>반려</option>
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
			</form>
			<!-- == 서브타이틀 종료 ===================================================================== -->
			
			<!-- == 목록 테이블 시작 ===================================================================== -->
			<table class="tb_list">
				<caption class="blind">연차 내역</caption>
				<thead>
					<tr>
						<th class="col_w20">작성자</th>
						<th>내용</th>
						<th class="col_w15">작성일</th>
						<th class="col_w15">결재 상태</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="item" items="${ vacalist }">
					<tr>
						<td class="msg_name">
							<span class="dept">${ item.dept }</span>
							<strong>${ item.name }<span class="duty">${ item.duty }</span></strong>
						</td>
						<td class="tl msg_name">
							<a href="<%= request.getContextPath() %>/auth/view_vaca.do?vacaNO=${ item.vacaNO }">
								<!-- 사원용<문서 결재상태> -->
								<c:if test="${ loginUser.duty != 0 }">
									<c:choose>
										<c:when test="${ item.state == 0 }"><span class="dstate_wait">대기</span></c:when>
										<c:when test="${ item.state == 1 }"><span class="dstate_ing">진행</span></c:when>
										<c:when test="${ item.state == 2 }"><span class="dstate_ok">승인</span></c:when>
										<c:when test="${ item.state == 8 }"><span class="dstate_no">반려</span></c:when>
										<c:when test="${ item.state == 9 }"><span class="dstate_back">철회</span></c:when>
									</c:choose>
								</c:if>
								${ item.why }
							</a>
						</td>
						<td class="col_w20">${ item.wdate }</td>
						<!-- 사원용<결재자 결재상태> -->
						<c:if test="${ loginUser.duty != 0 }">
							<td>
								<c:choose>
									<c:when test="${ item.astate == 0 || item.astate == 1 }">
										<a href="<%= request.getContextPath() %>/auth/view_vaca.do?vacaNO=${ item.vacaNO }" class="astate_wait">
											<i class="fi fi-sr-check-circle"></i>결재하기
										</a>
									</c:when>
									<c:when test="${ item.astate == 2 }"><span class="state_ok">승인 완료</span></c:when>
									<c:when test="${ item.astate == 9 }"><span class="state_no">반려</span></c:when>
								</c:choose>
							</td>
						</c:if>
						<!-- 관리자용<문서 결재상태> -->
						<c:if test="${ loginUser.duty == 0 }">
							<td>
								<c:choose>
									<c:when test="${ item.state == 0 }"><span class="state_wait">대기</span></c:when>
									<c:when test="${ item.state == 1 }"><span class="state_ing">진행</span></c:when>
									<c:when test="${ item.state == 2 }"><span class="state_ok">승인</span></c:when>
									<c:when test="${ item.state == 8 }"><span class="state_no">반려</span></c:when>
									<c:when test="${ item.state == 9 }"><span class="state_back">철회</span></c:when>
								</c:choose>
							</td>
						</c:if>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<c:if test="${ empty vacalist }">
				<div class="td_nodata">
					<img alt="데이터가 없습니다" src="<%=request.getContextPath() %>/resources/img/nodata.png">
				</div>
			</c:if>
			<!-- == /목록 테이블 종료 ===================================================================== -->
			
			<!-- == 페이징 시작 ===================================================================== -->
			<c:if test="${ not empty vacalist }">
			<div class="paging">
				<ul>
					<c:if test="${ MaxPage > 10 }">
						<li>
							<a href="${pageContext.request.contextPath}/auth/list_vaca.do?pageNO=1
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
						<a href="${pageContext.request.contextPath}/auth/list_vaca.do?pageNO=${ pageNO-1 }
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
							<a href="${pageContext.request.contextPath}/auth/list_vaca.do?pageNO=${ i }
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
						<a href="${pageContext.request.contextPath}/auth/list_vaca.do?pageNO=${ pageNO+1 }
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
							<a href="${pageContext.request.contextPath}/auth/list_vaca.do?pageNO=${ MaxPage }
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
			<!-- == 페이징 종료 ===================================================================== -->
		</section>
		<!-- == /메인정보 종료 ========================================================================== -->
	</main>
	<!-- == /정보영역 종료 ================================================================================= -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>