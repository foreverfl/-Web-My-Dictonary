package com.example.My.Dictonary.DTO;

import java.util.ArrayList;
import java.util.List;

import com.example.My.Dictonary.Entities.Dictionary;
import com.example.My.Dictonary.Entities.Member;
import com.example.My.Dictonary.Entities.MemberDictionary;
import com.example.My.Dictonary.Entities.Enum.Dic;
import com.example.My.Dictonary.Entities.Enum.Lan;

public class DictionaryDTO {
	private long idAuto;
	private String author;
	private String name;
	private Dic dic;
	private Lan lan;
	private int maxMeaning;
	private int maxExam;
	private int maxTest;
	private boolean isPublic;
	private String description;
	private List<Member> members = new ArrayList<>();
	private List<MemberDictionary> memberdictionaries;
	private Dictionary dictionaryInSetting;

	public long getIdAuto() {
		return idAuto;
	}

	public void setIdAuto(long idAuto) {
		this.idAuto = idAuto;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public int getMaxExam() {
		return maxExam;
	}

	public void setMaxExam(int maxExam) {
		this.maxExam = maxExam;
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

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public List<MemberDictionary> getMemberdictionaries() {
		return memberdictionaries;
	}

	public void setMemberdictionaries(List<MemberDictionary> memberdictionaries) {
		this.memberdictionaries = memberdictionaries;
	}

	public Dictionary getDictionaryInSetting() {
		return dictionaryInSetting;
	}

	public void setDictionaryInSetting(Dictionary dictionaryInSetting) {
		this.dictionaryInSetting = dictionaryInSetting;
	}

	public DictionaryDTO() {

	}

	public DictionaryDTO(long idAuto, String author, String name, Dic dic, Lan lan, int maxMeaning, int maxExam,
			int maxTest, boolean isPublic, String description, List<Member> members,
			List<MemberDictionary> memberdictionaries, Dictionary dictionaryInSetting) {
		super();
		this.idAuto = idAuto;
		this.author = author;
		this.name = name;
		this.dic = dic;
		this.lan = lan;
		this.maxMeaning = maxMeaning;
		this.maxExam = maxExam;
		this.maxTest = maxTest;
		this.isPublic = isPublic;
		this.description = description;
		this.members = members;
		this.memberdictionaries = memberdictionaries;
		this.dictionaryInSetting = dictionaryInSetting;
	}
	
	@Override
	public String toString() {
		return "DictionarySettingDTO [idAuto=" + idAuto + ", author=" + author + ", name=" + name + ", dic=" + dic
				+ ", lan=" + lan + ", maxMeaning=" + maxMeaning + ", maxExam=" + maxExam + ", maxTest=" + maxTest
				+ ", isPublic=" + isPublic + ", description=" + description + ", members=" + members
				+ ", memberdictionaries=" + memberdictionaries + ", dictionaryInSetting=" + dictionaryInSetting + "]";
	}

}
