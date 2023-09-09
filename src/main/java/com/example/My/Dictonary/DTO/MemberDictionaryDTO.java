package com.example.My.Dictonary.DTO;

import com.example.My.Dictonary.Entities.Dictionary;
import com.example.My.Dictonary.Entities.Member;

public class MemberDictionaryDTO {

	private long idAuto;
	private Member member;
	private Dictionary dictionary;

	public long getIdAuto() {
		return idAuto;
	}

	public void setIdAuto(long idAuto) {
		this.idAuto = idAuto;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

}
