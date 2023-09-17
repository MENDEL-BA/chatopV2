package com.techpal.sn.controllers;

import com.techpal.sn.models.UserEntity;
import com.techpal.sn.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    @ApiOperation(
            value = "Api pour recuperer les infos de l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return ResponseEntity.ok(userEntity);
    }
}
