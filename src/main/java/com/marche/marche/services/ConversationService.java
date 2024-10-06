package com.marche.marche.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marche.marche.modele.Conversation;
import com.marche.marche.modele.Personne;
import com.marche.marche.repository.ConversationRepository;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository cr;

    public void saveConversation(Conversation conversation) {
        cr.save(conversation);
    }

    public Conversation getConversationByVendeurAndAcheteur(Personne vendeur, Personne acheteur) {
        return cr.findByVendeurAndAcheteur(vendeur, acheteur);
    }
}
