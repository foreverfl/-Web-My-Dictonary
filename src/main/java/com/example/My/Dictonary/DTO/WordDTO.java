package com.example.My.Dictonary.DTO;

import com.example.My.Dictonary.Entities.Dictionary;

public class WordDTO {

	private long idAuto;
	private String word;
	private String pronoun;
	private String meaning;
	private Integer testCount;
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

	public WordDTO() {

	}

	public WordDTO(long idAuto, String word, String pronoun, String meaning, Integer testCount, Dictionary dictionary) {
		super();
		this.idAuto = idAuto;
		this.word = word;
		this.pronoun = pronoun;
		this.meaning = meaning;
		this.testCount = testCount;
		this.dictionary = dictionary;
	}

	@Override
	public String toString() {
		return "WordDTO [idAuto=" + idAuto + ", word=" + word + ", pronoun=" + pronoun + ", meaning=" + meaning
				+ ", testCount=" + testCount + ", dictionary=" + dictionary + "]";
	}

}
