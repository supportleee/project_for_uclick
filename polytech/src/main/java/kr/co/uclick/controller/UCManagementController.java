package kr.co.uclick.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import kr.co.uclick.dto.PhoneDto;
import kr.co.uclick.dto.UserDto;
import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;
import kr.co.uclick.response.Response;
import kr.co.uclick.service.PhoneService;
import kr.co.uclick.service.UserService;

@Controller // Controller임을 명시
public class UCManagementController {
	
	// Log 작성을 위해 logger 생성
	private static final Logger logger = LoggerFactory.getLogger(UCManagementController.class);

	// service bean을 찾아가도록 의존성 주입
	@Autowired
	private UserService userService; 
	@Autowired
	private PhoneService phoneService;

	// 페이징 처리된 사용자 목록 view mapping
	@GetMapping(value = { "user_list", "/" })
	public String userList(Integer page, Integer size, Model model) { // 파라미터로 요청 페이지번호와 페이지 목록 크기를 받음
		
		logger.debug("user_list.jsp start");
		
		// 요청 페이지 번호가 없는 경우 default 값으로 0(첫 페이지)을 줌
		if (page == null) {
			page = 0;
		}
		// 페이지 목록 크기 값이 오지 않은 경우 default 값으로 10을 줌
		if (size == null) {
			size = 10;
		}
		
		// 페이징 처리된 전체 리스트를 model로 전달
		model.addAttribute("users", userService.findAll(page, size));
		return "user_list"; // user_list.jsp로 model 던짐
	}

	// 전체 사용자 목록을 CSV 파일로 만들어 다운로드함
	@GetMapping(value = "/downloadCSV")
	public void downloadCSV(HttpServletResponse response) throws IOException {
		logger.debug("download CSV start");
		
		// CSV 파일명에 현재 날짜를 넣기 위해 설정
		Date from = new Date();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		String to = transFormat.format(from);
		String csvFileName = "users_" + to + ".csv"; // CSV 파일명 지정

		// 만드는 파일의 타입을 CSV로 지정하고, 한글 인코딩을 위해 charset을 MS949로 지정
		response.setContentType("text/csv;charset=MS949");

		// 파일의 header 세팅
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);

		// CSV 파일에 넣을 리스트 가져오기
		List<User> users = userService.findAll();

		// SuperCSV API를 사용하여 가져온 데이터를 CSV 데이터로 만든다.
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] header = { "id", "name", "department", "team", "rank", "age", "email"};
		csvWriter.writeHeader(header);
		for (User user : users) {
			csvWriter.write(user, header);
		}
		csvWriter.close(); // writer close
	}

	// 사용자 검색 view mapping
	@GetMapping(value = "user_search")
	public String userListSearch(String condition, String keyword, Integer page, Integer size, Model model) {
		// 파라미터로 조건(이름/카테고리), 검색어, 페이지번호, 페이지목록 크기를 받음
		
		logger.debug("user_search.jsp start, condition : " + condition);

		// 페이지관련 파라미터가 오지 않았을 경우 default값 지정
		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 10;
		}
		
		if ("name".equals(condition)) { // 조건이 이름인 경우
			model.addAttribute("users", userService.findUserByNameContaining(keyword, page, size));
		} 
	
		else if ("tel".equals(condition)) { // 조건이 전화번호인 경우
			model.addAttribute("users", phoneService.findUserByTel(keyword, page, size));
		}
		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);
		return "user_search";
	}

	// 비동기 검색어 자동완성을 위한 이름목록 JSON 생성
	@GetMapping(value = "/json_search")
	@ResponseBody
	public Object jsonSearch() {
		List<String> list = userService.findAllName();
		return list;
	}

	// 단일 사용자 목록 view mapping
	@GetMapping(value = "user_view/{user_id}")
	public String userView(@PathVariable("user_id") Long userId, Model model) {
		logger.debug("user_view.jsp start user_id = " + userId);
		model.addAttribute("user", userService.findById(userId).get());
		model.addAttribute("phones", phoneService.findAllByUserId(userId));
		return "user_view";
	}

	// 사용자 추가/수정 시 유효성 검사(ajax를 이용한 비동기 방식으로 JSON 사용)
	@PostMapping(value = "/user_validation", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Object validateUser(@ModelAttribute @Valid UserDto userDto, BindingResult result) {
		logger.debug(
				"---------------------------------------user_validation start---------------------------------------");
		Response response = new Response(); // 유효성 검사 결과용 객체

		// 유효성 검사 결과 에러가 검출되면
		if (result.hasErrors()) {
			logger.debug("--------------------------has Error----------------------");
			// 에러메시지를 세팅함
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			response.setValidated(false);
			response.setErrorMessages(errors);
		} else { // 에러가 없을 경우
			logger.debug("--------------------------no Error----------------------");
			User user = userDto.toEntity();
			userService.save(user); // 들어온 정보로 save
			response.setValidated(true);
		}
		return response;
	}

	// 사용자 삭제
	@DeleteMapping(value = "user_delete/{user_id}")
	public String deleteUser(@PathVariable("user_id") Long userId) {
		logger.debug("delete User");
		userService.deleteOne(userId);
		return "redirect:/user_list";
	}

	// 사용자 선택 삭제
	@PostMapping(value = "user_delete/selected")
	public String deleteUserSelected(String[] userIds) {
		userService.deleteUserByIds(userIds);
		return "redirect:/user_list";
	}

	// 전화기 추가/수정을 위한 유효성 검사(ajax를 이용한 비동기 방식으로 JSON 사용)
	@PostMapping(value = "/phone_validation/{user_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Response validatePhone(@ModelAttribute @Valid PhoneDto phoneDto, BindingResult result, @PathVariable("user_id") Long userId) {
		logger.debug(
				"---------------------------------------phone_validation start---------------------------------------");
		Response response = new Response(); // 유효성 검사 결과용 객체
		// 유효성 검사 결과 에러가 검출되면
		if (result.hasErrors()) {
			logger.debug("--------------------------has Error----------------------");
			// 에러메시지를 세팅함
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			response.setValidated(false);
			response.setErrorMessages(errors);
		} else { // 에러가 없을 경우
			logger.debug("--------------------------no Error----------------------");
			response.setValidated(true); // validate 값은 true로 설정
			// 들어온 전화번호 중복여부 확인
			if(phoneService.authenticateTel(phoneDto.getTel(), phoneDto.getId()) == true) {
				// 중복되지 않을 경우
				User user = userService.findById(userId).get();
				response.setNotDuplicatePhone(true);
				Phone phone = phoneDto.toEntity();
				phone.setUser(user);
				phoneService.save(phone); // 전화기 save
			} else {
				// 전화번호가 중복된 경우
				response.setNotDuplicatePhone(false);
			}
		}
		return response;
	}

	// 전화기 삭제
	@DeleteMapping(value = "phone_delete/{phone_id}")
	public String deletePhone(@PathVariable("phone_id") Long phoneId) {
		Phone phone = phoneService.findById(phoneId).get();
		phoneService.deleteOne(phoneId);
		return "redirect:/user_view/" + phone.getUser().getId();
	}

	// 전화기 수정 시 modal에 값 세팅을 위한 JSON
	@PostMapping(value = "/json_phone_search")
	@ResponseBody
	public Object phone(String phone_id) {
		logger.debug("phone_id : {}", phone_id);
		Long long_phone_id = Long.parseLong(phone_id);
		Phone phone = phoneService.findById(long_phone_id).get();
		return phone;
	}
}
