<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script type="text/javascript">
function read(msgNO) {
	$.ajax({
		url : "./view_r.do",
		type : "get",
		data : "msgNO="+msgNO,
		success : function(result) {
			$("body").append(result);
		}
	});
}

function closeMsg() {
	window.location.reload();
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
					<li class="active">
						<a href="list_r.do">받은 쪽지</a>
					</li>
					<li>
						<a href="list_s.do">보낸 쪽지</a>
					</li>
				</ul>
			</nav>
			<!-- == /서브메뉴 종료 ===================================================================== -->

			<!-- == 서브타이틀 시작 ===================================================================== -->
			<form method="Get" action="list_r.do">
			<div class="tit_sub">
				<!-- 검색 필터 -->
				<div class="search_warp">
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
					<div class="search_box MR10">
						<input type="text" placeholder="이름을 입력해주세요" name="name_r" id="name" value="${ param.name_s }">
						<button type="submit">
							<i class="fi fi-br-search"></i>
						</button>
					</div>
				</div>
				<a href="write.do" class="btn_write">
					<i class="fi fi-sr-comment-pen"></i>
					<span>쪽지 쓰기</span>
				</a>
			</div>
			</form>
			<!-- == 서브타이틀 종료 ===================================================================== -->

			<!-- == 목록 테이블 시작 ===================================================================== -->
			<table class="tb_list">
				<caption class="blind">받은 쪽지 목록</caption>
				<thead>
					<tr>
						<th class="col_w20">보낸 사람</th>
						<th>내용</th>
						<th class="col_w15">날짜</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${ list }">
					<fmt:parseDate var="wdate" value="${ item.wdate }" pattern="yyyy-MM-dd'T'HH:mm" type="both"/>
						<tr>
							<td class="msg_name">
								<span class="dept">${ item.deptName }</span>
								<strong>${ item.name_s }<span class="duty">${ item.dutyName }</span></strong>
							</td>
							<td class="tl">
								<c:if test="${ item.state == 1 }">
									<a href="javascript:read(${item.msgNO})">
										${ item.note }
									</a>
								</c:if>
								<c:if test="${ item.state == 0 }">
									<a href="javascript:read(${item.msgNO})" class="new" >${ item.note }</a>
								</c:if>
							</td>
							<td><fmt:formatDate value="${ wdate }" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
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
							<a href="${pageContext.request.contextPath}/msg/list_r.do?pageNO=1
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
						<a href="${pageContext.request.contextPath}/msg/list_r.do?pageNO=${ pageNO-1 }
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
							<a href="${pageContext.request.contextPath}/msg/list_r.do?pageNO=${ i }
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
						<a href="${pageContext.request.contextPath}/msg/list_r.do?pageNO=${ pageNO+1 }
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
							<a href="${pageContext.request.contextPath}/msg/list_r.do?pageNO=${ MaxPage }
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