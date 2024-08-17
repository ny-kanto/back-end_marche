package com.marche.marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marche.marche.modele.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
