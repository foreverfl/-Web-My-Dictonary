package com.example.My.Dictonary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.My.Dictonary.DTO.MemberDTO;
import com.example.My.Dictonary.Entities.Dictionary;
import com.example.My.Dictonary.Entities.Member;
import com.example.My.Dictonary.Entities.MemberDictionary;
import com.example.My.Dictonary.Repositories.MemberRepository;
import com.example.My.Dictonary.Services.DictionaryService;
import com.example.My.Dictonary.Services.EmailService;
import com.example.My.Dictonary.Services.MemberDictionaryService;
import com.example.My.Dictonary.Services.MemberService;
import com.example.My.Dictonary.Services.WordService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;

@SpringBootTest
class MyDictonaryApplicationTests {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberDictionaryService memberDictionaryService;

	@Autowired
	DictionaryService dictionaryService;

	@Autowired
	WordService wordService;

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	EmailService emailService;

	@Autowired
	ModelMapper modelMapper;

	@Test
	@Disabled("completed")
	public void authViewController() throws NoSuchAlgorithmException, UnsupportedEncodingException {

		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			System.out.println(providers[i]);
		}
		System.out.println();

		SecureRandom random = new SecureRandom();
		System.out.println("random: " + random);
		String state = new BigInteger(130, random).toString();
		System.out.println("state:" + state);
		System.out.println();

	}

	@Test
	@Disabled("completed")
	public void requestToServer() throws IOException {
		String apiURL = "https://www.naver.com";
		URL url = new URL(apiURL);
		System.out.println(url);
		System.out.println();

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		System.out.println(con.toString());
		System.out.println();

		con.setRequestMethod("GET");
		con.setRequestProperty("sample", "apple");
		System.out.println("con.getRequestProperty(\"sample\"): " + con.getRequestProperty("sample"));
		System.out.println();

		int responseCode = con.getResponseCode();
		System.out.println("responseCode: " + responseCode);
	}

	@Test
	@Disabled("completed")
	public void Mapper() {
		Member member = new Member();
		member.setId("admin");
		member.setPw("1234");

		MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);

		assertEquals(member.getId(), memberDTO.getId());

	}

	@Test
	@Disabled("completed")
	public void springSecurity() {
		// memberDTO
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId("test");
		memberDTO.setPw("1234");
		memberDTO.setNickname("test");
		memberDTO.setEmail("admin_test@gmail.com");
		memberDTO.setPremium(false); // In a boolean value, null is not permitted.
		memberDTO.setAuth("Member");

		System.out.println(memberDTO.getAuthorities());

		System.out.println("memberDTO: " + memberDTO);

		// member
		Member member = modelMapper.map(memberDTO, Member.class);

		Date time = new Date();

		member.setAuth("Member");
		member.setJoinedDate(time);

		System.out.println("member: " + member);

		// after encoding
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setPw(passwordEncoder.encode(member.getPw()));

		System.out.println("member: " + member);

		memberRepository.save(member);
	}

	@Test
	@Disabled("completed")
	public void getDicFromMember() {
		Optional<Member> member = memberRepository.findById("admin");

		List<MemberDictionary> memberDictionaries = member.get().getMemberDictionaries();

		for (int i = 0; i < memberDictionaries.size(); i++) {
			Dictionary dictionary = memberDictionaries.get(i).getDictionary();
			System.out.println(dictionary);
		}
	}

	@Test
	@Disabled("completed")
	public void getAllDics() {
		List<Dictionary> dics = dictionaryService.findAll();
		for (int i = 0; i < dics.size(); i++) {
			System.out.println(dics.get(i));
		}
	}

	// Iamport test

	@Test
	@Disabled("completed")
	public void IamportGeToken() {
		try {
			String test_api_key = "5104107106082538";
			String test_api_secret = "EksVTSPJD2qvgdQNtFGSQVwrnbtJLprSw3Lt4jRq9Jdz8Z1G368Kc2OT8T1n9ebwxxTQk7dYnT7ZziXN";
			IamportClient client = new IamportClient(test_api_key, test_api_secret);

			IamportResponse<AccessToken> auth_response = client.getAuth();

			System.out.println(auth_response.getResponse());
			System.out.println(auth_response.getResponse().getToken());

		} catch (IamportResponseException e) {
			System.out.println(e.getMessage());

			switch (e.getHttpStatusCode()) {
			case 401:
				break;
			case 500:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Disabled("completed")
	public void localeTest() {
		Locale defaultLocale = Locale.getDefault();
		System.out.println(defaultLocale);

		// change
		Locale.setDefault(Locale.ENGLISH);

		defaultLocale = Locale.getDefault();
		System.out.println(defaultLocale);

	}

	@Test
	@Disabled("completed")
	public void dateIncreaseTest() {
		List<String> dateArr = new ArrayList<>();

		LocalDate tmpDate = LocalDate.parse("2022-09-08");
		dateArr.add(tmpDate.toString());
		LocalDate lastDate = LocalDate.now().plusDays(1);

		while (true) {

			tmpDate = tmpDate.plusDays(1);

			if (tmpDate.equals(lastDate)) {
				break;
			}

			dateArr.add(tmpDate.toString());
		}

		for (int i = 0; i < dateArr.size(); i++) {
			System.out.println(dateArr.get(i));
		}

	}

	@Test
	@Disabled("completed")
	public void splitTest() {
		String now = LocalDate.now().toString();
		String path = "C:\\upload\\" + now;
		String[] folderPathForDB_1 = path.split("\\\\");
		String folderPathForDB_2 = folderPathForDB_1[folderPathForDB_1.length - 1];

		System.out.println(folderPathForDB_2);

		String photo = "/photo/8adc4e87-e29e-43c9-852d-79133fd29a25_12_429_yc3XsCZQ15I..jpg";
		String[] photoPath_1 = photo.split("/");

		for (int i = 0; i < photoPath_1.length; i++) {
			System.out.println(photoPath_1[i]);
		}

	}
	
	@Test
	public void randomObjectTest() {
		String random_1 = emailService.createKey();
		String random_2 = emailService.createKey();
		
		System.out.println(random_1);
		System.out.println(random_2);
		System.out.println(random_1 == random_2);
		System.out.println(random_1.equals(random_2));
		
	}

}
