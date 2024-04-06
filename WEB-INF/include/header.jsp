<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

	<!-- == 상단창 시작 =============================================================================== -->
	<header id="header">
		<h1 class="logo">
			<a href="<%= request.getContextPath() %>/main.do">
				<span class="blind">cterm 인사관리시스템</span>
			</a>
		</h1>
		<div class="info">
			<ul class="alarm_list">
				<c:if test="${ loginUser.duty < 4 and loginUser.duty != 0 }">
					<li>
						<a href=
							<c:choose>
								<c:when test="${ countDocu != 0 }">
									"<%= request.getContextPath() %>/auth/list_docu.do" 
								</c:when>
								<c:when test="${ countDocu == 0 && countVaca != 0 }">
									"<%= request.getContextPath() %>/auth/list_vaca.do" 
								</c:when>
								<c:when test="${ countDocu == 0 && countVaca == 0 }">
									"<%= request.getContextPath() %>/auth/list_over.do" 
								</c:when>
							</c:choose>
							title="결재 승인">
							<i class="fi fi-rr-stamp"></i>
							<c:if test="${ countDocu + countVaca + countOver ne 0 }">
								<span class="num">${ countDocu + countVaca + countOver }</span>
							</c:if>
						</a>
					</li>
				</c:if>
				<li>
					<a href="<%= request.getContextPath() %>/msg/list_r.do" title="받은 쪽지">
						<i class="fi fi-rr-envelope"></i>
						<c:if test="${ countNotRead ne 0 }">
							<span class="num">${ countNotRead }</span>
						</c:if>
					</a>
				</li>
			</ul>
			<a href="<%= request.getContextPath() %>/member/mine.do" title="내정보" class="info_mine">
				<div class="icon_person">
					<c:if test="${ loginUser.id == 100000 }">				
						<i class="fi fi-sr-settings PT4"></i>
					</c:if>
					<c:if test="${ loginUser.id != 100000 }">				
						<i class="fi fi-sr-user PT2 fts18"></i>
					</c:if>
				</div>
				<h2>
					<span class="rank">
						${ loginUser.dutyName }
					</span>
					<span class="name">${ loginUser.name }</span>
					<c:if test="${ loginUser == null }">
						<c:redirect url="/"></c:redirect>
					</c:if>
				</h2>
			</a>
		</div>
	</header>
	<!-- == /상단창 종료 ================================================================================== -->
