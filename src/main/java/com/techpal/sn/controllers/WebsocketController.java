package com.techpal.sn.controllers;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebsocketController {

    @MessageMapping("/socket/someoneJoined")
    @SendTo("/socket/someoneJoined")
    public String someoneJoined(String message) {
        //messagingTemplate.convertAndSend("/topic/progress", "Hello world");
        return message;
    }
}
