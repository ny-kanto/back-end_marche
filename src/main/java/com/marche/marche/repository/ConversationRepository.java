package com.marche.marche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marche.marche.modele.Conversation;
import com.marche.marche.modele.Personne;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    Conversation findByVendeurAndAcheteur(Personne vendeur, Personne acheteur);
}
