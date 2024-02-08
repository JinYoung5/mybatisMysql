<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글삭제</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body>
<div class="page-main-style">
	<h2>글삭제</h2>
	<form:form action="delete.do" modelAttribute="boardVO">
		<form:hidden path="num"/>
		<ul>
			<li>
				<label for="passwd">비밀번호</label>
				<form:password path="passwd"/>
				<form:errors path="passwd" cssClass="error-color"/>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="삭제">
			<input type="button" value="홈으로"
			       onclick="location.href='list.do'">
		</div>
	</form:form>
</div>
</body>
</html>











