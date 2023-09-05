package com.techpal.sn.services;

import com.techpal.sn.models.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    
     Message addMessage(Message message);

     List<Message> getAllMessages();

     Message getMessageById(Long id);

}
