package com.example.My.Dictonary.Controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.My.Dictonary.DTO.WordDTO;
import com.example.My.Dictonary.Entities.Dictionary;
import com.example.My.Dictonary.Entities.Member;
import com.example.My.Dictonary.Entities.MemberDictionary;
import com.example.My.Dictonary.Entities.Word;
import com.example.My.Dictonary.Entities.WordRemembered;
import com.example.My.Dictonary.Entities.Enum.Lan;
import com.example.My.Dictonary.Services.DictionaryService;
import com.example.My.Dictonary.Services.MemberDictionaryService;
import com.example.My.Dictonary.Services.MemberService;
import com.example.My.Dictonary.Services.WordRememberedService;
import com.example.My.Dictonary.Services.WordService;
import com.example.My.Dictonary.VO.Option;
import com.example.My.Dictonary.VO.WordTest;

@Controller
public class DictionaryController {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberDictionaryService memberDictionaryService;

	@Autowired
	DictionaryService dictionaryService;

	@Autowired
	WordService wordService;

	@Autowired
	WordRememberedService wordRememberedService;

	@Autowired
	ModelMapper modelMapper;

	// dictionary list(mine)
	@GetMapping(value = "/dictionary_list/mine")
	public String dictionaryListMineGet(HttpSession session) {

		String id = (String) session.getAttribute("member_id");
		Member member = memberService.findById(id).get();

		List<Dictionary> allDics = dictionaryService.findAll();

		List<Dictionary> allMyDics = new ArrayList<>();

		for (int i = 0; i < allDics.size(); i++) {
			if (allDics.get(i).getWriter().equals(member.getNickname())) {
				allMyDics.add(allDics.get(i));
			}
		}

		session.setAttribute("all_dics", allMyDics);

		List<Integer> allDicsCnt = new ArrayList<>();

		for (int i = 0; i < allMyDics.size(); i++) {
			allDicsCnt.add(allMyDics.get(i).getWords().size());
		}

		session.setAttribute("all_dics_cnt", allDicsCnt);

		return "/Dictionary/DictionaryList";
	}

	// dictionary list(popular)
	@GetMapping(value = "/dictionary_list/popular")
	public String dictionaryListPopular(HttpSession session) {

		List<Dictionary> all_dics = dictionaryService.findAll();

		session.setAttribute("all_dics", all_dics);

		List<Integer> all_dics_cnt = new ArrayList<>();

		for (int i = 0; i < all_dics.size(); i++) {
			all_dics_cnt.add(all_dics.get(i).getWords().size());
		}

		session.setAttribute("all_dics_cnt", all_dics_cnt);

		return "/Dictionary/DictionaryList";
	}

	// dictionary list(random)
	@GetMapping(value = "/dictionary_list/random")
	public String dictionaryListRandom(HttpSession session) {

		List<Dictionary> all_dics = dictionaryService.findAll();

		Collections.shuffle(all_dics);

		session.setAttribute("all_dics", all_dics);

		List<Integer> all_dics_cnt = new ArrayList<>();

		for (int i = 0; i < all_dics.size(); i++) {
			all_dics_cnt.add(all_dics.get(i).getWords().size());
		}

		session.setAttribute("all_dics_cnt", all_dics_cnt);

		return "/Dictionary/DictionaryList";
	}

	// making the dictionary
	@GetMapping(value = "/dictionaryMaking")
	public String dictionaryMakingGet(Dictionary dictionary) {
		return "/Dictionary/DictionaryMaking";

	}

	@PostMapping(value = "/dictionaryMaking")
	@Transactional
	public String dictionaryMakingPost(HttpSession session, Dictionary dictionary) {

		String id = (String) session.getAttribute("member_id");

		Member memberNow = memberService.findById(id).get();

		// dictionary
		Dictionary dictionarySetAuthor = dictionary;
		dictionarySetAuthor.setWriter(memberNow.getNickname());

		System.out.println(dictionary);
		dictionaryService.saveDictionary(dictionarySetAuthor);

		// making the MemberDictonary
		MemberDictionary memberDictionary = new MemberDictionary();
		memberDictionary.setDictionary(dictionarySetAuthor);
		memberDictionary.setMember(memberNow);
		memberDictionaryService.saveOne(memberDictionary);

		return "redirect:/dictionary_list/mine";

	}

	// opening dictionary
	@GetMapping(value = "/dictionary/{dicWriter}/{dicId}")
	public String dictionaryGet(HttpSession session, Model model, @PathVariable String dicWriter,
			@PathVariable String dicId, WordDTO wordDTO) throws UnsupportedEncodingException {
		// choosing the way to sort
		List<Option> options = new ArrayList<>();
		options.add(new Option("random", "무작위순"));
		options.add(new Option("time", "시간순"));
		options.add(new Option("character", "문자열순"));

		model.addAttribute("options", options);

		// dictionary info
		List<Dictionary> dictionaries = dictionaryService.findAll();
		for (int i = 0; i < dictionaries.size(); i++) {
			dictionaries.get(i).getName();
		}

		Dictionary dictionary = dictionaryService.findById(Long.parseLong(dicId)).get();
		session.setAttribute("dic", dictionary);
		session.setAttribute("dic_id", dicId);
		session.setAttribute("dic_name", dictionary.getName());
		session.setAttribute("dic_writer", dictionary.getWriter());
		session.setAttribute("dic_type", dictionary.getDic());

		String dicNow_lan = dictionary.getLan().toString();

		if (dicNow_lan.equals("ENG")) {
			dicNow_lan = "[영어]";
		} else if (dicNow_lan.equals("JAP")) {
			dicNow_lan = "[일본어]";
		} else if (dicNow_lan.equals("KOR")) {
			dicNow_lan = "[한국어]";
		} else if (dicNow_lan.equals("SPA")) {
			dicNow_lan = "[스페인어]";
		}

		session.setAttribute("dic_lan", dicNow_lan);

		// words for tests
		List<Word> words = dictionary.getWords();
		Collections.shuffle(words);
		session.setAttribute("words_random", words);

		// distinguishing between me and others
		String memberIdAuto = (String) session.getAttribute("member_id");
		Member member = memberService.findById(memberIdAuto).get();
		String nickName = member.getNickname();

		boolean isMe = false;
		if (nickName.equals((String) session.getAttribute("dic_writer"))) {
			isMe = true;
		}
		session.setAttribute("session_isMe", isMe);

		// showing the number of hearts
		boolean isContained = false;
		List<MemberDictionary> memberDictionaries = dictionary.getMemberDictionaries();
		for (int i = 0; i < memberDictionaries.size(); i++) {
			if (memberDictionaries.get(i).getMember().getIdAuto().equals(member.getIdAuto())) {
				isContained = true;
				break;
			}
		}

		session.setAttribute("isContained", isContained);
		session.setAttribute("dic_good_cnt", memberDictionaries.size() - 1);

		return "/Dictionary/Dictionary";

	}

	@PatchMapping(value = "/dictionary/{dicWriter}/{dicId}/random")
	public String dictionaryPatchRandom(HttpSession session, Model model, @PathVariable String dicWriter,
			@PathVariable String dicId, WordDTO wordDTO) throws UnsupportedEncodingException {

		session.setAttribute("sort", "random");

		Dictionary dictionary = dictionaryService.findById(Long.parseLong(dicId)).get();
		List<Word> words = dictionary.getWords();
		Collections.shuffle(words);
		session.setAttribute("words_random", words);

		return "/Dictionary/Dictionary";
	}

	@PatchMapping(value = "/dictionary/{dicWriter}/{dicId}/time")
	public String dictionaryPatchTime(HttpSession session, Model model, @PathVariable String dicWriter,
			@PathVariable String dicId, WordDTO wordDTO) throws UnsupportedEncodingException {

		session.setAttribute("sort", "time");

		Dictionary dictionary = dictionaryService.findById(Long.parseLong(dicId)).get();
		List<Word> words = dictionary.getWords();
		session.setAttribute("words_time", words);

		return "/Dictionary/Dictionary";
	}

	@PatchMapping(value = "/dictionary/{dicWriter}/{dicId}/character")
	public String dictionaryPatchCharacter(HttpSession session, Model model, @PathVariable String dicWriter,
			@PathVariable String dicId, WordDTO wordDTO) throws UnsupportedEncodingException {

		session.setAttribute("sort", "character");

		Dictionary dictionary = dictionaryService.findById(Long.parseLong(dicId)).get();
		List<Word> words = dictionary.getWords();
		// character
		Collections.sort(words, new Comparator<Word>() {

			@Override
			public int compare(Word o1, Word o2) {
				return o1.getWord().compareTo(o2.getWord());
			}

		});
		session.setAttribute("words_character", words);

		return "/Dictionary/Dictionary";
	}

	// saving words
	@PostMapping(value = "/dictionary/{dicWriter}/{dicIdAuto}")
	public String dictionaryPost(HttpSession session, Model model, @PathVariable String dicWriter,
			@PathVariable String dicIdAuto, WordDTO wordDTO) throws UnsupportedEncodingException {
		System.out.println("테스트");
		// saving words
		Dictionary dictionary = dictionaryService.findById(Long.parseLong(dicIdAuto)).get();
		wordDTO.setDictionary(dictionary);
		int testCount = dictionary.getMaxTest();
		wordDTO.setTestCount(testCount);

		Word word = modelMapper.map(wordDTO, Word.class);

		wordService.saveOne(word);

		return "/Dictionary/Dictionary";
	}

	// dictionary configuration
	@GetMapping(value = "/dictionary/{dicWriter}/{dicId}/config")
	public String dictionaryConfigGet(HttpSession session, Model model) {

		Dictionary dictionary = (Dictionary) session.getAttribute("dic");
		model.addAttribute("dictionary", dictionary);

		return "/Dictionary/DictionaryConfig";
	}

	// saving dictionary configuration
	@PostMapping(value = "/dictionary/{dicWriter}/{dicId}/config")
	public String dictionaryConfigPost(HttpSession session, Dictionary dictionary) {

		Dictionary dictionarySession = (Dictionary) session.getAttribute("dic");
		dictionary.setIdAuto(dictionarySession.getIdAuto());
		dictionary.setWriter(dictionarySession.getWriter());
		dictionary.setDic(dictionarySession.getDic());
		dictionary.setLan(dictionarySession.getLan());
		dictionary.setMemberDictionaries(dictionarySession.getMemberDictionaries());
		dictionary.setWords(dictionarySession.getWords());

		dictionaryService.saveDictionary(dictionary);

		return "redirect:/dictionary_list/mine";
	}

	// expressing good
	@GetMapping(value = "/dictionary/{dicWriter}/{dicId}/good")
	public String dictionaryGood(HttpSession session) {

		// dictionary
		Dictionary dictionary = (Dictionary) session.getAttribute("dic");

		// member
		String id = (String) session.getAttribute("member_id");
		Member member = memberService.findById(id).get();

		// relationship
		boolean isContained = (boolean) session.getAttribute("isContained");
		if (!isContained) {
			MemberDictionary memberDictionary = new MemberDictionary();
			memberDictionary.setDictionary(dictionary);
			memberDictionary.setMember(member);
			memberDictionaryService.saveOne(memberDictionary);
		}

		return "redirect:/dictionary/{dicWriter}/{dicId}";
	}

	// expressing not good
	@GetMapping(value = "/dictionary/{dicWriter}/{dicId}/notGood")
	public String dictionaryNotGood(HttpSession session) {

		// dictionary
		Dictionary dictionary = (Dictionary) session.getAttribute("dic");

		// member
		String id = (String) session.getAttribute("session_id");
		Member member = memberService.findById(id).get();

		// relationship
		boolean isContained = (boolean) session.getAttribute("isContained");
		if (isContained) {
			List<MemberDictionary> memberDictionaries = dictionary.getMemberDictionaries();
			for (int i = 0; i < memberDictionaries.size(); i++) {
				if (memberDictionaries.get(i).getMember().getIdAuto().equals(member.getIdAuto())) {
					memberDictionaryService.deleteOne(memberDictionaries.get(i));
					break;
				}
			}

		}
		return "redirect:/dictionary/{dicWriter}/{dicId}";
	}

	// deleting words of the dictionary
	@GetMapping(value = "/dictionary/{dicWriter}/{dicId}/delete")
	public String dictionaryDeleteGet(HttpSession session, @PathVariable String dicId) {

		Dictionary dictionary = dictionaryService.findById(Long.parseLong(dicId)).get();
		memberDictionaryService.deleteOne(dictionary.getMemberDictionaries().get(0));
		dictionaryService.deleteDictionary(dictionary);

		return "redirect:/dictionary_list/mine";
	}

	@GetMapping(value = "/dictionary/{dicWriter}/{dicId}/{wordIds}/delete")
	public String wordDeleteGet(HttpSession session, @PathVariable String dicWriter, @PathVariable String dicId,
			@PathVariable String wordIds) {

		String[] wordId_arr = wordIds.split(",");
		for (int i = 0; i < wordId_arr.length; i++) {
			Word word = wordService.findOne(Long.parseLong(wordId_arr[i])).get();
			wordService.deleteOne(word);
		}

		return "redirect:/dictionary/{dicWriter}/{dicId}";
	}

	// testing words of the dictionary
	@GetMapping(value = "/dictionary/{dicWriter}/{dicId}/{memberId}/test")
	public String dictionaryTestGet(HttpSession session, @PathVariable String dicWriter, @PathVariable String dicId,
			@PathVariable String memberId) {

		@SuppressWarnings("unchecked")
		List<Word> words = (List<Word>) session.getAttribute("words_random");

		List<WordTest> wordsForTest = new ArrayList<>();

		for (int i = 0; i < words.size(); i++) {
			String meaning = words.get(i).getMeaning();
			String meaningForProblem = "";

			String[] meaningLine = meaning.split("\n");

			for (String str : meaningLine) {
				str = str.trim();
				if (str.contains("<strong>")) {
					meaningForProblem += str + "\n";
				}
			}

			WordTest wordTest = new WordTest("", words.get(i).getWord(), meaningForProblem);
			wordsForTest.add(wordTest);

		}

		session.setAttribute("words_for_test", wordsForTest);

		return "/Dictionary/DictionaryTest";

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/dictionary/{dicWriter}/{dicId}/{memberId}/test")
	public String dictionaryTestPost(HttpSession session, @PathVariable String dicWriter, @PathVariable String dicId,
			@PathVariable String memberId, @RequestBody HashMap<String, Object> data) {

		// showing words not passed
		// getting json data
		Integer testPassed = (Integer) data.get("testPassed");
		Integer testSubmitted = (Integer) data.get("testSubmitted");
		List<Integer> testNotPassedList = (List<Integer>) data.get("testNotPassedList");

		List<Word> words = (List<Word>) session.getAttribute("words_random");
		List<Word> wordsNotPassed = new ArrayList<>();
		for (int i = 0; i < words.size(); i++) {
			if (testNotPassedList.contains(i)) {
				wordsNotPassed.add(words.get(i));
			}

			// saving result to DB
			else {
				Word word = words.get(i);
				Integer testCnt = word.getTestCount() - 1;

				if (testCnt.equals(0)) {
					wordService.deleteOne(word);
				} else {
					word.setTestCount(testCnt);
					wordService.saveOne(word);
				}
			}
		}

		session.setAttribute("words_not_passed", wordsNotPassed);
		session.setAttribute("test_all", words.size());
		session.setAttribute("test_passed", testPassed);
		session.setAttribute("test_submitted", testSubmitted);

		return "redirect:/dictionary/{dicWriter}/{dicId}/{memberId}/test/result";

	}

	// showing result of the test
	@GetMapping(value = "/dictionary/{dicWriter}/{dicId}/{memberId}/test/result")
	public String dictionaryTestResultGet(HttpSession session, @PathVariable String dicWriter,
			@PathVariable String dicId, @PathVariable String memberId) {

		return "/Dictionary/DictionaryTestResult";

	}

	// opening words_remembered list
	@GetMapping(value = "/words_remembered/{memberId}/{language}")
	public String wordsRememberedGet(HttpSession session, Model model, @PathVariable String memberId,
			@PathVariable String language) {
		// showing title
		String lanToShow = null;
		Lan lan = null;
		if (language.equals("english")) {
			lanToShow = "영어";
			lan = Lan.ENG;
		} else if (language.equals("japanese")) {
			lanToShow = "일본어";
			lan = Lan.JAP;
		} else if (language.equals("korean")) {
			lanToShow = "한국어";
			lan = Lan.KOR;
		} else if (language.equals("spanish")) {
			lanToShow = "스페인어";
			lan = Lan.SPA;
		}
		session.setAttribute("language", lanToShow);

		int lanCnt = 0;
		List<WordRemembered> words = wordRememberedService.findAll();

		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).getLan().equals(lan)) {
				lanCnt++;
			}
		}
		session.setAttribute("language_cnt", lanCnt);

		// choosing the way to sort
		List<Option> options = new ArrayList<>();
		options.add(new Option("random", "무작위순"));
		options.add(new Option("time", "저장순"));
		options.add(new Option("character", "문자열순"));
		options.add(new Option("character", "횟수순"));

		model.addAttribute("options", options);

		return "Dictionary/WordsRemembered";
	}

	// opening words_remembered list
	@GetMapping(value = "/words_remembered/{memberId}/{language}/{page}")
	public String wordsRememberedListGet(HttpSession session, Model model, @PathVariable String memberId,
			@PathVariable String language, @PathVariable String page) {

		// showing all remembered words
		List<WordRemembered> words = wordRememberedService.findAll();
		words.add(0, null);
		List<WordRemembered> words_1 = new ArrayList<>();
		List<WordRemembered> words_2 = new ArrayList<>();
		List<WordRemembered> words_3 = new ArrayList<>();

		int nowPage = Integer.parseInt(page);
		nowPage--;

		for (int i = 1; i < words.size(); i++) {

			if (i >= 1 + (nowPage * 60) && i < 21 + (nowPage * 60)) {
				words_1.add(words.get(i));
			} else if (i >= 21 + (nowPage * 60) && i < 41 + (nowPage * 60)) {
				words_2.add(words.get(i));
			} else if (i >= 41 + (nowPage * 60) && i < 61 + (nowPage * 60)) {
				words_3.add(words.get(i));
			}

		}

		session.setAttribute("words_1", words_1);
		session.setAttribute("words_2", words_2);
		session.setAttribute("words_3", words_3);

		return "Dictionary/WordsRememberedList";
	}

	// 더미 추가하기
	@GetMapping(value = "/words_remembered/add_dummy")
	public String wordRememberedTmp01(HttpSession session, Model model) {
		System.out.println("더미 일괄 추가!!");

		String language = (String) session.getAttribute("language");
		String languageForLink = null;
		Long memberId = (Long) session.getAttribute("member_idAuto");
		Member member = memberService.findByIdAuto(memberId).get();

		Lan lan = null;
		if (language.equals(language)) {
			lan = Lan.ENG;
			languageForLink = "english";
		} else if (language.equals(language)) {
			lan = Lan.JAP;
			languageForLink = "japanese";
		} else if (language.equals(language)) {
			lan = Lan.KOR;
			languageForLink = "korean";
		} else if (language.equals(language)) {
			lan = Lan.SPA;
			languageForLink = "spanish";
		}

		for (int i = 1; i <= 500; i++) {
			WordRemembered wordRemembered = new WordRemembered();

			wordRemembered.setLan(lan);
			wordRemembered.setCount(1);
			wordRemembered.setWord("tmp" + i);
			wordRememberedService.saveOne(wordRemembered);

			MemberDictionary memberDictionary = new MemberDictionary();
			memberDictionary.setMember(member);
			memberDictionary.setWordRemembered(wordRemembered);
			memberDictionaryService.saveOne(memberDictionary);

		}

		String url_redirect = "/words_remembered/" + Long.toString(memberId) + "/" + languageForLink;

		return "redirect:" + url_redirect;
	}

	// 더미 제거하기
	@GetMapping(value = "/words_remembered/delete_dummy")
	public String wordRememberedTmp02(HttpSession session, Model model) {

		String language = (String) session.getAttribute("language");
		String languageForLink = null;
		Long memberId = (Long) session.getAttribute("member_idAuto");
		Member member = memberService.findByIdAuto(memberId).get();

		Lan lan = null;
		if (language.equals(language)) {
			lan = Lan.ENG;
			languageForLink = "english";
		} else if (language.equals(language)) {
			lan = Lan.JAP;
			languageForLink = "japanese";
		} else if (language.equals(language)) {
			lan = Lan.KOR;
			languageForLink = "korean";
		} else if (language.equals(language)) {
			lan = Lan.SPA;
			languageForLink = "spanish";
		}

		// deleting memberDictionary
		List<MemberDictionary> memberDictionaries = memberDictionaryService.findAll();
		List<MemberDictionary> memberDictionariesToDelete = new ArrayList<>();

		for (int i = 0; i < memberDictionaries.size(); i++) {
			MemberDictionary memberDictionaryToDelete = memberDictionaries.get(i);
			Member memberToDelete = memberDictionaryToDelete.getMember();
			WordRemembered wordRememberedToDelete = memberDictionaryToDelete.getWordRemembered();

			if (wordRememberedToDelete == null) {
				continue;
			}

			if (memberToDelete.getId().equals(member.getId()) && wordRememberedToDelete.getLan().equals(lan)) {
				memberDictionariesToDelete.add(memberDictionaryToDelete);
			}

		}

		for (int i = 0; i < memberDictionariesToDelete.size(); i++) {
			// deleting foreign key
			memberDictionaryService.deleteOne(memberDictionariesToDelete.get(i));

			// deleting primary key
			WordRemembered wordRememberedToDelete = memberDictionariesToDelete.get(i).getWordRemembered();
			wordRememberedService.deleteOne(wordRememberedToDelete);
		}

		String url_redirect = "/words_remembered/" + Long.toString(memberId) + "/" + languageForLink;

		return "redirect:" + url_redirect;
	}

	@PatchMapping(value = "/words_remembered/{memberId}/{language}/random")
	public String wordsRememberedPatchRandom(HttpSession session, Model model, @PathVariable String memberId,
			@PathVariable String language) throws UnsupportedEncodingException {

		System.out.println("랜덤");

		return "/Dictionary/Dictionary";
	}

	@PatchMapping(value = "/words_remembered/{memberId}/{language}/time")
	public String wordsRememberedPatchTime(HttpSession session, Model model, @PathVariable String memberId,
			@PathVariable String language) throws UnsupportedEncodingException {

		System.out.println("시간");

		return "/Dictionary/Dictionary";
	}

	@PatchMapping(value = "/words_remembered/{memberId}/{language}/character")
	public String wordsRememberedPatchCharacter(HttpSession session, Model model, @PathVariable String memberId,
			@PathVariable String language) throws UnsupportedEncodingException {

		System.out.println("문자열");

		return "/Dictionary/Dictionary";
	}

}
