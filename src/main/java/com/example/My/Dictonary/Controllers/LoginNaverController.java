package com.example.My.Dictonary.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginNaverController {

	private String CLIENT_ID = "I_YVK1GJnm1knGUymP4Y";
	private String CLI_SECRET = "fYWCwJwI40";

	// view controller
	@RequestMapping("/naver")
	public String viewController(HttpSession session, Model model)
			throws UnsupportedEncodingException, UnknownHostException {
		String redirectURI = URLEncoder.encode("http://localhost:8080/naver/callback", "UTF-8");
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		apiURL += String.format("&client_id=%s&redirect_uri=%s&state=%s", CLIENT_ID, redirectURI, state);
		session.setAttribute("state", state);
		model.addAttribute("apiURL", apiURL);
		return "test-naver";
	}

	// callback page controller
	@RequestMapping("/naver/callback")
	public String naverCallback(HttpSession session, HttpServletRequest request, Model model)
			throws IOException, ParseException {
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String redirectURI = URLEncoder.encode("http://localhost:8080/naver/callback", "UTF-8");
		String apiURL;
		apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		apiURL += "client_id=" + CLIENT_ID;
		apiURL += "&client_secret=" + CLI_SECRET;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		apiURL += "&state=" + state;
		System.out.println("apiURL=" + apiURL);
		String res = requestToServer(apiURL);
		if (res != null && !res.equals("")) {
			model.addAttribute("res", res);
			Map<String, Object> parsedJson = new JSONParser(res).parseObject();
			System.out.println(parsedJson);
			session.setAttribute("currentUser", res);
			session.setAttribute("currentAT", parsedJson.get("access_token"));
			session.setAttribute("currentRT", parsedJson.get("refresh_token"));
		} else {
			model.addAttribute("res", "Login failed!");
		}
		return "test-naver-callback";
	}

	// controller for request for updating token
	@RequestMapping("/naver/refreshToken")
	public String refreshToken(HttpSession session, HttpServletRequest request, Model model, String refreshToken)
			throws IOException, ParseException {
		String apiURL;
		apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=refresh_token&";
		apiURL += "client_id=" + CLIENT_ID;
		apiURL += "&client_secret=" + CLI_SECRET;
		apiURL += "&refresh_token=" + refreshToken;
		System.out.println("apiURL=" + apiURL);
		String res = requestToServer(apiURL);
		model.addAttribute("res", res);
		session.invalidate();
		return "test-naver-callback";
	}

	// controller for deleting a token
	@RequestMapping("/naver/deleteToken")
	public String deleteToken(HttpSession session, HttpServletRequest request, Model model, String accessToken)
			throws IOException {
		String apiURL;
		apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=delete&";
		apiURL += "client_id=" + CLIENT_ID;
		apiURL += "&client_secret=" + CLI_SECRET;
		apiURL += "&access_token=" + accessToken;
		apiURL += "&service_provider=NAVER";
		System.out.println("apiURL=" + apiURL);
		String res = requestToServer(apiURL);
		model.addAttribute("res", res);
		session.invalidate();
		return "test-naver-callback";
	}

	// Getting profile through the access token
	@ResponseBody
	@RequestMapping("/naver/getProfile")
	public String getProfileFromNaver(String accessToken) throws IOException {
		String apiURL = "https://openapi.naver.com/v1/nid/me";
		String headerStr = "Bearer " + accessToken;
		String res = requestToServer(apiURL, headerStr);
		return res;
	}

	@RequestMapping("/naver/invalidate")
	public String invalidateSession(HttpSession session) {
		session.invalidate();
		return "redirect:/naver";
	}

	// method for communicating with server
	private String requestToServer(String apiURL) throws IOException {
		return requestToServer(apiURL, "");
	}

	private String requestToServer(String apiURL, String headerStr) throws IOException {
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		System.out.println("header Str: " + headerStr);
		if (headerStr != null && !headerStr.equals("")) {
			con.setRequestProperty("Authorization", headerStr);
		}
		int responseCode = con.getResponseCode();
		BufferedReader br;
		if (responseCode == 200) {
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		String inputLine;
		StringBuffer res = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			res.append(inputLine);
		}
		br.close();
		if (responseCode == 200) {
			return res.toString();
		} else {
			return null;
		}
	}
}
