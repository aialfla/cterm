<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
		<article id="total_warp">
			<ul>
				<li class="box_commute">
					<div class="info">
						<h3>오늘 근무 시간</h3>
						<strong <c:if test="${ not empty wv.start }"> class="color_purple" </c:if>>
						<c:if test="${ empty wv.start }">00:00</c:if>
							<fmt:parseDate var="start" value="${ wv.start }" pattern="HH:mm:ss"/>
							<fmt:formatDate value="${ start }" pattern="HH:mm"/>
						</strong>
						<span class="slash <c:if test="${ not empty wv.start }"> color_purple </c:if>"> ~ </span>
						<strong <c:if test="${ not empty wv.end }"> class="color_purple" </c:if>>
						<c:if test="${ empty wv.end }">00:00</c:if>
							<fmt:parseDate var="end" value="${ wv.end }" pattern="HH:mm:ss"/>
							<fmt:formatDate value="${ end }" pattern="HH:mm"/>
						</strong>
					</div>
					<div class="btns_commute">
						<c:if test="${ loginUser.duty ne 0 }">
							<button type="button" class="btn_commute <c:if test="${ empty wv.start }"> active </c:if> MB10" onclick="location.href='add_start.do'">출근하기</button>
							<button type="button" class="btn_commute <c:if test="${ not empty wv.start && empty wv.end }"> active </c:if>" onclick="location.href='add_end.do'">퇴근하기</button>
						</c:if>
					</div>
				</li>
				<li>
					<h3>금주 누적 근무</h3>
					<strong>
<%-- 					<c:set var="ww" value="${ fn:split(weeklyWork, ':')[0] }"/> --%>
<%-- 					<c:if test="${ ww ge 52 }">  --%>
<!-- 					52시간 -->
<%-- 					</c:if> --%>
<%-- 					<c:if test="${ ww lt 52 }"> --%>
						<c:if test="${ weeklyWork == 'null' }">00:00:00</c:if>
						<c:if test="${ weeklyWork != 'null' }">${ weeklyWork }</c:if>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${ !weeklyWork }"></c:if> --%>
<%-- 					<c:if test="${ weeklyWork }">${ weeklyWork }</c:if> --%>
					</strong>
					<div>/ 52시간</div>
				</li>
				<li>
					<h3>금주 누적 초과근무</h3>
					<strong>
					<c:if test="${ not empty weeklyOver }">${ weeklyOver }시간</c:if>
					<c:if test="${ empty weeklyOver }">없음</c:if>
					</strong>
					<div></div>
				</li>
			</ul>
		</article>