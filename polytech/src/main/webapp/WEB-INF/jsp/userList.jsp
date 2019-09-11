<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UC 사용자 관리 모듈</title>
</head>
<body>
	<h1>사용자 리스트</h1>
	<hr>
	<c:choose>
		<c:when test="${empty users}">
			<div>등록된 사용자가 없습니다.</div>
		</c:when>
		<c:otherwise>
			<table>
				<tr>
					<th>번호</th>
					<th>이름</th>
					<th>부서</th>
					<th>팀</th>
					<th>직위</th>
					<th>나이</th>
					<th>이메일</th>
					<th>등록일</th>
				</tr>
				<c:forEach items="${users }" var="user">
					<tr>
						<td>${user.id }</td>
						<td><a href="./oneviewUser/${user.id }">${user.id }</a></td>
						<td>${user.department }</td>
						<td>${user.team }</td>
						<td>${user.rank }</td>
						<td>${user.age }</td>
						<td>${user.email }</td>
						<td>${user.reg_date }</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<input type="button" value="사용자 등록" onclick="location.href='newUser.html'">
</body>
</html>