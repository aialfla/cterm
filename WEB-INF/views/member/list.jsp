<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script type="text/javascript">

// popup 내용
function read(id) {
	$.ajax({
		url : "./view.do",
		type : "get",
		data : "id=" + id,
		success : function(result) {
			$("body").append(result);
		}
	});
}

// popup 닫기
function popupDelete() {
	$("#popup").remove();
}
//  function popupDelete() {
// 	$('#popup').removeClass('active');
// }

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
					<li class="active">
						<a href="<%= request.getContextPath() %>/member/list.do">사원 조회</a>
					</li>
					<li>
						<a href="<%= request.getContextPath() %>/member/mine.do">내 정보</a>
					</li>
				</ul>
			</nav>
			<!-- == /서브메뉴 종료 ===================================================================== -->

			<!-- == 서브타이틀 시작 ===================================================================== -->
			<div class="tit_sub">
				<!-- 검색 필터 -->
				<form method="Get" action="list.do">
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
					<div class="search_box">
						<input type="text" placeholder="이름을 입력해주세요" name="name" id="name" value="${ param.name }">
						<button type="submit">
							<i class="fi fi-br-search"></i>
						</button>
					</div>
				</div>
				</form>
				<!-- 작성 버튼 -->
				<c:if test="${ loginUser.id == 100000 }">
					<a href="<%= request.getContextPath() %>/member/join.do" class="btn_write">
						<i class="fi fi-sr-id-badge"></i>
						<span>사원 등록</span>
					</a>
				</c:if>
			</div>
			<!-- == 서브타이틀 종료 ===================================================================== -->

			<!-- == 목록 테이블 시작 ===================================================================== -->
			<table class="tb_list">
				<caption class="blind">사원 목록</caption>
				<thead>
					<tr>
						<th class="col_w20">이름</th>
						<th class="col_w25">연락처</th>
						<th class="col_w25">이메일</th>
						<th class="col_w10">쪽지</th>
						<th class="col_w10">조회</th>
						<c:if test="${ loginUser.id == 100000 }">
							<th class="col_w10">수정</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${ list }">
						<tr>
							<td class="msg_name">
								<span class="dept">${ item.deptName }</span>
								<strong>${ item.name }<span class="duty">${ item.dutyName }</span></strong>
							</td>
							<td>
								<c:if test="${ empty item.tel }">-</c:if>
								${ item.tel }
							</td>
							<td>
								<c:if test="${ empty item.mail }">-</c:if>
								${ item.mail }
							</td>
							<td>
								<a href="<%= request.getContextPath() %>/msg/write_re.do?id=${item.id}&duty=${item.duty}&name=${item.name}" class="btn_table" title="쪽지쓰기">
									<i class="fi fi-sr-envelope"></i>
								</a>
							</td>
							<td>
								<a href="javascript:read(${ item.id })" class="btn_table" title="조회하기">
									<i class="fi fi-sr-search-alt"></i>
								</a>
							</td>
							<c:if test="${ loginUser.id == 100000 }">
								<td>
									<a href="<%= request.getContextPath() %>/member/modify_admin.do?id=${ item.id }" class="btn_table red" title="수정하기">
										<i class="fi fi-sr-user-gear"></i>
									</a>
								</td>
							</c:if>
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
							<a href="${pageContext.request.contextPath}/member/list.do?pageNO=1
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
						<a href="${pageContext.request.contextPath}/member/list.do?pageNO=${ pageNO-1 }
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
							<a href="${pageContext.request.contextPath}/member/list.do?pageNO=${ i }
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
						<a href="${pageContext.request.contextPath}/member/list.do?pageNO=${ pageNO+1 }
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
							<a href="${pageContext.request.contextPath}/member/list.do?pageNO=${ MaxPage }
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
			
			
			<!-- == 사원 정보 popup 시작 ===================================================================== -->
			<!-- 팝업 활성화 active -->
			<div id="popup">
				<div class="popup_warp popup_member">
					<div class="top_warp">
						<strong>사원 정보</strong>
						<button type="button" class="btn_close" onclick="popupDelete()">
							<i class="fi fi-br-cross"><span class="blind">팝업창 닫기</span></i>
						</button>
					</div>
					<div class="info_warp">
						<ul>
							<li>
								<strong class="tit_m">사원번호</strong>
								<div class="content">
									${ view.id }
								</div>
							</li>
							<li>
								<strong class="tit_m">이름</strong>
								<div class="content">
									${ view.name }
								</div>
							</li>
							<li>
								<strong class="tit_m">부서</strong>
								<div class="content">
									${ view.deptName }
								</div>
							</li>
							<li>
								<strong class="tit_m">직급</strong>
								<div class="content">
									${ view.dutyName }
								</div>
							</li>
							<li>
								<strong class="tit_m">상태</strong>
								<div class="content">
									${ view.stateName }
								</div>
							</li>
							<li>
								<strong class="tit_m">재직기간</strong>
								<div class="content">
									<fmt:parseDate var="joindate" value="${ view.joindate }" pattern="yyyy-MM-dd'T'HH:mm"/>
									<fmt:formatDate value="${ joindate }" pattern="yyyy-MM-dd"/>
									 ~ 
									<fmt:parseDate var="retiredate" value="${ view.retiredate }" pattern="yyyy-MM-dd'T'HH:mm"/>
									<fmt:formatDate value="${ retiredate }" pattern="yyyy-MM-dd"/>
								</div>
							</li>
							<li>
								<strong class="tit_m">연락처</strong>
								<div class="content">
									${ view.tel }
								</div>
							</li>
							<li>
								<strong class="tit_m">이메일</strong>
								<div class="content">
									${ view.mail }
								</div>
							</li>
							<li>
								<strong class="tit_m">주소</strong>
								<div class="content">
									${ view.addr }
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- = /사원 정보 popup 종료 ===================================================================== -->
		
		</section>
		<!-- == /메인정보 종료 ========================================================================== -->
	</main>
	<!-- == /정보영역 종료 ================================================================================= -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>