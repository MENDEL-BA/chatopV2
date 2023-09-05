package com.techpal.sn.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class OptionsController {

    @RequestMapping(value = "/api/auth", method = RequestMethod.OPTIONS)
    public void handleOptionsRequest() {
        // Ne rien faire ici, simplement répondre à la requête OPTIONS avec succès
    }
}
