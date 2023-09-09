package com.example.My.Dictonary.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.My.Dictonary.Entities.WordRemembered;
import com.example.My.Dictonary.Repositories.WordRememberedRepository;

@Service
public class WordRememberedService {

	@Autowired
	WordRememberedRepository wordRememberedRepository;

	public void saveOne(WordRemembered wordRemembered) {
		wordRememberedRepository.save(wordRemembered);
	}

	public void deleteOne(WordRemembered wordRemembered) {
		wordRememberedRepository.delete(wordRemembered);
	}

	public void deleteAll() {
		wordRememberedRepository.deleteAll();
	}

	public List<WordRemembered> findAll() {
		return wordRememberedRepository.findAll();
	}

}
