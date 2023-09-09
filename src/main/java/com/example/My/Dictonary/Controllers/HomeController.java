package com.example.My.Dictonary.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.My.Dictonary.DTO.MemberDTO;
import com.example.My.Dictonary.Entities.Member;
import com.example.My.Dictonary.Services.DictionaryService;
import com.example.My.Dictonary.Services.EmailService;
import com.example.My.Dictonary.Services.MemberDictionaryService;
import com.example.My.Dictonary.Services.MemberService;
import com.example.My.Dictonary.Services.WordService;

@Controller
public class HomeController {

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

	@Autowired
	EmailService emailService;

//	private String CLIENT_ID = "I_YVK1GJnm1knGUymP4Y";
//	private String CLI_SECRET = "fYWCwJwI40";

	public static void init(HttpServletResponse response) { // preventing Korean to be broken
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	}

	// index page(sign in)
	@GetMapping(value = "/")
	public String signInGet() {
		return "index";

	}

	@PostMapping(value = "/")
	public String signInPost(MemberDTO memberDTO) {
		return "index";
	}

	// sign up
	@GetMapping(value = "/sign_up")
	public String signUpGet(MemberDTO memberDTO) {
		return "/SignUp/SignUp";
	}

	@PostMapping(value = "/sign_up")
	public String signUpPost(MemberDTO memberDTO) {

		memberService.signUp(memberDTO);

		return "redirect:/"; // to main page
	}

	// finding name
	@GetMapping(value = "/finding_name")
	public String findingNameGet(MemberDTO memberDTO) {
		return "/SignUp/FindingName";
	}

	@PostMapping(value = "/finding_name")
	public void findingNamePost(HttpServletResponse response, Member member) throws IOException {

//		init(response);
//		PrintWriter out = response.getWriter();
//
//		Member members = memberService.findByEmail(member.getEmail());
//
//		Member memberToLogin;
//		if (members.isEmpty()) {
//			memberToLogin = null;
//		} else {
//			memberToLogin = members.get(0);
//
//		}
//
//		if (member.getEmail().equals("")) { // blank name
//			out.println("<script>alert('이메일을 입력해주세요.');</script>");
//			out.flush();
//		} else if (memberToLogin == null) {
//			out.println("<script>alert('존재하지 않는 이메일입니다.');</script>");
//			out.flush();
//		} else {
//			out.println("<script>alert('아이디는 " + memberToLogin.getId() + "입니다.');</script>");
//			out.flush();
//		}
	}

	// finding password
	@GetMapping(value = "/finding_password")
	public String findingPwGet(MemberDTO memberDTO) {
		return "/SignUp/FindingPw";
	}

	@PostMapping(value = "/finding_password")
	public String findingPwPost(HttpServletResponse response, Member memberDTO) {
		return "redirect:/";
	}

	// checking id
	@PostMapping("/sign_up/id_check")
	public ResponseEntity<String> checkId(@RequestBody HashMap<String, Object> data) throws Exception {
		String memberId = data.get("id").toString();
		Optional<Member> member = memberService.findById(memberId);
		System.out.println("id_check");
		System.out.println(member);

		if (member.isEmpty()) {
			return new ResponseEntity<String>("possible", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("impossible", HttpStatus.OK);
		}

	}

	// checking nickname
	@PostMapping("/sign_up/nickname_check")
	public ResponseEntity<String> checkNickname(@RequestBody HashMap<String, Object> data) throws Exception {
		String memberNickname = data.get("nickname").toString();
		Optional<Member> member = memberService.findByNickname(memberNickname);
		System.out.println("nickname_check");
		System.out.println(member);

		if (member.isEmpty()) {
			return new ResponseEntity<String>("possible", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("impossible", HttpStatus.OK);
		}

	}

	// checking email and sending email for authentication
	@PostMapping("/sign_up/email_check")
	public ResponseEntity<String> checkEmail(HttpSession session, @RequestBody HashMap<String, Object> data)
			throws Exception {
		String memberEmail = data.get("email").toString();
		Optional<Member> member = memberService.findByEmail(memberEmail);

		if (member.isEmpty()) {

			String code = emailService.createKey();
			session.setAttribute("email_code", code);
			
			emailService.sendSimpleMessage(session, memberEmail); // sending email for authentication
			
			return new ResponseEntity<String>("possible", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("impossible", HttpStatus.OK);
		}

	}

	@PostMapping("/sign_up/email_code_check")
	public ResponseEntity<String> checkEmailCode(HttpSession session, @RequestBody HashMap<String, Object> data) {

		String codeCreated = (String) session.getAttribute("email_code");
		String codeInput = (String) data.get("email_code");

		if (codeCreated.equals(codeInput)) {
			System.out.println("일치합니다.");
			return new ResponseEntity<String>("valid", HttpStatus.OK);
		} else {
			System.out.println("ㅠㅠ....");
			return new ResponseEntity<String>("invalid", HttpStatus.OK);
		}

	}

}
