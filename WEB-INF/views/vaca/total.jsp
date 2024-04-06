<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
		<!-- = 토탈정보 시작 ============================================================================= -->
		<article id="total_warp">
			<ul>
<!-- 				<li class="col_w50"> -->
				
<!-- 					<h3>연차 승인 현황</h3> -->
<!-- 					<strong>23.10.27(금) ~ 23.10.27(금)</strong> -->
<!-- 					<div class="color_red">진행중</div> -->
<!-- 				</li> -->
				<li>
					<h3>최근 연차 신청</h3>
					<strong class="color_purple">
					<c:choose>
						<c:when test="${ latest.state != 0 && latest.state != 1 && latest.state != 2  && latest.state != 8 && latest.state != 9 }">
						없음
						</c:when>
						<c:when test="${ latest.state == 0 }">
						대기
						</c:when>
						<c:when test="${ latest.state == 1 }">
						진행중
						</c:when>
						<c:when test="${ latest.state == 2 }">
						승인
						</c:when>
						<c:when test="${ latest.state == 8 }">
						반려
						</c:when>
						<c:when test="${ latest.state == 9 }">
						철회
						</c:when>
					</c:choose>
					</strong>
					<c:if test="${ latest.sday != latest.eday }">
						<div>${ latest.sday } ~ ${ latest.eday }</div>
					</c:if>
					<c:if test="${ latest.sday == latest.eday }">
						<div>${ latest.eday }</div>
					</c:if>
				</li>
				<li>
					<h3>잔여 연차</h3>
					<strong class="color_red">${ login.vaca - use }일</strong>
					<div>D-${ dday }</div>
				</li>
				<li>
					<h3>사용 연차</h3>
					<strong>${ use }일</strong>
				</li>
				<li>
					<h3>총 연차</h3>
					<strong>${ login.vaca }일</strong>
				</li>
			</ul>
		</article>
		<!-- == /토탈정보 종료 ========================================================================== -->