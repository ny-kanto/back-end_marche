package com.marche.marche.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marche.marche.modele.Message;
import com.marche.marche.repository.MessageRepository;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository mr;

    public void saveMessage(Message message) {
        mr.save(message);
    }
}
