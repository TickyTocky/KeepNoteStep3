package com.stackroute.keepnote.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserService;

@RestController
@RequestMapping("/")
@SessionAttributes(value = "user")
public class UserAuthenticationController {

	private UserService userService;

	@Autowired
	public UserAuthenticationController(UserService userService) {

		this.userService = userService;
	}

	@PostMapping("login")
	public ResponseEntity<?> login(@Validated @RequestBody User user, HttpSession session)
			throws UserNotFoundException {

		ResponseEntity<?> responseEntity = null;

		if (userService.validateUser(user.getUserId(), user.getUserPassword())) {
			User validatedUser = userService.getUserById(user.getUserId());
			session.setAttribute("loggedInUser", validatedUser);
			session.setAttribute("loggedInUserId", validatedUser.getUserId());
			session.setAttribute("UserName", validatedUser.getUserName());
			responseEntity = new ResponseEntity<User>(validatedUser, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}

		return responseEntity;
	}

	@GetMapping("logout")
	public ResponseEntity<?> logout(HttpSession session) {

		ResponseEntity<?> responseEntity = null;

		String userId = (String) session.getAttribute("loggedInUserId");
		if (userId != null) {
			User user = new User();
			session.invalidate();
			responseEntity = new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}

		return responseEntity;
	}
}
