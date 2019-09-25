// 검색어 자동완성 기능
$(function() {	//화면 다 뜨면 시작
	var datas;
	// 비동기 방식으로 이름 목록 json 데이터 가져오기
	$.ajax({
		type:'get',
		url:'json_search',
		dataType:"json",
		success: function(data) { // 성공 시
			datas = data; // data 값 저장
			// 검색어 자동완성 값으로 data 전달
			$("#keyword").autocomplete({
				source: datas
			});
		}
	});
});
