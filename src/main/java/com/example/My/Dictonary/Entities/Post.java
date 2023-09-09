package com.example.My.Dictonary.Entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POST_ID")
	private Long idAuto;

	@Lob
	private String title;

	@Lob
	private String content;

	private String writer;

	private Date date;

	private Long view;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "post")
	private List<MemberDictionary> memberDictionaries;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "post")
	private List<Comment> comments;

	public Long getIdAuto() {
		return idAuto;
	}

	public void setIdAuto(Long idAuto) {
		this.idAuto = idAuto;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getView() {
		return view;
	}

	public void setView(Long view) {
		this.view = view;
	}

	public List<MemberDictionary> getMemberDictionaries() {
		return memberDictionaries;
	}

	public void setMemberDictionaries(List<MemberDictionary> memberDictionaries) {
		this.memberDictionaries = memberDictionaries;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
