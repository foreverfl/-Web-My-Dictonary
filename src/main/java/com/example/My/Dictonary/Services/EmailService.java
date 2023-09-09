package com.example.My.Dictonary.Services;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
	void sendSimpleMessage(HttpSession session, String to) throws Exception;

	String createKey();
}
