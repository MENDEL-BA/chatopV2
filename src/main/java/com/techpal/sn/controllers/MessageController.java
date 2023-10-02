package com.techpal.sn.controllers;

import com.techpal.sn.dto.MessageDTO;
import com.techpal.sn.models.Messages;
import com.techpal.sn.services.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@Api(description = "Message management APIs")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    
    @GetMapping
    @ApiOperation(
            value = "Api pour recuperer les messages",authorizations = { @Authorization(value="jwtToken") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<List<Messages>> getAllMessages() {
        List<Messages> messages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
    
    @GetMapping("/{id}")
    @ApiOperation(
            value = "Api pour recuperer un message par son ID",authorizations = { @Authorization(value="jwtToken") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<Messages> getMessageById(@ApiParam(required = true, value = "id", example = "Id du message") @PathVariable Long id) {
        Messages messages = messageService.getMessageById(id);
        if (messages == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
    
    @PostMapping
    @ApiOperation(
            value = "Api pour la creation d'un message",authorizations = { @Authorization(value="jwtToken") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<Messages> createMessage(@ApiParam(required = true, value = "MessageDTO") @RequestBody MessageDTO messages) {
        Messages savedMessages = messageService.addMessage(messages);
        return ResponseEntity.status(HttpStatus.OK).body(savedMessages);
    }
    
    @PutMapping("/{id}")
    @ApiOperation(
            value = "Api pour modifier un message",authorizations = { @Authorization(value="jwtToken") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<Messages> updateMessage(@ApiParam(required = true, value = "id", example = "Id du message") @PathVariable Long id, @ApiParam(required = true, value = "MessageDTO", example = "L'objet MessageDto")@RequestBody MessageDTO messagesDTO) {
        Messages messages = messageService.updateMessage(id,messagesDTO);
        return ResponseEntity.status(HttpStatus.OK).body(messages);

  
    }
    
    @DeleteMapping("/{id}")
    @ApiOperation(
            value = "Api pour supprimer un message",authorizations = { @Authorization(value="jwtToken") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<?> deleteMessage(@ApiParam(required = true, value = "id", example = "Id du message") @PathVariable Long id) {
         messageService.deleteMessage(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
