package kr.co.uclick.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;
import kr.co.uclick.service.PhoneService;
import kr.co.uclick.service.UserService;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PhoneService phoneService;

	@GetMapping(value = {"user_list","/"})
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

	@GetMapping(value = "user_view/{user_id}")
	public String userView(@PathVariable("user_id") Long userId, Model model) {
		logger.debug("user_view.jsp start user_id = " + userId);
		model.addAttribute("user", userService.findById(userId).get());
		model.addAttribute("phones", phoneService.findAllByUserId(userId));
		return "user_view";
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
	
	@PostMapping(value="user_delete/selected")
	public String deleteUserSelected(String[] userIds) {
		//userService.deleteUserByIdIn(userIds);
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
	
	@DeleteMapping(value = "phone_delete/{phone_id}")
	public String deletePhone(@PathVariable("phone_id") Long phoneId) {
		Phone phone = phoneService.findById(phoneId).get();
		phoneService.deleteOne(phoneId);
		return "redirect:/user_view/" + phone.getUser().getId();
	}
}
