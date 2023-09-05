package com.techpal.sn.controllers;

import com.techpal.sn.models.Message;
import com.techpal.sn.repository.MessageRepository;
import com.techpal.sn.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageService messageService,MessageRepository messageRepository) {
        this.messageService = messageService;
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message savedMessage = messageService.addMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message message) {
        Message existingMessage = messageService.getMessageById(id);
        
        if (existingMessage == null) {
            return ResponseEntity.notFound().build();
        }
        
        existingMessage.setMessage(message.getMessage());
        existingMessage.setUpdatedAt(Timestamp.from(Instant.now()));
        
        Message updatedMessage = messageRepository.save(existingMessage);
        return ResponseEntity.ok(updatedMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        Message existingMessage = messageService.getMessageById(id);
        
        if (existingMessage == null) {
            return ResponseEntity.notFound().build();
        }
        
        messageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
