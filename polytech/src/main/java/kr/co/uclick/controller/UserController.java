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

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PhoneService phoneService;

	@GetMapping(value = { "user_list", "/" })
	public String userList(Integer page, Integer size, Model model) {
		logger.debug("user_list.jsp start");
		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 10;
		}
		model.addAttribute("users", userService.findAll(page, size));
		return "user_list";
	}

	@GetMapping(value = "/downloadCSV")
	public void downloadCSV(HttpServletResponse response) throws IOException {
		Date from = new Date();

		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");

		String to = transFormat.format(from);

		String csvFileName = "users_" + to + ".csv";

		response.setContentType("text/csv;charset=MS949");

		// creates mock data
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);

		List<User> users = userService.findAll();

		// uses the Super CSV API to generate CSV data from the model data
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		String[] header = { "id", "name", "department", "team", "rank", "age", "email" };

		csvWriter.writeHeader(header);

		for (User user : users) {
			csvWriter.write(user, header);
		}

		csvWriter.close();
	}

	@GetMapping(value = "user_search")
	public String userListSearch(String condition, String keyword, Integer page, Integer size, Model model) {
		logger.debug("user_search.jsp start, condition : " + condition);

		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 10;
		}

		if ("name".equals(condition)) {
			model.addAttribute("users", userService.findUserByNameContaining(keyword, page, size));
		} else if ("tel".equals(condition)) {
			model.addAttribute("users", phoneService.findUserByTel(keyword, page, size));
		}
		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);
		return "user_search";
	}

	@GetMapping(value = "/json_search")
	@ResponseBody
	public Object jsonSearch() {
		List<String> list = userService.findAllName();
		// String[] array = list.toArray(new String[0]);

		return list;
	}

	@GetMapping(value = "user_view/{user_id}")
	public String userView(@PathVariable("user_id") Long userId, Model model) {
		logger.debug("user_view.jsp start user_id = " + userId);
		model.addAttribute("user", userService.findById(userId).get());
		model.addAttribute("phones", phoneService.findAllByUserId(userId));
		return "user_view";
	}

	@PostMapping(value = "/user_validation", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Object validateUser(@ModelAttribute @Valid UserDto userDto, BindingResult result) {
		logger.debug(
				"---------------------------------------user_validation start---------------------------------------");
		Response response = new Response();

		if (result.hasErrors()) {
			logger.debug("--------------------------has Error----------------------");
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			response.setValidated(false);
			response.setErrorMessages(errors);
		} else {
			logger.debug("--------------------------no Error----------------------");
			User user = userDto.toEntity();
			userService.save(user);
			response.setValidated(true);
		}
		return response;
	}

	@PostMapping(value = "user_save")
	public String saveUser(User user) {
		logger.debug("save User");
		userService.save(user);
		return "redirect:user_view/" + user.getId();
	}

	@DeleteMapping(value = "user_delete/{user_id}")
	public String deleteUser(@PathVariable("user_id") Long userId) {
		logger.debug("delete User");
		userService.deleteOne(userId);
		return "redirect:/user_list";
	}

	@PostMapping(value = "user_delete/selected")
	public String deleteUserSelected(String[] userIds) {
		// userService.deleteUserByIdIn(userIds);
		userService.deleteUserByIds(userIds);
		return "redirect:/user_list";
	}

	@PostMapping(value = "phone_save/{user_id}")
	public String savePhone(Phone phone, @PathVariable("user_id") Long userId) {
		logger.debug("save Phone");
		User user = userService.findById(userId).get();
		phone.setUser(user);
		phoneService.save(phone);
		return "redirect:/user_view/" + phone.getUser().getId();
	}

	@PostMapping(value = "/phone_validation/{user_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Response validatePhone(@ModelAttribute @Valid PhoneDto phoneDto, BindingResult result, @PathVariable("user_id") Long userId) {
		logger.debug(
				"---------------------------------------phone_validation start---------------------------------------");
		Response response = new Response();

		if (result.hasErrors()) {
			logger.debug("--------------------------has Error----------------------");
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			response.setValidated(false);
			response.setErrorMessages(errors);
		} else {
			logger.debug("--------------------------no Error----------------------");
			response.setValidated(true);
			if(phoneService.authenticateTel(phoneDto.getTel(), phoneDto.getId()) == true) {
				User user = userService.findById(userId).get();
				response.setNotDuplicatePhone(true);
				Phone phone = phoneDto.toEntity();
				phone.setUser(user);
				phoneService.save(phone);
			} else {
				response.setNotDuplicatePhone(false);
			}
		}
		return response;
	}

	@DeleteMapping(value = "phone_delete/{phone_id}")
	public String deletePhone(@PathVariable("phone_id") Long phoneId) {
		Phone phone = phoneService.findById(phoneId).get();
		phoneService.deleteOne(phoneId);
		return "redirect:/user_view/" + phone.getUser().getId();
	}

	@PostMapping(value = "/json_phone_search")
	@ResponseBody
	public Object phone(String phone_id) {
		logger.debug("phone_id : {}", phone_id);
		Long long_phone_id = Long.parseLong(phone_id);
		Phone phone = phoneService.findById(long_phone_id).get();
		return phone;
	}
}
