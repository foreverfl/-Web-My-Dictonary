package com.example.My.Dictonary.Auth;

import java.util.Map;

public class OAuthAttributes {
	Map<String, Object> attributes;
	String nameAttributeKey;
	String name;
	String email;

	public OAuthAttributes() {

	}

	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getNameAttributeKey() {
		return nameAttributeKey;
	}

	public void setNameAttributeKey(String nameAttributeKey) {
		this.nameAttributeKey = nameAttributeKey;
	}

	public String getName() {
		return name;
	}

	public void setNickname(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
			Map<String, Object> attributes) {

		OAuthAttributes oAuthAttributes = new OAuthAttributes();
		oAuthAttributes.setNameAttributeKey(userNameAttributeName);
		oAuthAttributes.setNickname((String) attributes.get("name"));
		oAuthAttributes.setEmail((String) attributes.get("email"));
		oAuthAttributes.setAttributes(attributes);

		return oAuthAttributes;

	}

	public static OAuthAttributes ofGoogle(String registrationId, String userNameAttributeName,
			Map<String, Object> attributes) {
		OAuthAttributes oAuthAttributes = new OAuthAttributes();
		oAuthAttributes.setNameAttributeKey(userNameAttributeName);
		oAuthAttributes.setNickname((String) attributes.get("name"));
		oAuthAttributes.setEmail((String) attributes.get("email"));
		oAuthAttributes.setAttributes(attributes);

		return oAuthAttributes;
	}

}
