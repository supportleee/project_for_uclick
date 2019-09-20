/**
 * 
 */
function inputTelNumber(obj) {

    var number = obj.value.replace(/[^0-9]/g, "");
    var tel = "";

    // 서울이라는 변수를 선언
    var seoul = 0;

    // 서울 지역번호(02)가 들어가는 경우 1을 삽입
    if(number.substring(0, 2).indexOf('02') == 0) {
        seoul = 1;
    }

    // 문자열을 자르는 기준에서 서울의 값을 뺄쌤(-)한다.
    // 값이 1일경우 -1이 될것이고, 값이 0일경우 변화가 없다.
    if(number.length < (seoul - 4)) {
        return number;
    } else if(number.length < (seoul - 7)) {
        tel += number.substr(0, (seoul - 3));
        tel += "-";
        tel += number.substr(seoul - 3);
    } else if(number.length < (seoul - 11)) {
        tel += number.substr(0, (seoul - 3));
        tel += "-";
        tel += number.substr((seoul - 3), 3);
        tel += "-";
        tel += number.substr(seoul - 6);
    } else {
        tel += number.substr(0, (seoul - 3));
        tel += "-";
        tel += number.substr((seoul - 3), 4);
        tel += "-";
        tel += number.substr(seoul - 7);
    }
    obj.value = tel;
}

function replacePhoneNum(obj) {
	var phoneNum = obj.value.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
	obj.value = phoneNum;
}
