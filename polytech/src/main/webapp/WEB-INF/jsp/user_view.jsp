<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UC 사용자 관리 모듈</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/selectbox.js"/>"></script>
<script src="<c:url value="/resources/js/validation.js"/>"></script>
<style>
.page-link {
	color: black;
}
</style>
</head>
<body onload="onloadSelect('${user.department}', '${user.team }', '${user.rank }')">
	<h1 class="font-weight-bold text-center my-4 text-dark">
		<a class="text-dark" href="/user_list">UC 사용자 관리 모듈</a>
	</h1>
	<h3 class="text-center font-weight-bold p-1 text-dark">사용자 상세 정보</h3>
	<hr>
	<div class="row justify-content-center">
		<div class="row col-8 p-0 justify-content-between">
			<div class="row align-items-center col-6">
				<h5 class="font-weight-bold text-dark">사용자 정보</h5>
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
					<th colspan="2">비고</th>
				</tr>
			</thead>
			<tbody class="text-center">
				<tr>
					<td class="align-middle">${user.id }</td>
					<td class="align-middle">${user.name }</td>
					<td class="align-middle">${user.department }</td>
					<td class="align-middle">${user.team }</td>
					<td class="align-middle">${user.rank }</td>
					<td class="align-middle">${user.age }</td>
					<td class="align-middle">${user.email }</td>
					<td class="align-middle">
						<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#user_modifyModal">수정</button>
					</td>
					<td>
						<form method="POST" action="/user_delete/${user.id}?_method=DELETE">
							<button type="submit" class="btn btn-secondary">삭제</button>
						</form>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="user_modifyModal" tabindex="-1" role="dialog" aria-labelledby="user_modifyModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="user_modifyModalLabel">사용자 수정</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form method="post" action="../user_save">
					<div class="modal-body">
						<div class="form-group">
							<label for="name" class="col-form-label">이름 :</label> <input type="text" class="form-control" id="name" name="name" maxlength="6" value="${user.name }" placeholder="2~6자의 한글로 입력하세요." pattern="[가-힣]{2,6}" required>
						</div>
						<div class="form-group">
							<label for="department" class="col-form-label">부서 :</label>
							<select class="form-control" id="department" name="department" onchange="categoryChange(this)" required>
								<option value="" selected disabled hidden>부서를 선택하세요.</option>
								<option value="영업부">영업부</option>
								<option value="기술사업부">기술사업부</option>
								<option value="UC사업부">UC사업부</option>
								<option value="경영관리부">경영관리부</option>
							</select>
						</div>
						<div class="form-group">
							<label for="team" class="col-form-label">팀 :</label>
							<select class="form-control" id="team" name="team" required>
							</select>
						</div>
						<div class="form-group">
							<label for="rank" class="col-form-label">직위 :</label>
							<select class="form-control" id="rank" name="rank" required>
								<option value="본부장">본부장</option>
								<option value="팀장">팀장</option>
								<option value="대리">대리</option>
								<option value="주임">주임</option>
								<option value="사원">사원</option>
								<option value="인턴">인턴</option>
							</select>
						</div>
						<div class="form-group">
							<label for="age" class="col-form-label">나이 :</label> <input type="number" class="form-control" id="age" name="age" min="17" max="100" value="${user.age }" placeholder="17~100 사이의 값을 입력하세요" required>
						</div>
						<div class="form-group">
							<label for="email" class="col-form-label">이메일 :</label> <input type="email" class="form-control" id="email" name="email" maxlength="40" value="${user.email }" placeholder="xxx@xxx.xxx 형식으로 입력하세요." pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
						<input type="hidden" name="id" value="${user.id }">
						<button type="submit" class="btn btn-primary">수정</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- --------------------- -->
	<br>
	<br>
	<br>
	<div class="row justify-content-center">
		<div class="row col-8 p-0 justify-content-between">
			<div class="row align-items-center col-6">
				<h5 class="font-weight-bold text-dark">전화기 정보</h5>
			</div>
			<div class="m-0 row align-items-center">
				<button type="button" class="btn btn-secondary my-1" data-toggle="modal" data-target="#phone_insertModal">추가</button>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="phone_insertModal" tabindex="-1" role="dialog" aria-labelledby="phone_insertModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="phone_insertModalLabel">전화기 추가</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form method="post" action="../phone_save/${user.id }">
					<div class="modal-body">
						<div class="form-group">
							<label for="type" class="col-form-label">종류 :</label>
							<select class="form-control" id="type" name="type" required>
								<option value="" selected disabled hidden>종류를 선택하세요.</option>
								<option value="모바일">모바일</option>
								<option value="IP전화">IP전화</option>
								<option value="FAX">FAX</option>
							</select>
						</div>
						<div class="form-group">
							<label for="tel" class="col-form-label">전화번호 :</label><input type="text" class="form-control" id="tel" name="tel" maxlength="11" onblur="replacePhoneNum(this)" required>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
						<button type="submit" class="btn btn-primary">등록</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- --------------------- -->
	<div class="row justify-content-center">
		<table class="table table-hover col-8">
			<thead class="thead-dark text-center">
				<tr>
					<th>ID</th>
					<th>종류</th>
					<th>전화번호</th>
					<th colspan="2">비고</th>
				</tr>
			</thead>
			<tbody class="text-center">
				<c:choose>
					<c:when test="${empty phones }">
						<tr>
							<td colspan="5">등록된 전화기가 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${phones }" var="phone">
							<tr>
								<td class="align-middle">${phone.id }</td>
								<td class="align-middle">${phone.type }</td>
								<td class="align-middle">${phone.tel }</td>
								<td class="align-middle">
									<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#phone_modifyModal">수정</button>
									<!-- Modal -->
									<div class="modal fade justify-content-start" id="phone_modifyModal" tabindex="-1" role="dialog" aria-labelledby="phone_modifyModalLabel" aria-hidden="true">
										<div class="modal-dialog" role="document">
											<div class="modal-content">
												<div class="modal-header">
													<h5 class="modal-title" id="phone_modifyModalLabel">전화기 수정</h5>
													<button type="button" class="close" data-dismiss="modal" aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
												</div>
												<form method="post" action="../phone_save/${user.id }">
													<div class="modal-body text-left">
														<div class="form-group">
															<label for="type" class="col-form-label">종류 :</label>
															<select class="form-control" id="type" name="type" required>
																<option value="" selected disabled hidden>종류를 선택하세요.</option>
																<option value="모바일" <c:if test="${phone.type eq '모바일' }">selected</c:if>>모바일</option>
																<option value="IP전화" <c:if test="${phone.type eq 'IP전화' }">selected</c:if>>IP전화</option>
																<option value="FAX" <c:if test="${phone.type eq 'FAX' }">selected</c:if>>FAX</option>
															</select>
														</div>
														<div class="form-group">
															<label for="tel" class="col-form-label">전화번호 :</label><input type="text" class="form-control" id="tel" name="tel" value="${phone.tel }" maxlength="11" onblur="replacePhoneNum(this)" required>
														</div>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
														<input type="hidden" name="id" value="${phone.id }">
														<button type="submit" class="btn btn-primary">수정</button>
													</div>
												</form>
											</div>
										</div>
									</div>
									<!-- --------------------- -->
								</td>
								<td>
									<form method="POST" action="/phone_delete/${phone.id}?_method=DELETE">
										<button type="submit" class="btn btn-secondary">삭제</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>

</body>
</html>