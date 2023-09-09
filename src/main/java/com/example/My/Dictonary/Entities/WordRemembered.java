package com.example.My.Dictonary.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.example.My.Dictonary.Entities.Enum.Lan;

@Entity
public class WordRemembered {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WORD_REMEMBERED_ID")
	private Long idAuto;

	private String word;

	@Enumerated(EnumType.STRING)
	private Lan lan;

	private Integer count;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "wordRemembered")
	private List<MemberDictionary> memberDictionaries;

	public Long getIdAuto() {
		return idAuto;
	}

	public void setIdAuto(Long idAuto) {
		this.idAuto = idAuto;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Lan getLan() {
		return lan;
	}

	public void setLan(Lan lan) {
		this.lan = lan;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<MemberDictionary> getMemberDictionaries() {
		return memberDictionaries;
	}

	public void setMemberDictionaries(List<MemberDictionary> memberDictionaries) {
		this.memberDictionaries = memberDictionaries;
	}

}
