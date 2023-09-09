package com.example.My.Dictonary.Services;

import java.security.SecureRandom;
import java.util.Random;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	JavaMailSender emailSender;
	
	private MimeMessage createMessage(HttpSession session, String to) throws Exception {
		System.out.println("보내는 대상: " + to);
		String key = (String) session.getAttribute("email_code");
		System.out.println("인증 번호: " + key);
		
		MimeMessage message = emailSender.createMimeMessage();

		message.addRecipients(RecipientType.TO, to);
		message.setSubject("My Dictionary 회원가입 인증 메일입니다."); // title

		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<br>";
		msgg += "<h3 align='center'>아래 코드를 회원가입 창으로 돌아가 입력해주세요</h3>";
		msgg += "<br>";
		msgg += "<div align='center' style='width: 500px; margin: 0 auto; border:1px grey solid; border-radius: 10px;'>";
		msgg += "<div style='font-size:130%'>";
		msgg += "<br>CODE: <strong>";
		msgg += key + "</strong><div><br/> ";
		msgg += "</div>";
		
		message.setText(msgg, "utf-8", "html");
		message.setFrom(new InternetAddress("forevermfl@gmail.com", "My Dictionary"));

		return message;
	}

	@Override
	public String createKey() {
		StringBuffer key = new StringBuffer();
		Random random = new SecureRandom();

		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(3); 

			switch (index) {
			case 0:
				// lower case(a~z)
				key.append((char) ((int) (random.nextInt(26)) + 97));
				break;
			case 1:
				// upper case(A~Z)
				key.append((char) ((int) (random.nextInt(26)) + 65));
				break;
			case 2:
				// number(0~9)
				key.append((random.nextInt(10)));
				break;
			}
		}

		return key.toString();
	}

	@Override
	public void sendSimpleMessage(HttpSession session, String to) throws Exception {

		MimeMessage message = createMessage(session, to);

		try {
			emailSender.send(message);
			
		} catch (MailException es) {
			
			es.printStackTrace();
			throw new IllegalArgumentException();
			
		}

	}
}
