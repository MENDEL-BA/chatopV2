package com.techpal.sn.controllers;

import com.techpal.sn.dto.MessageDTO;
import com.techpal.sn.models.Messages;
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
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageService messageService,MessageRepository messageRepository) {
        this.messageService = messageService;
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public ResponseEntity<List<Messages>> getAllMessages() {
        List<Messages> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Messages> getMessageById(@PathVariable Long id) {
        Messages messages = messageService.getMessageById(id);
        if (messages == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<Messages> createMessage(@RequestBody MessageDTO messages) {
        Messages savedMessages = messageService.addMessage(messages);
        return ResponseEntity.ok(savedMessages);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Messages> updateMessage(@PathVariable Long id, @RequestBody Messages messages) {
        Messages existingMessages = messageService.getMessageById(id);
        
        if (existingMessages == null) {
            return ResponseEntity.notFound().build();
        }
        
        existingMessages.setMessage(messages.getMessage());
        existingMessages.setUpdatedAt(Timestamp.from(Instant.now()));
        
        Messages updatedMessages = messageRepository.save(existingMessages);
        return ResponseEntity.ok(updatedMessages);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        Messages existingMessages = messageService.getMessageById(id);
        
        if (existingMessages == null) {
            return ResponseEntity.notFound().build();
        }
        
        messageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
