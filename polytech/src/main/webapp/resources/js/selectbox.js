// 부서 select태그 값이 변경될 때 발생하는 함수
function categoryChange(e) {
	// 각 부서에 속한 팀 배열 지정
	var option_sales = ["영업팀", "기획팀", "기술팀"];
	var option_tech = ["사업팀", "PS팀"];
	var option_uc = ["UC영업팀", "UC사업팀"];
	var option_management = ["경영관리팀", "경영기획팀"];	
	var target = document.getElementById("team"); // 선택된 부서에 따라 값을 바꿔줄 element 지정
	
	console.log(e.value);
	
	// 각 값을 비교하여 target에 넣을 값 지정
	if(e.value=="영업부") var d = option_sales;
	else if(e.value=="기술사업부") var d = option_tech;
	else if(e.value=="UC사업부") var d = option_uc;
	else if(e.value=="경영관리부") var d = option_management;
	
	// target의 값을 초기화
	target.options.length = 0;
	
	// option element 생성
	var option = document.createElement("option");
	option.value = '';
	option.innerHTML = "팀을 선택하세요.";
	target.appendChild(option); // target에 option 추가
	
	// 세팅된 하위 select에 배열의 값을 차례로 추가
	for(x in d) {
		var option = document.createElement("option");
		option.value = d[x];
		option.innerHTML = d[x];
		target.appendChild(option);
	}
}

// 페이지 당 조회 건수 조절 시 발생하는 함수
function sizeChange(e) {
	// 해당 변수로 size값을 던져줌
	location.href="?page=0&size="+e.value;
}

// 사용자 조회 페이지에서 사용자 수정 modal의 select값 세팅
function onloadSelect(dept, team, rank) {
	var dept_option = document.getElementById('department');
	dept_option.value = dept;
	categoryChange(dept_option);
	var team_option = document.getElementById('team');
	team_option.value = team;
	var rank_option = document.getElementById('rank');
	rank_option.value = rank;
}
