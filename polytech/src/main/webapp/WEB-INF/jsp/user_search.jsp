<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UC 사용자 관리 모듈</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/selectbox.js"/>"></script>
<style>
.page-link {
	color: black;
}
</style>
</head>
<body>
	<h1 class="font-weight-bold text-center my-4">
		<a class="text-dark" href="/user_list">UC 사용자 관리 모듈</a>
	</h1>
	<form class="form-inline justify-content-center" method="get"
		action="/user_search">
		<select class="custom-select my-1 mr-sm-2" name="condition" required>
			<option value='' selected disabled hidden>조건</option>
			<option value="name"
				<c:if test="${condition eq 'name' }">selected</c:if>>이름</option>
			<option value="tel"
				<c:if test="${condition eq 'tel' }">selected</c:if>>전화번호</option>
		</select> <input class="form-control my-1 mr-sm-2" type="text" name="keyword"
			value="${keyword }" required>
		<button type="submit" class="btn btn-secondary my-1 mr-sm-2">검색</button>
	</form>
	<hr>
	<div class="row justify-content-center">
		<div class="row col-8 p-0 justify-content-between">
			<div class="m-0 row align-items-center">
				<h5 class="m-0 font-weight-bold text-dark">
					검색 결과 : 총
					<c:choose>
						<c:when test="${empty users.totalElements }">0</c:when>
						<c:otherwise>${users.totalElements }</c:otherwise>
					</c:choose>
					건
				</h5>
			</div>
			<div class="m-0 row align-items-center">
				<select class="custom-select my-1 text-end" name="size"
					onChange="sizeChange(this)">
					<option value="5" <c:if test="${users.size eq 5 }">selected</c:if>>5건씩
						보기</option>
					<option value="10"
						<c:if test="${users.size eq 10 }">selected</c:if>>10건씩 보기</option>
					<option value="15"
						<c:if test="${users.size eq 15 }">selected</c:if>>15건씩 보기</option>
				</select>
			</div>
		</div>
	</div>


	<div class="row justify-content-center">
		<table class="table table-hover col-8">
			<thead class="thead-dark text-center">
				<tr>
					<th>번호</th>
					<th>이름</th>
					<th>부서</th>
					<th>팀</th>
					<th>직위</th>
					<th>나이</th>
					<th>이메일</th>
				</tr>
			</thead>
			<tbody class="text-center">
				<c:choose>
					<c:when test="${empty users.content}">
						<tr>
							<td colspan="8">등록된 사용자가 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${users.content }" var="user">
							<tr>
								<td>${user.id }</td>
								<td><a href="./user_view/${user.id }">${user.name }</a></td>
								<td>${user.department }</td>
								<td>${user.team }</td>
								<td>${user.rank }</td>
								<td>${user.age }</td>
								<td>${user.email }</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>

	<c:if test="${not empty users.content }">
		<div class="row justify-content-center">
			<nav>
				<fmt:parseNumber var="totalPages" integerOnly="true" type="number"
					value="${users.totalPages}" />
				<fmt:parseNumber var="firstPage" integerOnly="true" type="number"
					value="${users.number/10}" />

				<ul class="pagination">
					<c:if test="${users.number ne 0}">
						<li class="page-item"><a class="page-link"
							href="user_search?condition=${condition }&keyword=${keyword }&first=${Math.max(0, (firstPage*10)-1)}&size=${users.size}"><span
								aria-hidden="true">&lt;</span></a></li>
					</c:if>
					<c:forEach var="i" begin="${(firstPage*10)+1}"
						end="${Math.min(totalPages, (firstPage*10)+10)}">
						<li class="page-item"><a
							class="page-link <c:if test="${users.number eq i-1 }">font-weight-bold</c:if>"
							href="user_search?condition=${condition }&keyword=${keyword }&first=${i-1}&size=${users.size}">${i}</a></li>
					</c:forEach>
					<c:if test="${users.number ne users.totalPages-1}">
						<li class="page-item"><a class="page-link"
							href="user_search?condition=${condition }&keyword=${keyword }&first=${Math.min(users.number, (firstPage*10)+10)}&size=${users.size}">
								<span aria-hidden="true">&gt;</span>
						</a></li>
					</c:if>
				</ul>
			</nav>
		</div>
	</c:if>
</body>
</html>