<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="true" %>

<!-- ==== head 시작 ==================================================================================== -->
<%@ include file="../../include/head.jsp"%>
<!-- ==== script 시작 ==================================================================================== -->
<script type="text/javascript">

	//결재자 사원 목록(popup) 쳬크박스 선택 시 정보 입력받기
	function getCheckboxValue(index, id, name, duty)  {
		let Textid = "<input type='hidden' value="+id+" id='check"+index+"' name='checkIds'>";
		checkadd(index, Textid, id, name, duty);
	};
	
	// 결재자 사원 목록(popup) 선택 목록 화면 입력 및 체크박스 해제시 정보 삭제
	function checkadd(index, Textid, id, name, duty) {	
		if($("#"+index).is(":checked")){
			$("form").append(Textid);
			let text = "<li id='li"+id+"'><span class='duty'>"+duty+"</span><span class='name'>"+name+"</span><button type='button' class='btn_del' onclick='checkDelete("+index+","+id+", this)'><i class='fi fi-rr-cross-small'><span class='blind'>결재자 삭제</span></i></button></li>";
			$("#lists").append(text);
		} else {
			const check = document.getElementById('check'+index);
			const li = document.getElementById('li'+id);
			$(check).remove();
			$(li).remove();
		}
	};
	
	// (popup) 실행 전 결재 목록에서 결재자Ｘ 삭제 및 체크박스 연동 해제
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
	
	//목록 버튼
	var preUrl = document.referrer;
	function back() {
		if (preUrl.slice(-7) == "rite.do") 
		{
//	 		window.history.go(-2);
			window.location.href = "<%= request.getContextPath() %>/msg/list_r.do";
		}
		else {
			window.history.back();
		}
	}
	
	// 전체
	function clickDept(dept) {
		document.getElementById("dept_0").classList.remove("active");
		document.getElementById("dept_1").classList.remove("active");
		document.getElementById("dept_2").classList.remove("active");
		document.getElementById("dept_3").classList.remove("active");
		document.getElementById("dept_"+dept).className += "active";
		switch(dept) {
		case 0 : 
			$("#dept_click_0").removeClass("hidden");
			$("#dept_click_1").removeClass("hidden");
			$("#dept_click_2").removeClass("hidden");
			$("#dept_click_3").removeClass("hidden");
			$("#admin").removeClass("hidden");
			$("#all_click_0").removeClass("hidden");
			$("#all_click_1").addClass("hidden");
			$("#all_click_2").addClass("hidden");
			$("#all_click_3").addClass("hidden");
			break;
		case 1 : 
			$("#admin").addClass("hidden");
			$("#dept_click_0").addClass("hidden");
			$("#dept_click_1").removeClass("hidden");
			$("#dept_click_2").addClass("hidden");
			$("#dept_click_3").addClass("hidden");
			$("#all_click_0").addClass("hidden");
			$("#all_click_1").removeClass("hidden");
			$("#all_click_2").addClass("hidden");
			$("#all_click_3").addClass("hidden");
			break;
		case 2 : 
			$("#admin").addClass("hidden");
			$("#dept_click_0").addClass("hidden");
			$("#dept_click_1").addClass("hidden");
			$("#dept_click_2").removeClass("hidden");
			$("#dept_click_3").addClass("hidden");
			$("#all_click_0").addClass("hidden");
			$("#all_click_1").addClass("hidden");
			$("#all_click_2").removeClass("hidden");
			$("#all_click_3").addClass("hidden");
			break;
		case 3 : 
			$("#admin").addClass("hidden");
			$("#dept_click_0").addClass("hidden");
			$("#dept_click_1").addClass("hidden");
			$("#dept_click_2").addClass("hidden");
			$("#dept_click_3").removeClass("hidden");
			$("#all_click_0").addClass("hidden");
			$("#all_click_1").addClass("hidden");
			$("#all_click_2").addClass("hidden");
			$("#all_click_3").removeClass("hidden");
			break;
		}
	}
	
	function checkBlank() {
		let box = $("#note");
		var list = $("#lists"); 
		if (list.children().length == 0) {
// 			alert("받는 사람을 추가해주세요");
			Swal.fire('받는 사람을 추가해주세요');
			return false;
		}
		if (!box.val()) {
// 			alert("쪽지 내용을 입력해주세요");
			box.focus();
			Swal.fire('쪽지 내용을 입력해주세요');
			return false;
		}
		return true;
	}
	
	function checkAll(checkAll) {
		var checkboxes 
			= document.getElementsByName('checks');
		var check_cnt = checkboxes.length;
		var checked_cnt = 0;
		checkboxes.forEach((checkbox) => {
			if(checkbox.checked) {
				checked_cnt++;
			}
		})
		if (checked_cnt == check_cnt || checked_cnt == 0) { 
			if ($("#select_all").is(":checked")) {
				$("#select_dept_1").prop("checked",true);
				$("#select_dept_2").prop("checked",true);
				$("#select_dept_3").prop("checked",true);
			} else {
				$("#select_dept_1").prop("checked",false);
				$("#select_dept_2").prop("checked",false);
				$("#select_dept_3").prop("checked",false);
			}
			checkboxes.forEach((checkbox) => {
				checkbox.click();
			})
		} else {
			checkboxes.forEach((checkbox) => {
				if (!checkbox.checked) {
					checkbox.click();
				}
			})
		}
	}
	
	function check_1(check_1) {
		var checkboxes
			= document.getElementsByClassName('box_dept_1');
		var check_cnt = checkboxes.length;
		var checked_cnt = 0;
		Array.from(checkboxes).forEach((checkbox) => {
			if(checkbox.checked) {
				checked_cnt++;
			}
		})
		if (checked_cnt == check_cnt || checked_cnt == 0) { 
			Array.from(checkboxes).forEach((checkbox) => {
				checkbox.click();
			})
		} else {
			Array.from(checkboxes).forEach((checkbox) => {
				if (!checkbox.checked) {
					checkbox.click();
				}
			})
		}
	}
	
	function check_2(check_2) {
		var checkboxes
			= document.getElementsByClassName('box_dept_2');
		var check_cnt = checkboxes.length;
		var checked_cnt = 0;
		Array.from(checkboxes).forEach((checkbox) => {
			if(checkbox.checked) {
				checked_cnt++;
			}
		})
		if (checked_cnt == check_cnt || checked_cnt == 0) { 
			Array.from(checkboxes).forEach((checkbox) => {
				checkbox.click();
			})
		} else {
			Array.from(checkboxes).forEach((checkbox) => {
				if (!checkbox.checked) {
					checkbox.click();
				}
			})
		}
	}
	
	function check_3(check_3) {
		var checkboxes
			= document.getElementsByClassName('box_dept_3');
		var check_cnt = checkboxes.length;
		var checked_cnt = 0;
		Array.from(checkboxes).forEach((checkbox) => {
			if(checkbox.checked) {
				checked_cnt++;
			}
		})
		if (checked_cnt == check_cnt || checked_cnt == 0) { 
			Array.from(checkboxes).forEach((checkbox) => {
				checkbox.click();
			})
		} else {
			Array.from(checkboxes).forEach((checkbox) => {
				if (!checkbox.checked) {
					checkbox.click();
				}
			})
		}
	}
	
	$(document).on("click", "input:checkbox[name=checks]", function() {
		
		var chks = document.getElementsByName("checks");
		var chksChecked = 0;
		
		for(var i=0; i<chks.length; i++) {
			var cbox = chks[i];
			
			if(cbox.checked) {
				chksChecked++;
			}
		}
		
		if(chks.length == chksChecked){
			$("#select_all").prop("checked", true);
		}else{
			$("#select_all").prop("checked",false);
		}
		
	});
	
	$(document).on("click", "input:checkbox.box_dept_1", function() {
		var chks = document.getElementsByClassName("box_dept_1");
		var chksChecked = 0;
		 
		for(var i=0; i<chks.length; i++) {
			var cbox = chks[i];
			
			if(cbox.checked) {
				chksChecked++;
			}
		}
		
		if(chks.length == chksChecked){
			$("#select_dept_1").prop("checked", true);
		}else{
			$("#select_dept_1").prop("checked",false);
		}
		
	});
	
	$(document).on("click", "input:checkbox.box_dept_2", function() {
		var chks = document.getElementsByClassName("box_dept_2");
		var chksChecked = 0;
		
		for(var i=0; i<chks.length; i++) {
			var cbox = chks[i];
			
			if(cbox.checked) {
				chksChecked++;
			}
		}
		
		if(chks.length == chksChecked){
			$("#select_dept_2").prop("checked", true);
		}else{
			$("#select_dept_2").prop("checked",false);
		}
		
	});
	
	$(document).on("click", "input:checkbox.box_dept_3", function() {
		
		var chks = document.getElementsByClassName("box_dept_3");
		var chksChecked = 0;
		
		for(var i=0; i<chks.length; i++) {
			var cbox = chks[i];
			
			if(cbox.checked) {
				chksChecked++;
			}
		}
		
		if(chks.length == chksChecked){
			$("#select_dept_3").prop("checked", true);
		}else{
			$("#select_dept_3").prop("checked",false);
		}
		
	});
	
</script>
<%
	String value = "";
%>
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
			<h3 class="title">쪽지 쓰기</h3>
			<!-- == 서브타이틀 종료 ===================================================================== -->
			
			<!-- == 작성 폼 시작 ===================================================================== -->
			<form action="write.do" method="post" onsubmit="return checkBlank();">
				<div class="write_wrap">
					<ul class="write_container">
						<li>
							<strong class="tit">받는 사람</strong>
							<div class="content">
								<ul class="list_auth" id="lists">
								</ul>
								<button type="button" class="btn_add" title="받는 사람" onclick="userAdd()">
									<i class="fi fi-ss-user-add color_purple"></i>
								</button>
							</div>
						</li>
						<li>
							<strong class="tit tit_note">내용</strong>
							<div class="content">
								<textarea name="note" id="note" placeholder="내용을 입력해주세요"></textarea>
							</div>
						</li>
					</ul>
					<!-- = 사원목록 popup 시작 = -->
					<div id="popup" class="">
						<div class="popup_warp popup_sendlist">
							<div class="top_warp">
								<button type="button" class="btn_close" onclick="popupDelete()">
									<i class="fi fi-br-cross"><span class="blind">팝업창 닫기</span></i>
								</button>
							</div>
							<div class="info_warp">
								<div class="tit_container" id="dept_value">
									<strong>사원 목록</strong>
									<div class="search_box">
<!-- 										<input type="text" placeholder="이름을 입력해주세요"> -->
<!-- 										<button type="submit"> -->
<!-- 											<i class="fi fi-br-search"></i> -->
<!-- 										</button> -->
									</div>
								</div>
								<div class="info_container">
									<div class="dept_wrap">
										<ul>
											<li class="active" id="dept_0" onclick="clickDept(0,this)">
												<strong class="dept">전체</strong>
												<c:set var="count" value="0" />
												<c:forEach var="item" items="${ olists }" varStatus="status">
														<c:set var="count" value="${ count + 1 }" />
												</c:forEach>
												<span class="num">${ count }</span>
											</li>
											<li class="" id="dept_1" onclick="clickDept(1,this)">
												<strong class="dept">기획부</strong>
												<c:set var="count" value="0" />
												<c:forEach var="item" items="${ olists }" varStatus="status">
													<c:if test="${ item.dept eq 1 }">
														<c:set var="count" value="${ count + 1 }" />
													</c:if>
												</c:forEach>
												<span class="num">${ count }</span>
											</li>
											<li class="" id="dept_2" onclick="clickDept(2,this)">
												<strong class="dept">디자인부</strong>
												<c:set var="count" value="0" />
												<c:forEach var="item" items="${ olists }" varStatus="status">
													<c:if test="${ item.dept eq 2 }">
														<c:set var="count" value="${ count + 1 }" />
													</c:if>
												</c:forEach>
												<span class="num">${ count }</span>
											</li>
											<li class="" id="dept_3" onclick="clickDept(3,this)">
												<strong class="dept">개발부</strong>
												<c:set var="count" value="0" />
												<c:forEach var="item" items="${ olists }" varStatus="status">
													<c:if test="${ item.dept eq 3 }">
														<c:set var="count" value="${ count + 1 }" />
													</c:if>
												</c:forEach>
												<span class="num">${ count }</span>
											</li>
										</ul>
									</div>
									<div class="check_wrap">
										<div class="checkbox_wrap tit_check" id="all_click_0">
											<input type="checkbox" id="select_all" onclick='checkAll(this)' class="btn_checkbox blind"> 
											<label for="select_all">
												<span class="icon_checkbox"></span>
												<strong class="all">전체 선택</strong>
											</label>
										</div>
										<div class="checkbox_wrap tit_check hidden" id="all_click_1">
											<input type="checkbox" id="select_dept_1" onclick='check_1(this)' class="btn_checkbox blind">
											<label for="select_dept_1">
												<span class="icon_checkbox"></span>
												<strong class="all">기획부 전체</strong>
											</label>
										</div>
										<div class="checkbox_wrap tit_check hidden" id="all_click_2">
											<input type="checkbox" id="select_dept_2" onclick='check_2(this)' class="btn_checkbox blind">
											<label for="select_dept_2">
												<span class="icon_checkbox"></span>
												<strong class="all">디자인부 전체</strong>
											</label>
										</div>
										<div class="checkbox_wrap tit_check hidden" id="all_click_3">
											<input type="checkbox" id="select_dept_3" onclick='check_3(this)' class="btn_checkbox blind">
											<label for="select_dept_3">
												<span class="icon_checkbox"></span>
												<strong class="all">개발부 전체</strong>
											</label>
										</div>
										<ul class="">
											<li>
												<div class="checkbox_wrap">
													<input type="checkbox" id="100000" class="btn_checkbox blind" name="checks" onclick="getCheckboxValue('100000','100000','관리자','',this)">
													<label for="100000" class="" id="admin">
														<span class="icon_checkbox" id="100000"></span>
														<input type='hidden' value='100000' id='addId'>
														<input type='hidden' value='관리자' id='addName'>
														<input type='hidden' value='관리부' id='addDeptName'>
														<strong class="name">관리자</strong><span class="duty"></span>
													</label>
												</div>
											</li>
										</ul>
										<ul id="dept_click_1" class="">
											<c:forEach var="item" items="${ olists }" varStatus="status">
												<c:if test="${ item.dept eq 1 }">
													<li>
														<div class="checkbox_wrap">
															<input type="checkbox" id="${ status.index }" class="btn_checkbox blind box_dept_1" name="checks" onclick="getCheckboxValue('${ status.index }','${ item.id }','${ item.name }','${ item.dutyName }',this)">
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
										<ul id="dept_click_2" class="">
											<c:forEach var="item" items="${ olists }" varStatus="status">
												<c:if test="${ item.dept eq 2 }">
													<li>
														<div class="checkbox_wrap">
															<input type="checkbox" id="${ status.index }" class="btn_checkbox blind box_dept_2" name="checks" onclick="getCheckboxValue('${ status.index }','${ item.id }','${ item.name }','${ item.dutyName }',this)">
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
										<ul id="dept_click_3" class="">
											<c:forEach var="item" items="${ olists }" varStatus="status">
												<c:if test="${ item.dept eq 3 }">
													<li>
														<div class="checkbox_wrap">
															<input type="checkbox" id="${ status.index }" class="btn_checkbox blind box_dept_3" name="checks" onclick="getCheckboxValue('${ status.index }','${ item.id }','${ item.name }','${ item.dutyName }',this)">
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
										<button type="button" id="close" class="btn_light" onclick="popupDelete()"> 닫기 </button>
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
							<a href="javascript:back()" class="btn_light">돌아가기</a>
						</li>
						<li>
							<button type="submit" class="btn_primary">쪽지쓰기</button>
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