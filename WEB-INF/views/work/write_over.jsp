<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script>

	//결재자 사원 목록(popup) 쳬크박스 선택 시 정보 입력받기
	function getCheckboxValue(index, id, name, duty, dutyN)  {
		let Textid = "<input type='hidden' value="+id+" id='check"+index+"' name='checkIds'>";
		checkadd(index, Textid, id, name, duty, dutyN);
	};
	
	// 결재자 사원 목록(popup) 선택 목록 화면 입력 및 체크박스 해제시 정보 삭제
	function checkadd(index, Textid, id, name, duty, dutyN)  {
		if($("#"+index).is(":checked")){
			$("form").append(Textid);
			let text = "<li id='li"+id+"'><span class='duty'>"+duty+"</span><input type='hidden' class='duty"+dutyN+"' value='"+dutyN+"'><span class='name'>"+name+"</span><button type='button' class='btn_del' onclick='checkDelete("+index+","+id+", this)'><i class='fi fi-rr-cross-small'><span class='blind'>결재자 삭제</span></i></button></li>";
			$("#lists").append(text);
		} else {
			const check = document.getElementById('check'+index);
			const li = document.getElementById('li'+id);
			$(check).remove();
			$(li).remove();
		}
	};
	
	// (popup) 실행 전 결재 목록에서 결재자Ｘ 삭제 및 체크박스 연동 해제
		
	function checkBlank() {
		var t = 0 ; // 팀장
		var b = 0 ; // 부장
		var d = 0 ; // 대표
		
		// 결재권자 확인
		var level = $("#loginduty").val();
		jQuery('.duty3').each(function(i){
			if(jQuery(this).val()){
		        t++;
		    }
		});
		jQuery('.duty2').each(function(i){
			if(jQuery(this).val()){
		        b++;
		    }
		});
		jQuery('.duty1').each(function(i){
			if(jQuery(this).val()){
		        d++;
		    }
		});
		if(level == 4)
		{
			if(t < 1) { Swal.fire("결재권자에 팀장을 추가해주세요"); return false; }
			if(b < 1) { Swal.fire("결재권자에 부장을 추가해주세요"); return false; }
			if(d < 1) { Swal.fire("결재권자에 대표를 추가해주세요"); return false; }
		}
		if(level == 3)
		{
			if(b < 1) { Swal.fire("결재권자에 부장을 추가해주세요"); return false; }
			if(d < 1) { Swal.fire("결재권자에 대표를 추가해주세요"); return false; }
		}
		if(level == 2)
		{
			if(d < 1) { Swal.fire("결재권자에 대표를 추가해주세요"); return false; }
		}
		
		let box = $("#note");
		var list = $("#lists"); 
		var start = $("#start");
		var end = $("#end");
		if (list.children().length == 0) {
// 			alert("결재선을 추가해주세요");
			Swal.fire('결재선을 추가해주세요');
			return false;
		}
		if (!start.val() || !end.val()) {
// 			alert("시간을 입력해주세요");
			Swal.fire('시간을 입력해주세요');
			return false;
		}
		if (start.val() > end.val()) {
// 			alert("종료시간이 시작시간보다 빠를 수 없습니다");
			Swal.fire('종료시간이 시작시간보다 빠를 수 없습니다');
			return false;
		}
		if (start.val() == end.val()) {
// 			alert("시작시간과 종료시간이 같을 수 없습니다");
			Swal.fire('시작시간과 종료시간이 같을 수 없습니다');
			return false;
		}
		if (!box.val()) {
// 			alert("근무내용을 입력해주세요");
			Swal.fire('근무내용을 입력해주세요');
			box.focus();
			return false;
		}
	}
	
	function checkDelete(index, id) {
		$("input:checkbox[id='"+index+"']").prop("checked", false);
		const check = document.getElementById('check'+index);
		const li = document.getElementById('li'+id);
		$(check).remove();
		$(li).remove();
	};
	
	// (popup) 실행 class명 변경
	function userAdd() {
		document.getElementById('popup').className += 'active';
	};
	
	//(popup) 닫기 class명 변경
	function popupDelete() {
		document.getElementById('popup').classList.remove('active');
	};

	window.onload = function(){
		$('#date').val(new Date().toISOString().slice(0,10));
	}
	
// 	function timeCheck() {
// 		var start = $("#start").val()
// 		var end = $("#end").val()
		
// 		if (start > end) {
// // 			alert("종료시간이 시작시간보다 빠를 수 없습니다");
// 			Swal.fire('종료시간이 시작시간보다 빠를 수 없습니다');
// 		}
// 		if (start == end) {
// // 			alert("시작시간과 종료시간이 같을 수 없습니다");
// 			Swal.fire('시작시간과 종료시간이 같을 수 없습니다');
// 		}
		
// 	}
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
			<!-- == 서브타이틀 시작 ===================================================================== -->
			<h3 class="title">초과근무 신청</h3>
			<!-- == 서브타이틀 종료 ===================================================================== -->
			
			<!-- == 작성 폼 시작 ===================================================================== -->
			<form action="write_over.do" method="post" onsubmit="return checkBlank();">
				<div class="write_wrap">
					<input type="hidden" name="id" value="${ loginUser.id }" readonly>
					<input type="hidden" id="loginduty" value="${ loginUser.duty }" readonly>		
					<ul class="write_container">
						<li>
							<strong class="tit">결재</strong>
							<div class="content">
								<ul class="list_auth" id="lists">
								</ul>
								<button type="button" class="btn_add" title="결재자 추가" onclick="userAdd()">
									<i class="fi fi-ss-user-add color_purple"></i>
								</button>
							</div>
						</li>
						<li>
							<strong class="tit">일시</strong>
							<div class="content">
								<ul class="date">
									<li>
										<input type="date" name="date" id="date">
									</li>
									<li>
										<select name="start" class="time" id="start">
											<option value="18:00">18:00</option>
											<option value="19:00">19:00</option>
											<option value="20:00">20:00</option>
											<option value="21:00">21:00</option>
											<option value="22:00">22:00</option>
											<option value="23:00">23:00</option>
										</select>
									</li>
									<li>
										<span class="slash"> - </span>
									</li>
									<li>
										<select name="end" class="time" id="end">
											<option value="19:00">19:00</option>
											<option value="20:00">20:00</option>
											<option value="21:00">21:00</option>
											<option value="22:00">22:00</option>
											<option value="23:00">23:00</option>
											<option value="23:59">24:00</option>
										</select>
									</li>
								</ul>
							</div>
						</li>
						<li>
							<strong class="tit tit_note">내용</strong>
							<div class="content">
								<textarea placeholder="근무 내용을 입력해주세요" name="note" id="note"></textarea>
							</div>
						</li>
					</ul>
					<!-- = 사원목록 popup 시작 = -->
					<div id="popup" class="">
<!-- 				<div id="popup" class="active">  -->
						<div class="popup_warp popup_sendlist">
							<div class="top_warp">
								<button type="button" class="btn_close" onclick="popupDelete()">
									<i class="fi fi-br-cross"><span class="blind">팝업창 닫기</span></i>
								</button>
							</div>
							<div class="info_warp">
								<div class="tit_container" id='result'>
									<strong>사원 목록</strong>
<!-- 									<div class="search_box"> -->
<!-- 										<input type="text" placeholder="이름을 입력해주세요"> -->
<!-- 										<button type="submit"> -->
<!-- 											<i class="fi fi-br-search"></i> -->
<!-- 										</button> -->
<!-- 									</div> -->
								</div>
								<div class="info_container">
									<div class="dept_wrap">
										<ul>
											<li class="active">
												<strong class="dept">${ loginUser.deptName }</strong>
												<c:set var="count" value="0" />
												<c:forEach var="item" items="${ olists }" varStatus="status">
													<c:if test="${ loginUser.dept eq item.dept && loginUser.duty gt item.duty }">
														<c:set var="count" value="${ count + 1 }" />
													</c:if>
												</c:forEach>
												<span class="num">${ count }</span>
											</li>
										</ul>
									</div>
									<div class="check_wrap">
										<ul>
											<c:forEach var="item" items="${ olists }" varStatus="status">
											<c:if test="${ loginUser.dept eq item.dept && loginUser.duty gt item.duty }">
											<li>
												<div class="checkbox_wrap">
													<input type="checkbox" id="${ status.index }" class="btn_checkbox blind" name="checks" onclick="getCheckboxValue('${ status.index }','${ item.id }','${ item.name }','${ item.dutyName }',${ item.duty },this)">
													<label for="${status.index}">
														<span class="icon_checkbox"  id="${ status.index }"></span>
														<input type='hidden' value=${ item.id } id='addId'>
														<input type='hidden' value=${ item.name } id='addName'>
														<input type='hidden' value=${ item.deptName } id='addDeptName'>
														<strong class="name">${ item.name }</strong><span class="duty">${ item.dutyName }</span>
													</label>
												</div>
											</li>
											</c:if>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
							<div class="btn_wrap MT10">
								<ul>
									<li>
										<button type="button" id="close" class="btn_light" onclick="popupDelete()">닫기</button>
									</li>
									<li>
										<button type="button" class="btn_primary" onclick="popupDelete()">추가</button>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<!-- = 사원목록 popup 종료 = -->
				</div>
				<!-- == 버튼 시작 ===================================================================== -->
				<div class="btn_wrap MT60">
					<ul>
						<li>
							<a href="javascript:window.history.back()" class="btn_light">목록으로</a>
						</li>
						<li>
							<button type="submit" class="btn_primary">작성완료</button>
						</li>
					</ul>
				</div>
				<!-- == /버튼 종료 ===================================================================== -->
			</form>
			<!-- == /작성 폼 종료 ===================================================================== -->
		</section>
		<!-- == /메인정보 종료 ========================================================================== -->
	</main>
	<!-- == /정보영역 종료 ================================================================================= -->
</body>
<!-- ==== /body 종료 ===================================================================================== -->
</html>