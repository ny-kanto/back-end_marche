package com.marche.marche.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marche.marche.modele.Conversation;
import com.marche.marche.modele.Message;
import com.marche.marche.repository.MessageRepository;


import java.util.List;
@Service
public class MessageService {

    @Autowired
    private MessageRepository mr;

    public void saveMessage(Message message) {
        mr.save(message);
    }

    public List<Message> getListMessagesByConversation(Conversation conversation) {
        return mr.findByConversation(conversation);
    }
}
