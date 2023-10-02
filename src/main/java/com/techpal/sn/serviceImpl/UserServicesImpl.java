package com.techpal.sn.serviceImpl;

import com.techpal.sn.controllers.UserInfoResponse;
import com.techpal.sn.dto.LoginDto;
import com.techpal.sn.dto.RegisterDto;
import com.techpal.sn.models.Role;
import com.techpal.sn.models.UserEntity;
import com.techpal.sn.payload.response.AuthSuccess;
import com.techpal.sn.repository.RoleRepository;
import com.techpal.sn.repository.UserRepository;
import com.techpal.sn.security.JWTGenerator;
import com.techpal.sn.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final PasswordEncoder passwordEncoder;


    public UserServicesImpl(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthSuccess register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(roles));
        LoginDto loginDto = null;
        UserEntity userceated = userRepository.save(user);
        if(userceated != null){
            loginDto = new LoginDto(registerDto.getEmail(), registerDto.getPassword());
            return loggedIn(loginDto);
       }
       return new AuthSuccess();
    }

    @Override
    public UserInfoResponse getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == null) {
            new RuntimeException("Veuillez vous authentifier d'abord!");
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Optional<UserEntity> userEntity = userRepository.findByEmail(userDetails.getUsername());
        return new UserInfoResponse((long) userEntity.get().getId(), userEntity.get().getEmail(),userEntity.get().getName(),null,null );
    }

    @Override
    public AuthSuccess loggedIn(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        AuthSuccess authSuccess = new AuthSuccess();
        authSuccess.setToken(token);
        return authSuccess;
    }
}
