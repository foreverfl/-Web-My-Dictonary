package com.example.My.Dictonary.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.My.Dictonary.Entities.WordRemembered;

@Repository
public interface WordRememberedRepository extends JpaRepository<WordRemembered, Long> {

}
