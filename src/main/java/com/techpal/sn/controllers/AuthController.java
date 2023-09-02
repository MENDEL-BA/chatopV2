package com.techpal.sn.controllers;

import com.techpal.sn.models.ERole;
import com.techpal.sn.models.Role;
import com.techpal.sn.models.User;
import com.techpal.sn.models.UserDetailsImpl;
import com.techpal.sn.payload.request.LoginRequest;
import com.techpal.sn.payload.request.SignupRequest;
import com.techpal.sn.payload.response.AuthSuccess;
import com.techpal.sn.repository.RoleRepository;
import com.techpal.sn.repository.UserRepository;
import com.techpal.sn.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

//@CrossOrigin(origins = "http://localhost:4200/login", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
public class AuthController {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login", produces ={ "application/json" }, consumes = { "application/json" })
    public ResponseEntity<?>  authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        AuthSuccess authSuccess = new AuthSuccess(jwt);
        // Retournez la réponse avec l'objet AuthSuccess
        return ResponseEntity.status(HttpStatus.OK).body(authSuccess);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/me", produces ={ "application/json" })
    public ResponseEntity<?>  getUser(@AuthenticationPrincipal UserDetails userDetail) {
        System.out.println("User Details: " + SecurityContextHolder.getContext().getAuthentication());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        if (auth.getPrincipal() == null) {
            new RuntimeException("Veuillez vous authentifier d'abord!");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            // L'utilisateur est authentifié
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Accédez aux informations de l'utilisateur ici
            System.out.println("/////userDetails "+userDetails);

        }
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        System.out.println("/////+++++++========="+username+" eeee"+auth.getName());
        return ResponseEntity.ok(userDetails);
        // Retournez la réponse avec l'objet AuthSuccess
      //  return ResponseEntity.status(HttpStatus.OK).body(user);

    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getName());

        Set<Role> roles = new HashSet<>();
        Set<String> strRoles= new HashSet<>();
        if (signUpRequest.getRole() == null) {
            strRoles = signUpRequest.getRole();
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "user":
                        Role modRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles); // Assign roles to the user
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
