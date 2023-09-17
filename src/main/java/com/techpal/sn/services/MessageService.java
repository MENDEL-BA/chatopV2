package com.techpal.sn.services;

import com.techpal.sn.dto.MessageDTO;
import com.techpal.sn.models.Messages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    
     Messages addMessage(MessageDTO message);

     List<Messages> getAllMessages();

     Messages getMessageById(Long id);

     void deleteMessage(Long Id);

     Messages updateMessage(Long id, MessageDTO messageDTO);

}
