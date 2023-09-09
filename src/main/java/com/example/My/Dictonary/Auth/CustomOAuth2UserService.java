package com.example.My.Dictonary.Auth;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.My.Dictonary.Entities.Member;
import com.example.My.Dictonary.Repositories.MemberRepository;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google, kakao, naver

		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
				.getUserNameAttributeName(); // PK

		// OAuth2UserService
		OAuthAttributes attributes = OAuthAttributes.ofGoogle(registrationId, userNameAttributeName,
				oAuth2User.getAttributes());
		Member member = saveOrUpdate(attributes, userNameAttributeName);

		httpSession.setAttribute("now_id", attributes.getName());
		
		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getAuth())),
				attributes.getAttributes(), attributes.getNameAttributeKey());
	}

	private Member saveOrUpdate(OAuthAttributes attributes, String userNameAttributeName) {

		Optional<Member> members = memberRepository.findByEmail(attributes.getEmail());
		Member member = null;

		if (members.isEmpty()) {
			member = new Member();
			member.setId(userNameAttributeName);
			member.setNickname(attributes.getName());
			member.setEmail(attributes.getEmail());

			member.setPremium(false);
			member.setAuth("ROLE_MEMBER");

			Date time = new Date();
			member.setJoinedDate(time);

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String uuid = UUID.randomUUID().toString().substring(0, 6);
			String password = passwordEncoder.encode(uuid);
			member.setPw(passwordEncoder.encode(password));

			memberRepository.save(member);

			return member;

		} else {

			member = members.get();
			return memberRepository.save(member);
		}

	}
}
