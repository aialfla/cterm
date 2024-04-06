<%@page import="javax.naming.Context"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
   
    <script src="<%= request.getContextPath() %>/resources/fullcalendar/main.js"></script>
	<script type="text/javascript">

	document.addEventListener('DOMContentLoaded', function() {
	$.ajax({
			type:"get",
			 	url:"<%=request.getContextPath() %>/calendar.do",
	 			dataType:"json" ,
	   			success : function(data){
   				console.log(data);
   				
   		    	var calendarEl = document.getElementById('calendar');
   		        var calendar = new FullCalendar.Calendar(calendarEl, {
 
   		            locale: "ko",
   		        	eventClick: function(info) {
//   		        	    alert('Event: ' + info.event.title);
   		        	    var date = new Date(info.event.start);
   		        		date.setHours(date.getHours() + 9);
// 		        	    alert('date: ' + date.toISOString());
   		        	    location.href="<%=request.getContextPath() %>/main.do?day="+date.toISOString().split("T")[0];
 					},
   		            selectable: false,
   		            selectMirror: true,
   		            
   		            dayCellContent: function (info) {
   		                var number = document.createElement("a");
   		                number.classList.add("fc-daygrid-day-number");
   		                number.innerHTML = info.dayNumberText.replace("일", '').replace("日","");
   		                if (info.view.type === "dayGridMonth") {
   		                  return {
   		                    html: number.outerHTML
   		                  };
   		                }
   		                return {
   		                  domNodes: []
   		                };
   		            },
	   		        navLinks: false,
   		            editable: false,
   		            dayMaxEvents: false, // allow "more" link when too many events
   		            fixedWeekCount: false,
   		            
   		            events: data
   		        });
   		        calendar.render();
   		        
   		        var list_item = $(".fc-list-event-title");
   		     	list_item.each(function(index,item){
   		     	var html = $(item).find('a:eq(0)').text();
   		     	html.replace("|","<br>");
   		     	$(item).find('a:eq(0)').text(html.replace("|","<br>"))
   		     	console.log($(item).find('a:eq(0)').text());
   		     	});
   			} 
		});
    });
	</script>
	
	<script>
		const dpTime = function () {
			const now = new Date()
			const curr = new Date();
			const utc = 
			      curr.getTime() + 
			      (curr.getTimezoneOffset() * 60 * 1000);
			const KR_TIME_DIFF = 9 * 60 * 60 * 1000;
			const kr_curr = 
			      new Date(utc + (KR_TIME_DIFF));
			let hours = now.getHours()
			let minutes = now.getMinutes()
			let seconds = now.getSeconds()
			if (hours < 10) {
				hours = '0' + hours
			}
			if (minutes < 10) {
				minutes = '0' + minutes
			}
			if (seconds < 10) {
				seconds = '0' + seconds
			}
			document.querySelector('#time').innerHTML = hours + ":" + minutes + ":" + seconds
		}
		setInterval(dpTime, 100)  // 1초마다 함수 실행되도록 설정
	</script>
<!-- ==== /script 종료 ==================================================================================== -->
</head>
<!-- ==== /head 종료 ==================================================================================== -->
<!-- ==== body 시작 ===================================================================================== -->
<body>
	<!-- 상단바 -->
	<%@ include file="../include/header.jsp"%>
	<!-- 네비 -->
	<%@ include file="../include/nav.jsp"%>
	<!-- == 정보영역 시작 ================================================================================= -->
	<main id="main">
		<!-- = 토탈정보 시작 ============================================================================= -->
		<article id="total_warp">
			<ul>
				<li>
					<h3>현재 시간</h3>
					<c:set var="now" value="<%=new java.util.Date() %>"/>
					<strong id="time"><fmt:formatDate value="" pattern="HH:mm:ss"/>00:00:00</strong>
					<c:if test="${ loginUser.duty ne 0 }">
						<div>
							<button type="button" class="btn_commute_s <c:if test="${ empty wv.start }"> active </c:if> " onclick="location.href='<%= request.getContextPath() %>/work/add_start.do'">출근하기</button>
							<button type="button" class="btn_commute_s <c:if test="${ not empty wv.start && empty wv.end }"> active </c:if>" onclick="location.href='<%= request.getContextPath() %>/work/add_end.do'">퇴근하기</button>
						</div>
					</c:if>
				</li>
				<li>
					<h3>결재 문서</h3>
					<strong class="color_red">${ countVaca + countDocu + countOver }건</strong>
<!-- 					<div>내 문서 2건</div> -->
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
					</strong>
					<div>/ 52시간</div>
				</li>
				<li>
					<h3>금년 잔여 연차</h3>
					<strong>${ login.vaca - use }일</strong>
					<div>/ ${ login.vaca }일</div>
				</li>
			</ul>
		</article>
		<!-- == /토탈정보 종료 ========================================================================== -->

		<!-- == 메인정보 시작 ========================================================================== -->
		<section id="main_warp">
			<div class="calendar_wrap">
				<!-- 캘린더 -->
				<div id='calendar'></div>
				
				
 				<!-- 캘린더 우측 상세화면 -->
				<div class="calendar_more">
					<c:if test="${ day == null }">
						<h3 class="tit">${ today }</h3>
					</c:if>
					<c:if test="${ day != null}">
						<h3 class="tit">${ dayMonth }월 ${ dayDay }일</h3>
					</c:if>
					<ul>
						<c:forEach var="item" items="${ list }">
							<li class="todaylist">
								<div class="member_name">
									<span class="dept">${ item.deptName }</span>
									<strong class="name">${ item.name }<span class="duty">${ item.dutyName }</span></strong>
								</div>
								<div class="why">${ item.why }</div>
							</li>
						</c:forEach>
						<c:if test="${ empty list }">
							<li class="nodata">
								<img alt="연차 사용자가 없습니다" src="<%=request.getContextPath() %>/resources/img/novaca.png">
							</li>
						</c:if>
					</ul>
				</div>
			</div>
			
			
<%-- 			<img alt="" src="<%=request.getContextPath() %>/resources/img/main_calender.jpg" style="width: 100%;"> --%>
			
		</section>
		<!-- == /메인정보 종료 ========================================================================== -->
	</main>
	<!-- == /정보영역 종료 ================================================================================= -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>