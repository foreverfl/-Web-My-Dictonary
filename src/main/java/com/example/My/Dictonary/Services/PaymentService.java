package com.example.My.Dictonary.Services;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

	String getToken() throws IOException;

	int paymentInfo(String imp_uid, String access_token) throws IOException;

	public void paymentCancel(String access_token, String imp_uid, int amount, String reason) throws IOException;
}
