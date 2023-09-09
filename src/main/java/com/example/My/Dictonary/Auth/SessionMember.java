package com.example.My.Dictonary.Auth;

import java.io.Serializable;

import com.example.My.Dictonary.Entities.Member;

public class SessionMember implements Serializable {

	private static final long serialVersionUID = 3934906279034087915L;
	private String name;
	private String email;
	private String picture;

	public SessionMember(Member member) {
		this.name = member.getNickname();
		this.email = member.getEmail();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPicture() {
		return picture;
	}

}
