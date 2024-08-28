package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.models.User;
import com.rev_connect_api.services.UserService;
import com.rev_connect_api.models.BusinessProfile;
import com.rev_connect_api.services.BusinessProfileService;

@RestController
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private BusinessProfileService businessProfileService;

	@RequestMapping("/")
	public String hello() {
		return "Hello Localhost World!!";
	}

	@PostMapping(value = "/register")
	public String register(
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String username,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam Boolean isBusiness) {

		User newUser = new User(username, firstName, lastName, email, password, isBusiness);
		userService.registerUser(newUser);

		User registeredUser = userService.findUserByUsername(username);
		if (registeredUser != null) {
			if (registeredUser.getIsBusiness()) {
				businessProfileService.createBusinessProfile(new BusinessProfile(registeredUser, ""));
			}
			return registeredUser.toString();
		} else {
			return "User not Found!";
		}
	}

	@PostMapping(value = "/checkUsername")
	public Boolean checkUserId(@RequestParam String username) {
		User registeredUser = userService.findUserByUsername(username);
		if (registeredUser != null)
			return true;
		else
			return false;
	}

}
