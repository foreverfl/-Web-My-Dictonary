package com.example.My.Dictonary.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.My.Dictonary.Entities.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findById(String id);

	Optional<Member> findByEmail(String email);
	
	Optional<Member> findByNickname(String nickname);


}
