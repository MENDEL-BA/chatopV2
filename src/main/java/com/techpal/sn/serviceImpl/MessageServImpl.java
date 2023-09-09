package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.MessageDTO;
import com.techpal.sn.models.Messages;
import com.techpal.sn.models.Rentals;
import com.techpal.sn.models.UserEntity;
import com.techpal.sn.repository.MessageRepository;
import com.techpal.sn.repository.RentalRepository;
import com.techpal.sn.repository.UserRepository;
import com.techpal.sn.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;

    @Autowired
    private MessageServImpl(MessageRepository messageRepository, RentalRepository rentalRepository,
                            UserRepository userRepository){
        this.messageRepository = messageRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Messages addMessage(MessageDTO messages) {
        Optional<UserEntity> user = userRepository.findById(messages.getUser_id());
        Optional<Rentals> rentals = rentalRepository.findById((long) messages.getRental_id());
        Messages message = new Messages();
        message.setUser(user.get());
        message.setRental(rentals.get());
        message.setCreatedAt(Timestamp.from(Instant.now()));
        message.setMessage(messages.getMessage());
        return messageRepository.save(message);
    }

    @Override
    public List<Messages> getAllMessages() {
       return messageRepository.findAll();
    }

    @Override
    public Messages getMessageById(Long id) {
        return messageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Messages not found"));
    }

}
