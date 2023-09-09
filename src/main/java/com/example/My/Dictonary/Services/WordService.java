package com.example.My.Dictonary.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.My.Dictonary.Entities.Word;
import com.example.My.Dictonary.Repositories.WordRepository;

@Service
@Transactional
public class WordService {

	@Autowired
	WordRepository wordRepository;

	public void saveOne(Word word) {
		wordRepository.save(word);
	}
	
	public void deleteOne(Word word) {
		wordRepository.delete(word);
	}
	
	public Optional<Word> findOne(Long idAuto) {
		return wordRepository.findById(idAuto);
	}


	public List<Word> findAll() {
		return wordRepository.findAll();
	}

}
