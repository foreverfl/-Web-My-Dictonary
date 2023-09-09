package com.example.My.Dictonary.VO;

public class WordTest {

	private String wordMine;
	private String word;
	private String meaning;

	public WordTest(String wordMine, String word, String meaning) {
		super();
		this.wordMine = wordMine;
		this.word = word;
		this.meaning = meaning;
	}

	public String getWordMine() {
		return wordMine;
	}

	public void setWordMine(String wordMine) {
		this.wordMine = wordMine;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

}
