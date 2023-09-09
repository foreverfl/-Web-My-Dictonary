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

import com.example.My.Dictonary.Entities.Enum.Dic;
import com.example.My.Dictonary.Entities.Enum.Lan;

@Entity
public class Dictionary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DICTIONARY_ID")
	private long idAuto;

	private String writer;
	private String name;

	@Enumerated(EnumType.STRING)
	private Dic dic;

	@Enumerated(EnumType.STRING)
	private Lan lan;

	private int maxMeaning;
	private int maxTest;
	private boolean isPublic;
	private String description;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "dictionary")
	private List<MemberDictionary> memberDictionaries;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "dictionary")
	private List<Word> words;

	public long getIdAuto() {
		return idAuto;
	}

	public void setIdAuto(long idAuto) {
		this.idAuto = idAuto;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dic getDic() {
		return dic;
	}

	public void setDic(Dic dic) {
		this.dic = dic;
	}

	public Lan getLan() {
		return lan;
	}

	public void setLan(Lan lan) {
		this.lan = lan;
	}

	public int getMaxMeaning() {
		return maxMeaning;
	}

	public void setMaxMeaning(int maxMeaning) {
		this.maxMeaning = maxMeaning;
	}

	public int getMaxTest() {
		return maxTest;
	}

	public void setMaxTest(int maxTest) {
		this.maxTest = maxTest;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MemberDictionary> getMemberDictionaries() {
		return memberDictionaries;
	}

	public void setMemberDictionaries(List<MemberDictionary> memberDictionaries) {
		this.memberDictionaries = memberDictionaries;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}

}
