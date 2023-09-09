package com.example.My.Dictonary.Services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.My.Dictonary.Entities.Member;

@Service
public class LevelService {

	@Autowired
	MemberService memberService;

	public List<Long> getLevel(HttpSession session) {

		Long memberId = (Long) session.getAttribute("member_idAuto");
		Member member = memberService.findByIdAuto(memberId).get();

		List<Long> levelAndExp = new ArrayList<>();
		Long level = 1L;
		Long exp = member.getExp();
		Long expPercent = (exp * 100 / 1000);

		while (true) {
			if (level == 1L && exp >= 1000) {
				level = 2L;
				exp = exp - 1000;
				expPercent = (exp * 100 / 4000);
			} else if (level == 2L && exp >= 4000) {
				level = 3L;
				exp = exp - 4000;
				expPercent = (exp * 100 / 9000);
			} else if (level == 3L && exp >= 9000) {
				level = 4L;
				exp = exp - 9000;
				expPercent = (exp * 100 / 16000);
			} else if (level == 4L && exp >= 16000) {
				level = 5L;
				exp = exp - 16000;
				expPercent = (exp * 100 / 25000);
			} else if (level == 5L && exp >= 25000) {
				level = 6L;
				exp = exp - 25000;
				expPercent = (exp * 100 / 36000);
			} else if (level == 6L && exp >= 36000) {
				level = 7L;
				exp = exp - 36000;
				expPercent = (exp * 100 / 49000);
			} else if (level == 7L && exp >= 49000) {
				level = 8L;
				exp = exp - 49000;
				expPercent = (exp * 100 / 64000);
			} else if (level == 8L && exp >= 64000) {
				level = 9L;
				exp = exp - 64000;
				expPercent = (exp * 100 / 81000);
			} else if (level == 9L && exp >= 81000) {
				level = 10L;
				exp = exp - 81000;
				expPercent = (exp * 100 / 100000);

			} else {
				break;
			}
		}

		levelAndExp.add(level);
		levelAndExp.add(exp);
		levelAndExp.add(expPercent);

		return levelAndExp;
	}

}
