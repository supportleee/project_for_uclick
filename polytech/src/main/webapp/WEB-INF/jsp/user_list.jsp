<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UC 사용자 관리 모듈</title>
<!-- bootstrap, jQuery -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<!-- custom javascript -->
<script src="<c:url value="/resources/js/selectbox.js"/>"></script>
<script src="<c:url value="/resources/js/checkbox.js"/>"></script>
<script src="<c:url value="/resources/js/validation.js"/>"></script>
<script src="<c:url value="/resources/js/buttonClick.js"/>"></script>
<script src="<c:url value="/resources/js/modal.js"/>"></script>
<!-- 한글 검색어 자동완성 -->
<script src="https://unpkg.com/hangul-js" type="text/javascript"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="<c:url value="/resources/js/autocomplete_korean.js"/>"></script>

<style>
.page-link {
	color: black;
}
</style>
</head>
<body>
	<!-------------------------------------------------------------------- header -------------------------------------------------------------------->
	<h1 class="font-weight-bold text-center my-4 text-dark">UC 사용자 관리 모듈</h1>
	<form class="form-inline justify-content-center" method="get" action="user_search">
		<select class="custom-select my-1 mr-sm-2" name="condition" required>
			<option value='' selected disabled hidden>조건</option>
			<option value="name">이름</option>
			<option value="tel">전화번호</option>
		</select>
		<input class="form-control my-1 mr-sm-2" type="text" name="keyword" id="keyword" required>
		<button type="submit" class="btn btn-secondary my-1 mr-sm-2">검색</button>
	</form>
	<hr>
	<!-------------------------------------------------------------------------------------------------------------------------------------------------->

	<!------------------------------------------------------------------ table header ------------------------------------------------------------------>
	<div class="row justify-content-center">
		<div class="row col-8 p-0 justify-content-between">
			<div class="m-0 row align-items-center">
				<!-- userModal button -->
				<button type="button" class="btn btn-secondary my-1" data-toggle="modal" data-target="#userModal" data-type="등록" data-whatever="0">사용자 등록</button>
			</div>
			<div class="m-0 row align-items-center">
				<select class="custom-select my-1 text-end" name="size" onChange="sizeChange(this)">
					<option value="5" <c:if test="${users.size eq 5 }">selected</c:if>>5건씩 보기</option>
					<option value="10" <c:if test="${users.size eq 10 }">selected</c:if>>10건씩 보기</option>
					<option value="15" <c:if test="${users.size eq 15 }">selected</c:if>>15건씩 보기</option>
				</select>
			</div>
		</div>
	</div>
	<!---------------------------------------------------------------------------------------------------------------------------------------------------->

	<!---------------------------------------------------------------------- table ----------------------------------------------------------------------->
	<form method="post" action="user_delete/selected" onsubmit="return deleteConfirm();">
		<div class="row justify-content-center">
			<table class="table table-hover col-8">
				<thead class="thead-dark text-center">
					<tr>
						<th><input type="checkbox" id="allCheck" onClick="allChk(this)" /></th>
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
									<td>
										<input type="checkbox" name="userIds" value="${user.id }">
									</td>
									<td>${user.id }</td>
									<td>
										<a href="user_view/${user.id }">${user.name }</a>
									</td>
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
		<c:if test="${!empty users.content }">
			<div class="row justify-content-center">
				<div class="row col-8 p-0 justify-content-between">
					<button type="submit" class="btn btn-secondary my-1">선택 삭제</button>
					<button type="button" class="btn btn-secondary my-1" onclick="location.href='downloadCSV'">내보내기</button>
				</div>
			</div>
		</c:if>
	</form>
	<!-------------------------------------------------------------------------------------------------------------------------------------------------------->

	<!---------------------------------------------------------------------- pagination ---------------------------------------------------------------------->
	<c:if test="${not empty users.content }">
		<div class="row justify-content-center">
			<nav>
				<fmt:parseNumber var="totalPages" integerOnly="true" type="number" value="${users.totalPages}" />
				<fmt:parseNumber var="firstPage" integerOnly="true" type="number" value="${users.number/10}" />
				<fmt:parseNumber var="totalPage_calc" integerOnly="true" type="number" value="${(totalPages-1)/10 }" />

				<ul class="pagination">
					<c:if test="${users.number ne 0 }">
						<li class="page-item">
							<a class="page-link" href="user_list?page=0&size=${users.size}"> 
								&lt;&lt;
							</a>
						</li>
					</c:if>
					<c:if test="${firstPage >= 1 }">
						<li class="page-item">
							<a class="page-link" href="user_list?page=${users.number-10 + (10-users.number%9) }&size=${users.size}">
								&lt;
							</a>
						</li>
					</c:if>
					<c:forEach var="i" begin="${(firstPage*10)+1}" end="${Math.min(totalPages, (firstPage*10)+10)}">
						<li class="page-item">
							<a class="page-link <c:if test="${users.number eq i-1 }">font-weight-bold</c:if>" href="user_list?page=${i-1}&size=${users.size}">${i}</a>
							</li>
					</c:forEach>
					<c:if test="${firstPage ne totalPage_calc }">
						<li class="page-item">
							<a class="page-link" href="user_list?page=${users.number+10 - (users.number%10) }&size=${users.size}"> 
								&gt;
							</a>
						</li>
					</c:if>
					<c:if test="${users.number ne totalPages-1}">
						<li class="page-item">
							<a class="page-link" href="user_list?page=${totalPages-1 }&size=${users.size}"> 
								&gt;&gt;
							</a>
						</li>
					</c:if>
				</ul>
			</nav>
		</div>
	</c:if>
	<!-------------------------------------------------------------------------------------------------------------------------------------------------------->

	<!----------------------------------------------------------------------- userModal ---------------------------------------------------------------------->
	<div class="modal fade" id="userModal" tabindex="-1" role="dialog" aria-labelledby="userModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="userModalLabel">사용자 등록</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form method="post" id="userForm" onsubmit="return validateUser('user_list');">
					<div class="modal-body">
						<div class="form-group">
							<label for="name" class="col-form-label">이름 :</label> 
							<input type="text" class="form-control" id="name" name="name" maxlength="6" placeholder="2~6자의 한글로 입력하세요." pattern="[가-힣]{2,6}" required> 
							<span class="text-danger" id="name_error"></span>
						</div>
						<div class="form-group">
							<label for="department" class="col-form-label">부서 :</label>
							<select class="form-control" id="department" name="department" onchange="categoryChange(this)" required>
								<option value='' selected disabled hidden>부서를 선택하세요.</option>
								<option value="영업부">영업부</option>
								<option value="기술사업부">기술사업부</option>
								<option value="UC사업부">UC사업부</option>
								<option value="경영관리부">경영관리부</option>
							</select>
							<span class="text-danger" id="department_error"></span>
						</div>
						<div class="form-group">
							<label for="team" class="col-form-label">팀 :</label>
							<select class="form-control" id="team" name="team" required>
								<option value='' disabled hidden>팀을 선택하세요.</option>
							</select>
							<span class="text-danger" id="team_error"></span>
						</div>
						<div class="form-group">
							<label for="rank" class="col-form-label">직위 :</label>
							<select class="form-control" id="rank" name="rank" required>
								<option value=''>직위를 선택하세요.</option>
								<option value="본부장">본부장</option>
								<option value="팀장">팀장</option>
								<option value="대리">대리</option>
								<option value="주임">주임</option>
								<option value="사원">사원</option>
								<option value="인턴">인턴</option>
							</select>
							<span class="text-danger" id="rank_error"></span>
						</div>
						<div class="form-group">
							<label for="age" class="col-form-label">나이 :</label> 
							<input type="number" class="form-control" id="age" name="age" min="17" max="100" placeholder="17~100 사이의 값을 입력하세요" required> 
							<span class="text-danger" id="age_error"></span>
						</div>
						<div class="form-group">
							<label for="email" class="col-form-label">이메일 :</label> 
							<input type="text" class="form-control" id="email" name="email" maxlength="40" placeholder="xxx@xxx.xxx 형식으로 입력하세요." pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required> <span class="text-danger" id="email_error"></span>
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
						<button type="submit" class="btn btn-primary"></button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-------------------------------------------------------------------------------------------------------------------------------------------------------->
</body>
</html>