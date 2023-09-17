package com.techpal.sn.controllers;

import com.techpal.sn.dto.LoginDto;
import com.techpal.sn.dto.RegisterDto;
import com.techpal.sn.payload.response.AuthSuccess;
import com.techpal.sn.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServices userServices;
    @Autowired
    public AuthController(UserServices userServices) {
        this.userServices = userServices;
    }

    @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<AuthSuccess> login(@RequestBody LoginDto loginDto){
        AuthSuccess authSuccess = userServices.loggedIn(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(authSuccess);
    }
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/me", produces ={ "application/json" })
    public ResponseEntity<UserInfoResponse>  getUser() {
        UserInfoResponse userInfoResponse = userServices.getUser();
        return ResponseEntity.ok(userInfoResponse);
    }
    @CrossOrigin
    @PostMapping("register")
    public ResponseEntity<AuthSuccess> register(@RequestBody RegisterDto registerDto) {
        AuthSuccess authSuccess = userServices.register(registerDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(authSuccess);
    }
    @CrossOrigin
    @RequestMapping(value = "/",method = RequestMethod.OPTIONS)
    public void handleOptionsRequest() {
    }
}
