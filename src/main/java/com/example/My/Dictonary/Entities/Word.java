package com.example.My.Dictonary.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Word {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WORD_ID")
	private long idAuto;

	private String word;
	private String pronoun;

	@Lob
	private String meaning;

	private Integer testCount;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DICTIONARY_ID") // 외래키 매핑
	private Dictionary dictionary;

	public long getIdAuto() {
		return idAuto;
	}

	public void setIdAuto(long idAuto) {
		this.idAuto = idAuto;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPronoun() {
		return pronoun;
	}

	public void setPronoun(String pronoun) {
		this.pronoun = pronoun;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public Integer getTestCount() {
		return testCount;
	}

	public void setTestCount(Integer testCount) {
		this.testCount = testCount;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

//	@Override
//	public String toString() {
//		return "Word [idAuto=" + idAuto + ", word=" + word + "]";
//	}
		

}
