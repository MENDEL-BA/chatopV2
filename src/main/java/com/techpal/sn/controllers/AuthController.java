package com.techpal.sn.controllers;

import com.techpal.sn.dto.LoginDto;
import com.techpal.sn.dto.RegisterDto;
import com.techpal.sn.payload.response.AuthSuccess;
import com.techpal.sn.services.UserServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
@Api(description = "Account management APIs")
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServices userServices;

    public AuthController(UserServices userServices) {
        this.userServices = userServices;
    }

    
    @PostMapping("login")
    @ApiOperation(
            value = "Api pour se connecter generation JWT token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in and JWT token generated"),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Authentication failed")
    })
    public ResponseEntity<?> login(@ApiParam(value = "le corps de la requete DTO", required = true) @RequestBody LoginDto loginDto){
        AuthSuccess authSuccess = userServices.loggedIn(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(authSuccess);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/me", produces ={ "application/json" })
    @ApiOperation(
            value = "Api pour recuperer les infos de l'utilisateur",authorizations = { @Authorization(value="jwtToken") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<?>  getUser() {
        UserInfoResponse userInfoResponse = userServices.getUser();
        return ResponseEntity.ok(userInfoResponse);
    }
    
    @PostMapping("register")
    @ApiOperation(
            value = "Api pour s'enregistrer l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<AuthSuccess> register(@ApiParam(value = "le corps de la requete DTO", required = true)@RequestBody RegisterDto registerDto) {
        AuthSuccess authSuccess = userServices.register(registerDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(authSuccess);
    }

}
