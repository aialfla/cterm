<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<%
	// 줄바꿈 변경
	pageContext.setAttribute("cn", "\n");
%>
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
			<!-- == 상세정보 시작 ===================================================================== -->
			<div class="view_wrap cf">
				<!-- 글 내용 -->
				<div class="info_warp">
					<h3 class="view_tit">
						<span class="type">기안</span>${ view.title }
					</h3>
					<ul class="view_info">
						<li class="view_name">
							<strong>작성자</strong>
							<span>[${ view.deptName }] ${ view.name } ${ view.dutyName }</span>
						</li>
						<li class="view_date">
							<strong>등록일</strong>
							<span>
								<fmt:parseDate var="wdate" value="${ view.wdate }" pattern="yyyy-MM-dd'T'HH:mm"/>
								<fmt:formatDate value="${ wdate }" pattern="yyyy-MM-dd HH:mm"/>
							</span>
						</li>
						<!-- 첨부파일 없을 시 숨김 처리-->
						<c:if test="${ not empty Alist }">
							<li class="view_file">
								<strong>첨부파일</strong>
								<ul>
									<c:forEach var="item" items="${ Alist }">
									<li>
										<a href="<%= request.getContextPath() %>/resources/docuUpload/${ item.newName }" title="첨부파일 다운" download="${ item.orgName }">
											<i class="fi fi-rr-file-download"></i>
											<span>${ item.orgName }</span>
										</a>
									</li>
									</c:forEach>
								</ul>
							</li>
						</c:if>
					</ul>
					<div class="view_note">
						<p>${fn:replace(view.note,cn,"<br/>")}</p>
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
						<a href="<%= request.getContextPath() %>/auth/list_docu.do" class="btn_light">목록으로</a>
					</li>
					<c:if test="${ login.state == 1 }">
						<li>
							<a href="<%= request.getContextPath() %>/auth/approve_docu.do?docuNO=${ view.docuNO }" class="btn_primary">승인하기</a>
						</li>
						<li>
							<a href="<%= request.getContextPath() %>/auth/reject_docu.do?docuNO=${ view.docuNO }" class="btn_secon">반려하기</a>
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