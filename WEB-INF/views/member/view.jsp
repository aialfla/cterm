<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	<!-- == 사원 정보 popup 시작 ===================================================================== -->
	<!-- 팝업 활성화 active -->
	<div id="popup" class="active">
		<div class="popup_warp popup_member">
			<div class="top_warp">
				<strong>사원 정보</strong>
				<button type="button" class="btn_close" onclick="popupDelete()">
					<i class="fi fi-br-cross"><span class="blind">팝업창 닫기</span></i>
				</button>
			</div>
			<div class="info_warp">
				<ul>
					<c:if test="${ loginUser.duty == 0 }">
						<li>
							<strong class="tit_m">사원번호</strong>
							<div class="content">
								${ view.id }
							</div>
						</li>
					</c:if>
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
						<strong class="tit_m">
							<c:if test="${ empty view.retiredate }">입사일</c:if>
							<c:if test="${ not empty view.retiredate }">재직기간</c:if>
						</strong>
						<div class="content">
							<fmt:parseDate var="joindate" value="${ view.joindate }" pattern="yyyy-MM-dd'T'HH:mm"/>
							<fmt:formatDate value="${ joindate }" pattern="yyyy-MM-dd"/>
							<c:if test="${ not empty view.retiredate }"> ~ </c:if>
							<fmt:parseDate var="retiredate" value="${ view.retiredate }" pattern="yyyy-MM-dd'T'HH:mm"/>
							<fmt:formatDate value="${ retiredate }" pattern="yyyy-MM-dd"/>
						</div>
					</li>
					<li>
						<strong class="tit_m">연락처</strong>
						<div class="content">
							<c:if test="${ empty view.tel }">-</c:if>
							${ view.tel }
						</div>
					</li>
					<li>
						<strong class="tit_m">이메일</strong>
						<div class="content">
							<c:if test="${ empty view.mail }">-</c:if>
							${ view.mail }
						</div>
					</li>
					<li>
						<strong class="tit_m">주소</strong>
						<div class="content">
							<c:if test="${ empty view.addr }">-</c:if>
							${ view.addr }
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- = /사원 정보 popup 종료 ===================================================================== -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>