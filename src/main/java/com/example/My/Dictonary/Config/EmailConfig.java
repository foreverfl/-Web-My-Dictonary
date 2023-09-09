package com.example.My.Dictonary.Config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {
	@Value("${mail.smtp.port}")
	private int port;
	@Value("${mail.smtp.socketFactory.port}")
	private int socketPort;
	@Value("${mail.smtp.auth}")
	private boolean auth;
	@Value("${mail.smtp.starttls.enable}")
	private boolean starttls;
	@Value("${mail.smtp.starttls.required}")
	private boolean startlls_required;
	@Value("${mail.smtp.socketFactory.fallback}")
	private boolean fallback;
	@Value("${AdminMail.id}")
	private String id;
	@Value("${AdminMail.password}")
	private String password;

	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setUsername(id);
		javaMailSender.setPassword(password);
		javaMailSender.setPort(port);
		javaMailSender.setJavaMailProperties(getMailProperties());
		javaMailSender.setDefaultEncoding("UTF-8");
		return javaMailSender;
	}

	private Properties getMailProperties() {
		Properties property = new Properties();
		property.put("mail.smtp.socketFactory.port", socketPort);
		property.put("mail.smtp.auth", auth);
		property.put("mail.smtp.starttls.enable", starttls);
		property.put("mail.smtp.starttls.required", startlls_required);
		property.put("mail.smtp.socketFactory.fallback", fallback);
		property.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		return property;
	}
}
