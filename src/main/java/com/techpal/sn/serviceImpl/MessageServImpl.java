package com.techpal.sn.serviceImpl;

import com.techpal.sn.models.Message;
import com.techpal.sn.models.UserEntity;
import com.techpal.sn.repository.MessageRepository;
import com.techpal.sn.repository.UserRepository;
import com.techpal.sn.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    @Autowired
    private MessageServImpl(MessageRepository messageRepository,  UserDetailsService userDetailsService,
                            UserRepository userRepository){
        this.messageRepository = messageRepository;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    public Message addMessage(Message message) {
        Optional<UserEntity> user = userRepository.findByEmail(String.valueOf(userDetailsService.loadUserByUsername(message.getUser().getEmail())));
        message.setUser(user.get());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessages() {
       return messageRepository.findAll();
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Message not found"));
    }
    
}
