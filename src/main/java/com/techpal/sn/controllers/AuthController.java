package com.techpal.sn.controllers;

import com.techpal.sn.dto.LoginDto;
import com.techpal.sn.dto.RegisterDto;
import com.techpal.sn.models.Role;
import com.techpal.sn.models.UserEntity;
import com.techpal.sn.payload.response.AuthSuccess;
import com.techpal.sn.repository.RoleRepository;
import com.techpal.sn.repository.UserRepository;
import com.techpal.sn.security.JWTGenerator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Api(description = "Account management APIs")
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        AuthSuccess authSuccess = new AuthSuccess(token);
        // Retournez la réponse avec l'objet AuthSuccess
        return ResponseEntity.status(HttpStatus.OK).body(authSuccess);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/me", produces ={ "application/json" })
    @ApiOperation(
            value = "Api pour recuperer les infos de l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<?>  getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == null) {
            new RuntimeException("Veuillez vous authentifier d'abord!");
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Optional<UserEntity> userEntity = userRepository.findByEmail(userDetails.getUsername());
        UserInfoResponse userDTO = new UserInfoResponse();
        userDTO.setId(Long.valueOf(userEntity.get().getId()));
        userDTO.setName(userEntity.get().getName());
        userDTO.setEmail(userEntity.get().getEmail());
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("register")
    @ApiOperation(
            value = "Api pour s'enregistrer l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<String> register(@ApiParam(value = "le corps de la requete DTO", required = true)@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @RequestMapping(value = "/",method = RequestMethod.OPTIONS)
    public void handleOptionsRequest() {
    }
}
