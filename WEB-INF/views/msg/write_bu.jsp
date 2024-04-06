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
			<h3 class="title">쪽지 쓰기</h3>
			<!-- == 서브타이틀 종료 ===================================================================== -->
			
			<!-- == 작성 폼 시작 ===================================================================== -->
			<form action="write.do" method="post">
				<div class="write_wrap">
					<ul class="write_container">
						<li>
							<strong class="tit">받는 사람</strong>
							<div class="content">
								<ul class="list_auth" id="lists">
									<li>
										<span class="duty">부장</span>
										<span class="name">김철수</span>
										<button type="button" class="btn_del">
											<i class="fi fi-rr-cross-small"><span class="blind">결재자 삭제</span></i>
										</button>
									</li>
									<li>
										<span class="duty">부장</span>
										<span class="name">김철수</span>
										<button type="button" class="btn_del">
											<i class="fi fi-rr-cross-small"><span class="blind">결재자 삭제</span></i>
										</button>
									</li>
								</ul>
								<button type="button" class="btn_add" title="받는 사람" onclick="userAdd()">
									<i class="fi fi-ss-user-add color_purple"></i>
								</button>
							</div>
						</li>
						<li>
							<strong class="tit tit_note">내용</strong>
							<div class="content">
								<textarea name="note" placeholder="내용을 입력해주세요"></textarea>
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
								<div class="tit_container">
									<strong>사원 목록</strong>
									<div class="search_box">
										<input type="text" placeholder="이름을 입력해주세요">
										<button type="submit">
											<i class="fi fi-br-search"></i>
										</button>
									</div>
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
										<div class="checkbox_wrap tit_check">
											<input type="checkbox" id="select_all" class="btn_checkbox blind">
											<label for="select_all">
												<span class="icon_checkbox"></span>
												<strong class="all">전체 선택</strong>
											</label>
										</div>
										<ul>
											<c:forEach var="item" items="${ olists }" varStatus="status">
											<c:if test="${ loginUser.dept eq item.dept && loginUser.duty gt item.duty }">
											<li>
												<div class="checkbox_wrap">
													<input type="checkbox" id="${ status.index }" class="btn_checkbox blind" name="checks" onclick="getCheckboxValue('${ status.index }','${ item.id }','${ item.name }','${ item.dutyName }',this)">
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
									<div class="select_wrap">
										<div class="tit_select">
											<strong>결재자</strong>
											<span>2</span>
										</div>
										<div class="list_select">
											<ul>
												<li>
													<div class="person">
														김철수<span>부장</span>
													</div>
													<button type="button" class="btn_del">
														<i class="fi fi-rr-cross-small"><span class="blind">결재자 삭제</span></i>
													</button>
												</li>
												<li>
													<div class="person">
														김철수<span>부장</span>
													</div>
													<button type="button" class="btn_del">
														<i class="fi fi-rr-cross-small"><span class="blind">결재자 삭제</span></i>
													</button>
												</li>
											</ul>
										</div>
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
							<a href="list_s.do" class="btn_light">목록으로</a>
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