<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UC 사용자 관리 모듈</title>
<link rel="stylesheet" href="/WEB-INF/jsp/css/table.css">
<script type="text/javascript" src="/WEB-INF/jsp/js/textCheck.js"></script>
</head>
<body>
	<h1>사용자 등록</h1>
	<hr>
	<form method="post">
		<table>
			<tr>
				<td>이름</td>
				<td><input type="text" name="name" maxlength="20" required></td>
			</tr>
			<tr>
				<td>부서</td>
				<td><input type="text" name="department" maxlength="20"
					required></td>
			</tr>
			<tr>
				<td>팀</td>
				<td><input type="text" name="team" maxlength="20" required></td>
			</tr>
			<tr>
				<td>직위</td>
				<td><input type="text" name="rank" maxlength="20" required></td>
			</tr>
			<tr>
				<td>나이</td>
				<td><input type="number" name="age" min="17" max="100" required></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="text" name="rank" maxlength="20" required></td>
			</tr>
		</table>
		<input type="button" value="등록"
			onclick="location.href='saveUser.html'"> <input type="button"
			value="취소" onclick="location.href='userList.html'">
	</form>
</body>
</html>