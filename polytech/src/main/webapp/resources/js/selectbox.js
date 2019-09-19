/**
 * 
 */

function categoryChange(e) {
	var option_sales = ["영업팀", "기획팀", "기술팀"];
	var option_tech = ["사업팀", "PS팀"];
	var option_uc = ["UC영업팀", "UC사업팀"];
	var option_management = ["경영관리팀", "경영기획팀"];	
	var target = document.getElementById("team");
	
	console.log(e.value);
	
	if(e.value=="영업부") var d = option_sales;
	else if(e.value=="기술사업부") var d = option_tech;
	else if(e.value=="UC사업부") var d = option_uc;
	else if(e.value=="경영관리부") var d = option_management;
	
	target.options.length = 0;
	
	var option = document.createElement("option");
	option.value = '';
	option.innerHTML = "팀을 선택하세요.";
	target.appendChild(option);
	
	for(x in d) {
		var option = document.createElement("option");
		option.value = d[x];
		option.innerHTML = d[x];
		target.appendChild(option);
	}
}

function sizeChange(e) {
	location.href="?first=0&size="+e.value;
}


function onloadSelect(dept, team, rank) {
	var dept_option = document.getElementById('department');
	dept_option.value = dept;
	categoryChange(dept_option);
	var team_option = document.getElementById('team');
	team_option.value = team;
	var rank_option = document.getElementById('rank');
	rank_option.value = rank;
}