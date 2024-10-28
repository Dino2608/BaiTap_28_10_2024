package com.example.thymeleaf.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "admin")
public class HomeAdminController {
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
}
