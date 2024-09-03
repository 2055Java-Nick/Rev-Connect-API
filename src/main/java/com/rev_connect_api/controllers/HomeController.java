package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.services.UserService;

@Service
public class HomeController {
	
	@Autowired
	UserService userService;

}
