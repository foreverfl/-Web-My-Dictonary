package com.example.My.Dictonary.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.My.Dictonary.DTO.MemberDTO;
import com.example.My.Dictonary.Entities.Member;
import com.example.My.Dictonary.Repositories.MemberRepository;

@Service
@Transactional
public class MemberService implements UserDetailsService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	MemberRepository memberRepository;

	public void signUp(MemberDTO memberDTO) {

		memberDTO.setPremium(false);
		memberDTO.setAuth("ROLE_MEMBER");

		Date time = new Date();
		memberDTO.setJoinedDate(time);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		memberDTO.setPw(passwordEncoder.encode(memberDTO.getPw()));

		Member member = modelMapper.map(memberDTO, Member.class);

		// validation
		validateDuplicateMember(member);

		memberRepository.save(member);
	}

	private void validateDuplicateMember(Member member) {

		Optional<Member> findMembers = memberRepository.findById(member.getId());
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

		Optional<Member> member = memberRepository.findById(id);

		Member memberLogin = member.get();

		if (memberLogin == null)
			throw new UsernameNotFoundException("User not authorized.");

		MemberDTO memberDTO = modelMapper.map(memberLogin, MemberDTO.class);

		return memberDTO;
	}

	public void saveOne(Member member) {
		memberRepository.save(member);
	}

	public void deleteOne(Member member) {
		memberRepository.delete(member);
	}

	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	public Optional<Member> findByIdAuto(Long idAuto) {

		return memberRepository.findById(idAuto);
	}

	public Optional<Member> findById(String id) {
		return memberRepository.findById(id);
	}

	public Optional<Member> findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}
	
	public Optional<Member> findByNickname(String nickname) {
		return memberRepository.findByNickname(nickname);
	}

}
