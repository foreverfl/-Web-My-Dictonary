package com.example.My.Dictonary.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.My.Dictonary.Services.DictionaryService;
import com.example.My.Dictonary.Services.MemberDictionaryService;
import com.example.My.Dictonary.Services.MemberService;
import com.example.My.Dictonary.Services.WordService;

@Controller
public class OthersController {
	
	@Autowired
	MemberService memberService;

	@Autowired
	MemberDictionaryService memberDictionaryService;

	@Autowired
	DictionaryService dictionaryService;

	@Autowired
	WordService wordService;

	@Autowired
	ModelMapper modelMapper;
	
	// terms
	@GetMapping(value = "/terms_personal_information")
	public String termsPersonalInformation() {
		return "/Others/TermsPersonalInformation";
	}

	@GetMapping(value = "/terms_using_site")
	public String termsUsingSite() {
		return "/Others/TermsUsingSite";
	}
}
