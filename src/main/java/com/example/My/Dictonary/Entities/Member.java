package com.example.My.Dictonary.Entities;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long idAuto;

	@Column(unique = true, nullable = false)
	private String id;

	private String pw;

	@Column(unique = true, nullable = false)
	private String nickname;

	@Column(unique = true, nullable = false)
	private String email;

	private String photo;
	private String photoFolder;
	private Long exp;
	private boolean isPremium;
	private boolean isSNS;

	private Date joinedDate;
	private Date payedDate;
	private Long recommended;
	private Integer recommendingCnt;
	private String auth;

	private boolean nicknameIsPublic;
	private boolean emailIsPublic;
	private boolean expIsPublic;
	private boolean postIsPublic;
	private boolean commentIsPublic;

	@OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
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

	public String getPhotoFolder() {
		return photoFolder;
	}

	public void setPhotoFolder(String photoFolder) {
		this.photoFolder = photoFolder;
	}

	public Long getExp() {
		return exp;
	}

	public void setExp(Long exp) {
		this.exp = exp;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public boolean isSNS() {
		return isSNS;
	}

	public void setSNS(boolean isSNS) {
		this.isSNS = isSNS;
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

	public Long getRecommended() {
		return recommended;
	}

	public void setRecommended(Long recommended) {
		this.recommended = recommended;
	}

	public Integer getRecommendingCnt() {
		return recommendingCnt;
	}

	public void setRecommendingCnt(Integer recommendingCnt) {
		this.recommendingCnt = recommendingCnt;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public boolean isNicknameIsPublic() {
		return nicknameIsPublic;
	}

	public void setNicknameIsPublic(boolean nicknameIsPublic) {
		this.nicknameIsPublic = nicknameIsPublic;
	}

	public boolean isEmailIsPublic() {
		return emailIsPublic;
	}

	public void setEmailIsPublic(boolean emailIsPublic) {
		this.emailIsPublic = emailIsPublic;
	}

	public boolean isExpIsPublic() {
		return expIsPublic;
	}

	public void setExpIsPublic(boolean expIsPublic) {
		this.expIsPublic = expIsPublic;
	}

	public boolean isPostIsPublic() {
		return postIsPublic;
	}

	public void setPostIsPublic(boolean postIsPublic) {
		this.postIsPublic = postIsPublic;
	}

	public boolean isCommentIsPublic() {
		return commentIsPublic;
	}

	public void setCommentIsPublic(boolean commentIsPublic) {
		this.commentIsPublic = commentIsPublic;
	}

	public List<MemberDictionary> getMemberDictionaries() {
		return memberDictionaries;
	}

	public void setMemberDictionaries(List<MemberDictionary> memberDictionaries) {
		this.memberDictionaries = memberDictionaries;
	}

	@Override
	public int hashCode() {
		return Objects.hash(auth, commentIsPublic, email, emailIsPublic, exp, expIsPublic, id, idAuto, isPremium, isSNS,
				joinedDate, memberDictionaries, nickname, nicknameIsPublic, payedDate, photo, photoFolder, postIsPublic,
				pw, recommended, recommendingCnt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		return Objects.equals(auth, other.auth) && commentIsPublic == other.commentIsPublic
				&& Objects.equals(email, other.email) && emailIsPublic == other.emailIsPublic
				&& Objects.equals(exp, other.exp) && expIsPublic == other.expIsPublic && Objects.equals(id, other.id)
				&& Objects.equals(idAuto, other.idAuto) && isPremium == other.isPremium && isSNS == other.isSNS
				&& Objects.equals(joinedDate, other.joinedDate)
				&& Objects.equals(memberDictionaries, other.memberDictionaries)
				&& Objects.equals(nickname, other.nickname) && nicknameIsPublic == other.nicknameIsPublic
				&& Objects.equals(payedDate, other.payedDate) && Objects.equals(photo, other.photo)
				&& Objects.equals(photoFolder, other.photoFolder) && postIsPublic == other.postIsPublic
				&& Objects.equals(pw, other.pw) && Objects.equals(recommended, other.recommended)
				&& Objects.equals(recommendingCnt, other.recommendingCnt);
	}

}
