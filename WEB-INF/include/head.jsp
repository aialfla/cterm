<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="kr">
<!-- ==== head 시작 ==================================================================================== -->
<head>
	<meta charset="UTF-8">
	<title>cterm</title>
	
	<!-- 모달 라이브러리 -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>
	
	<!-- css -->
	<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/fullcalendar/main.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/common.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/board.css">

	<!-- jquery-3.7.1 -->
	<script
		src="https://code.jquery.com/jquery-3.7.1.js"
		integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
		crossorigin="anonymous">
	</script>
	
<%-- 	<script src="<%= request.getContextPath() %>/resources/js/jquery-3.7.1.js"></script> --%>
<!-- 	<script type="text/javascript"></script> -->

	
