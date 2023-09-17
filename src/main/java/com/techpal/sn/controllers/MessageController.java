package com.techpal.sn.controllers;

import com.techpal.sn.dto.MessageDTO;
import com.techpal.sn.models.Messages;
import com.techpal.sn.services.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/messages")
@Api(description = "Message management APIs")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
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
        return ResponseEntity.status(HttpStatus.OK).body(messages);
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
        return ResponseEntity.status(HttpStatus.OK).body(messages);
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
        return ResponseEntity.status(HttpStatus.OK).body(savedMessages);
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
    public ResponseEntity<Messages> updateMessage(@PathVariable Long id, @RequestBody MessageDTO messagesDTO) {
        Messages messages = messageService.updateMessage(id,messagesDTO);
        return ResponseEntity.status(HttpStatus.OK).body(messages);

  
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
         messageService.deleteMessage(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @RequestMapping(value = "/",method = RequestMethod.OPTIONS)
    public void handleOptionsRequest() {
    }
}
