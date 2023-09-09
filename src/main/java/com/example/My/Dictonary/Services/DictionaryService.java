package com.example.My.Dictonary.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.My.Dictonary.Entities.Dictionary;
import com.example.My.Dictonary.Repositories.DictionaryRepository;

@Service
@Transactional
public class DictionaryService {

	@Autowired
	DictionaryRepository dictionaryRepository;

	public void saveDictionary(Dictionary dictionary) {
		dictionaryRepository.save(dictionary);
	}
	
	public void deleteDictionary(Dictionary dictionary) {
		dictionaryRepository.delete(dictionary);
	}

	public List<Dictionary> findAll() {
		return dictionaryRepository.findAll();
	}
	
	public Optional<Dictionary> findById(Long idAuto) {
		return dictionaryRepository.findById(idAuto);
	}

	public Optional<Dictionary> findByAuthor(String writer) {
		return dictionaryRepository.findByWriter(writer);
	}

	public Optional<Dictionary> findByName(String name) {
		return dictionaryRepository.findByName(name);
	}
}
