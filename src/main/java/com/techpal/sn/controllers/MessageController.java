package com.techpal.sn.controllers;

import com.techpal.sn.dto.MessageDTO;
import com.techpal.sn.models.Messages;
import com.techpal.sn.repository.MessageRepository;
import com.techpal.sn.services.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/messages")
@Api(description = "Message management APIs")
public class MessageController {

    private final MessageService messageService;

    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageService messageService,MessageRepository messageRepository) {
        this.messageService = messageService;
        this.messageRepository = messageRepository;
    }

    @CrossOrigin
    @GetMapping
    @ApiOperation(
            value = "Api pour recuperer les messages")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<List<Messages>> getAllMessages() {
        List<Messages> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
    @CrossOrigin
    @GetMapping("/{id}")
    @ApiOperation(
            value = "Api pour recuperer un message par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<Messages> getMessageById(@PathVariable Long id) {
        Messages messages = messageService.getMessageById(id);
        if (messages == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(messages);
    }
    @CrossOrigin
    @PostMapping
    @ApiOperation(
            value = "Api pour la creation d'un message")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<Messages> createMessage(@RequestBody MessageDTO messages) {
        Messages savedMessages = messageService.addMessage(messages);
        return ResponseEntity.ok(savedMessages);
    }
    @CrossOrigin
    @PutMapping("/{id}")
    @ApiOperation(
            value = "Api pour modifier un message")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
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
    @CrossOrigin
    @DeleteMapping("/{id}")
    @ApiOperation(
            value = "Api pour supprimer un message")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        Messages existingMessages = messageService.getMessageById(id);
        
        if (existingMessages == null) {
            return ResponseEntity.notFound().build();
        }
        
        messageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @RequestMapping(value = "/",method = RequestMethod.OPTIONS)
    public void handleOptionsRequest() {
    }
}
