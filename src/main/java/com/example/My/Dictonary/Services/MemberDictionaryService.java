package com.example.My.Dictonary.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.My.Dictonary.Entities.MemberDictionary;
import com.example.My.Dictonary.Repositories.MemberDictionaryRepository;

@Service
@Transactional
public class MemberDictionaryService {

	@Autowired
	MemberDictionaryRepository memberDictionaryRepository;

	public void saveOne(MemberDictionary memberDictionary) {
		memberDictionaryRepository.save(memberDictionary);
	}

	public void deleteOne(MemberDictionary memberDictionary) {
		memberDictionaryRepository.delete(memberDictionary);
	}

	public List<MemberDictionary> findAll() {
		return memberDictionaryRepository.findAll();
	}

}
