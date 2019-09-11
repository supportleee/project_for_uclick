package kr.co.uclick.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.uclick.entity.User;
import kr.co.uclick.service.UserService;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@GetMapping(value="listUser.html")
	public String userList(Model model) {
		model.addAttribute("users", userService.findAll());
		return "userList";
	}
	
	@GetMapping(value="newUser.html")
	public String newUser() {
		return "newUser";
	}
	
	@GetMapping(value="editUser.html")
	public String editUser(Long userId) {
		userService.findById(userId);
		return "editUser";
	}
	
	@PostMapping(value="saveUser.html")
	public String saveUser(User user){
		logger.debug("save User");
		userService.save(user);
		return "redirect:userList.html";
	}
	
	@DeleteMapping(value="deleteUser.html")
	public String deleteUser(User user) {
		logger.debug("delete User");
		userService.delete(user);
		return "redirect:userList.html";
	}
}
