package com.example.My.Dictonary.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VerifyController {
	
	@PostMapping("/iamport")
	public String iamport() {
		return "index";
	}
}
