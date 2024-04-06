<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script type="text/javascript">

//(popup) 실행 class명 변경
function popupOpen(hid, name) {
	$.ajax({
			type: "get",
			dataType:"json",
			url: "<%=request.getContextPath() %>/vaca/history.do",
			data : { id : hid },
			success : function(data) {
				let text = "";
				$.each(data , function(i, item){
					$.each(item, function(key, value){
						text = "<li> <strong class='tit'>"+item.sday+" ~ "+item.eday+"</strong> <div class='content'> "+item.why+" </div> <span class='day'>"+item.day+"일</span> </li>";
					});
					if (data != "")
					{
						$("#nohis").remove();
					}
					$("#his").append(text);
				});
		  	}
	});
	
	let title = "<strong>"+name+"님의 연차 내역</strong><button type='button' class='btn_close' onclick='popupDelete()'><i class='fi fi-sr-cross'><span class='blind'>팝업창 닫기</span></i></button>";
	$("#hisname").append(title);
	document.getElementById('popup').className += 'active';
};

//(popup) 닫기 class명 변경
function popupDelete() {
	$("#hisname").empty();
	$("#his").empty();
// 	let text = "<li id='nohis'>없음</li>";
	let text = "<li id='nohis'><img alt='데이터가 없습니다' src='<%=request.getContextPath() %>/resources/img/nodata.png'></li>";
	$("#his").append(text);
	document.getElementById('popup').classList.remove('active');
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
		<!-- = 토탈정보 시작 ============================================================================= -->
		<c:if test="${ loginUser.duty != 0}" >
		<%@ include file="./total.jsp"%>
		</c:if>
		<!-- == /토탈정보 종료 ========================================================================== -->

		<!-- == 메인정보 시작 ========================================================================== -->
		<section id="main_warp">
			<!-- == 서브메뉴 시작 ===================================================================== -->
			<nav id="nav_sub">
				<ul class="menu_sub">
					<c:if test="${ loginUser.duty != 0}">
					<li>
						<a href="<%= request.getContextPath() %>/vaca/list_mine.do">내 연차</a>
					</li>
					</c:if>
					<c:if test="${ loginUser.duty == 0}">
					<li class="active">
						<a href="<%= request.getContextPath() %>/vaca/list.do">연차 조회</a>
					</li>
					</c:if>
				</ul>
			</nav>
			<!-- == /서브메뉴 종료 ===================================================================== -->

			<!-- == 서브타이틀 시작 ===================================================================== -->
			<div class="tit_sub">
				<form action="list.do" method="get">
				<!-- 검색 필터 -->
				<div class="search_warp MR10">
					<select name="dept" id="dept" onchange="this.form.submit()">
						<c:if test="${ loginUser.duty == 0}" >
							<option value="0"<c:if test="${param.dept eq 0}">selected</c:if>>전체</option>
							<option value="1"<c:if test="${param.dept eq 1}">selected</c:if>>기획부</option>
							<option value="2"<c:if test="${param.dept eq 2}">selected</c:if>>디자인부</option>
							<option value="3"<c:if test="${param.dept eq 3}">selected</c:if>>개발부</option>
						</c:if>
					</select>

					<select name="duty" id="duty" onchange="this.form.submit()">
					<c:if test="${ loginUser.duty == 0}">
						<option value="0"<c:if test="${param.duty eq 0}">selected</c:if>>전체</option>
						<option value="1"<c:if test="${param.duty eq 1}">selected</c:if>>대표</option>
						<option value="2"<c:if test="${param.duty eq 2}">selected</c:if>>부장</option>
						<option value="3"<c:if test="${param.duty eq 3}">selected</c:if>>팀장</option>
						<option value="4"<c:if test="${param.duty eq 4}">selected</c:if>>사원</option>
					</c:if>
					</select>
					<div class="search_box">
						<input type="text" placeholder="이름을 입력해주세요" name="name" id="name" value="${ param.name }">
						<button type="submit">
							<i class="fi fi-br-search"></i>
						</button>
					</div>
				</div>
				</form>
				<c:if test="${ loginUser.duty == 0}" >
				<a href="<%= request.getContextPath() %>/vaca/addVacation.do" class="btn_write"><i class="fi fi-sr-calendar-lines-pen"></i>연차 부여</a>
				</c:if>
			</div>
			<!-- == 서브타이틀 종료 ===================================================================== -->

			<!-- == 목록 테이블 시작 ===================================================================== -->
			<table class="tb_list">
				<caption class="blind">초과 근무 내역</caption>
				<thead>
					<tr>
						<th>작성자</th>
						<th>입사일</th>
						<th>총 연차</th>
						<th>사용 연차</th>
						<th>잔여 연차</th>
						<th>내역</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="item" items="${ vacalist }">
						<tr>
							<td class="msg_name">
								<span class="dept">${ item.deptName }</span>
								<strong>${ item.name }<span class="duty">${ item.dutyName }</span></strong>
							</td>
							<td>${ item.join }</td>
							<td>${ item.vaca }일</td>
							<td>${ item.usevaca }일</td>
							<td>${ item.ingvaca }일</td>
							<td>
								<button type="button" class="btn_table" title="조회하기" onclick="popupOpen('${ item.id }','${ item.name }', this)">
								<i class="fi fi-sr-search-alt"></i></button>
							</td>
						</tr>
				</c:forEach>
				</tbody>
			</table>
			<c:if test="${ empty vacalist }">
				<div class="td_nodata">
					<img alt="데이터가 없습니다" src="<%=request.getContextPath() %>/resources/img/nodata.png">
				</div>
			</c:if>
			<div id="popup" class="">
				<div class="popup_warp popup_member">
					<div class="top_warp" id="hisname">
						
						
					</div>
					<div class="info_warp">
						<ul id="his">
							<li id="nohis">
								<img alt="데이터가 없습니다" src="<%=request.getContextPath() %>/resources/img/nodata.png">
							</li>
						</ul>
					</div>
				</div>
			</div>				
			<!-- == /목록 테이블 종료 ===================================================================== -->
			
			<!-- == 페이징 시작 ===================================================================== -->
			<c:if test="${ not empty vacalist }">
			<div class="paging">
				<ul>
					<c:if test="${ MaxPage > 10 }">
						<li>
							<a href="${pageContext.request.contextPath}/vaca/list.do?pageNO=1
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
						<a href="${pageContext.request.contextPath}/vaca/list.do?pageNO=${ pageNO-1 }
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
							<a href="${pageContext.request.contextPath}/vaca/list.do?pageNO=${ i }
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
						<a href="${pageContext.request.contextPath}/vaca/list.do?pageNO=${ pageNO+1 }
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
							<a href="${pageContext.request.contextPath}/vaca/list.do?pageNO=${ MaxPage }
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