package com.example.My.Dictonary.Entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MemberDictionary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_DICTIONARY_ID")
	private Long idAuto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DICTIONARY_ID")
	private Dictionary dictionary;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WORD_REMEMBERED_ID")
	private WordRemembered wordRemembered;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "POST_ID")
	private Post post;

	public Long getIdAuto() {
		return idAuto;
	}

	public void setIdAuto(Long idAuto) {
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

	public WordRemembered getWordRemembered() {
		return wordRemembered;
	}

	public void setWordRemembered(WordRemembered wordRemembered) {
		this.wordRemembered = wordRemembered;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dictionary, idAuto, member, post, wordRemembered);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberDictionary other = (MemberDictionary) obj;
		return Objects.equals(dictionary, other.dictionary) && Objects.equals(idAuto, other.idAuto)
				&& Objects.equals(member, other.member) && Objects.equals(post, other.post)
				&& Objects.equals(wordRemembered, other.wordRemembered);
	}

	@Override
	public String toString() {
		return "MemberDictionary [idAuto=" + idAuto + ", member=" + member + ", dictionary=" + dictionary
				+ ", wordRemembered=" + wordRemembered + ", post=" + post + "]";
	}

}
