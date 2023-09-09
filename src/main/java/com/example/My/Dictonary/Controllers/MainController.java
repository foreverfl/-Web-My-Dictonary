package com.example.My.Dictonary.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.My.Dictonary.DTO.MemberDTO;
import com.example.My.Dictonary.Entities.Dictionary;
import com.example.My.Dictonary.Entities.Member;
import com.example.My.Dictonary.Entities.MemberDictionary;
import com.example.My.Dictonary.Entities.WordRemembered;
import com.example.My.Dictonary.Services.DictionaryService;
import com.example.My.Dictonary.Services.MemberDictionaryService;
import com.example.My.Dictonary.Services.MemberService;
import com.example.My.Dictonary.Services.WordService;

@Controller
public class MainController {

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

	@GetMapping(value = "/main")
	public String mainGet(HttpSession session, Authentication authentication) {

		StringTokenizer st = new StringTokenizer(authentication.toString());
		String token = st.nextToken();

		if (token.equals("UsernamePasswordAuthenticationToken")) {
			MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();
			session.setAttribute("member_idAuto", memberDTO.getIdAuto());
			session.setAttribute("member_id", memberDTO.getId());
			session.setAttribute("member_email", memberDTO.getEmail());
			session.setAttribute("member_photo", memberDTO.getPhoto());
		} else if (token.equals("OAuth2AuthenticationToken")) {

		}
		return "/Main";
	}

	@GetMapping(value = "/fragment/navbar")
	public String navbar() {
		return "/Fragment/Navbar";
	}

	@GetMapping(value = "/fragment/sidebar")
	public String sidebar(HttpSession session, Model model) {

		// distributing data
		Long id = (Long) session.getAttribute("member_idAuto");
		Member member = memberService.findByIdAuto(id).get();

		List<MemberDictionary> memberDictionaries = member.getMemberDictionaries();
		List<Dictionary> dictionariesMine = new ArrayList<>();
		List<Dictionary> dictionariesSubscribed = new ArrayList<>();

		for (int i = 0; i < memberDictionaries.size(); i++) {

			Dictionary dictionary = memberDictionaries.get(i).getDictionary();

			// handling null pointer exception
			if (dictionary == null)
				continue;

			if (dictionary.getWriter().equals(member.getNickname())) {
				dictionariesMine.add(dictionary);
			} else {
				dictionariesSubscribed.add(dictionary);
			}

		}

		session.setAttribute("dics_mine", dictionariesMine);
		session.setAttribute("dics_subscribed", dictionariesSubscribed);

		// calculating level
		Long exp = 0L;

		// by wordRemembered
		for (int i = 0; i < memberDictionaries.size(); i++) {

			WordRemembered wordRemembered = memberDictionaries.get(i).getWordRemembered();

			// handling null pointer exception
			if (wordRemembered == null)
				continue;

			if (wordRemembered.getCount() == 1) {
				exp += 10;
			} else if (wordRemembered.getCount() == 2) {
				exp += 15;
			} else if (wordRemembered.getCount() == 3) {
				exp += 18;
			} else if (wordRemembered.getCount() == 4) {
				exp += 20;
			} else if (wordRemembered.getCount() >= 5) {
				Long additionalCnt = Long.valueOf((wordRemembered.getCount() - 4));
				exp += (20 + additionalCnt);
			}
			

		}
		
		member.setExp(exp);
		memberService.saveOne(member);
		
		return "/Fragment/Sidebar";
	}

}
