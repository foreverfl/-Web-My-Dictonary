package com.example.My.Dictonary.DTO;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.My.Dictonary.Entities.MemberDictionary;

public class MemberDTO implements UserDetails, OAuth2User {

	private MemberDTO memberDTO;
	private Map<String, Object> attributes;

	// default constructor
	public MemberDTO() {

	}

	// form login
	public MemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}

	// oauth2 login
	public MemberDTO(MemberDTO memberDTO, Map<String, Object> attributes) {
		this.memberDTO = memberDTO;
		this.attributes = attributes;
	}

	private static final long serialVersionUID = 3538723164595520825L;
	private Long idAuto;
	private String id;
	private String pw;
	private String nickname;
	private String email;
	private String photo;
	private Boolean isPremium;
	private Date joinedDate;
	private Date payedDate;
	private String auth;
	private List<MemberDictionary> memberDictionaries;

	public Long getIdAuto() {
		return idAuto;
	}

	public void setIdAuto(Long idAuto) {
		this.idAuto = idAuto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public Date getPayedDate() {
		return payedDate;
	}

	public void setPayedDate(Date payedDate) {
		this.payedDate = payedDate;
	}

	public List<MemberDictionary> getMemberDictionaries() {
		return memberDictionaries;
	}

	public void setMemberDictionaries(List<MemberDictionary> memberDictionaries) {
		this.memberDictionaries = memberDictionaries;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "MemberDTO [idAuto=" + idAuto + ", id=" + id + ", pw=" + pw + ", nickname=" + nickname + ", email="
				+ email + ", photo=" + photo + ", isPremium=" + isPremium + ", joinedDate=" + joinedDate
				+ ", payedDate=" + payedDate + ", auth=" + auth + ", memberDictionaries=" + memberDictionaries + "]";
	}

	public MemberDTO(Long idAuto, String id, String pw, String nickname, String email, String photo, Boolean isPremium,
			Date joinedDate, Date payedDate, String auth, List<MemberDictionary> memberDictionaries) {
		super();
		this.idAuto = idAuto;
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.email = email;
		this.photo = photo;
		this.isPremium = isPremium;
		this.joinedDate = joinedDate;
		this.payedDate = payedDate;
		this.auth = auth;
		this.memberDictionaries = memberDictionaries;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(this.auth));
	}

	@Override
	public String getPassword() {
		return this.pw;

	}

	@Override
	public String getUsername() {
		return this.id;

	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	// oauth
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		String sub = attributes.get("sub").toString();
		return sub;
	}

}
