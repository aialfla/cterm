<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="true" %>
<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<%
	// 줄바꿈 변경
	pageContext.setAttribute("cn", "\n");
%>
<script type="text/javascript">
</script>
<!-- ==== /script 종료 ==================================================================================== -->
</head>
<!-- ==== /head 종료 ==================================================================================== -->

<!-- ==== body 시작 ===================================================================================== -->
<body>
<!-- == 쪽지 읽기 popup 시작 ===================================================================== -->
 		<div id="popup" class="active">
				<div class="popup_warp popup_msg">
					<div class="top_warp">
						<strong>쪽지 읽기</strong>
						<button type="button" class="btn_close" onclick="closeMsg()">
							<i class="fi fi-sr-cross"><span class="blind">팝업창 닫기</span></i>
						</button>
					</div>
					<div class="info_warp">
						<div class="tit_container">
							<div class="sender">
								<div class="icon_person"><i class="fi fi-sr-user PT2"></i></div>
								<p>
									<span class="rank">
										<c:if test="${ msg.duty_s == 0 }">
											관리자
										</c:if>
										<c:if test="${ msg.duty_s == 1 }">
											대표
										</c:if>
										<c:if test="${ msg.duty_s == 2 }">
											부장
										</c:if>
										<c:if test="${ msg.duty_s == 3 }">
											팀장
										</c:if>
										<c:if test="${ msg.duty_s == 4 }">
											사원
										</c:if>
									</span>
									<span class="name">${ msg.name_s }
									</span>
								</p>
							</div>
							<fmt:parseDate var="wdate" value="${ msg.wdate }" pattern="yyyy-MM-dd'T'HH:mm" type="both"/>
							<time><fmt:formatDate value="${ wdate }" type="date" pattern="yyyy-MM-dd HH:mm"/></time>
						</div>
						<div class="info_container" >
							<p>${fn:replace(msg.note,cn,"<br/>")}</p>
						</div>
					</div>
					<div class="btn_wrap MT10">
						<ul>
							<li>
								<a href="javascript:closeMsg()" class="btn_light">닫기</a>
							</li>
							<li>
								<button type="button" onclick="location.href='write_re.do?id=${msg.id}&duty=${msg.duty_s}&name=${msg.name_s}'" class="btn_primary">답장</button>
							</li>
						</ul>
					</div>
	 			</div>
			</div> 
			<!-- == 쪽지 읽기 popup 종료 ===================================================================== -->

			</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>


