package com.example.My.Dictonary.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.My.Dictonary.Entities.Dictionary;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

	Optional<Dictionary> findByIdAuto(Long idAuto);
	
	Optional<Dictionary> findByWriter(String writer);
	
	Optional<Dictionary> findByName(String name);
	
	
}
