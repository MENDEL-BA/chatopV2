package com.techpal.sn.controllers;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.techpal.sn.dto.PatientDto;
import com.techpal.sn.dto.UserDto;
import com.techpal.sn.payload.response.JwtResponse;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.UserDetailsServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.techpal.sn.models.ERole;
import com.techpal.sn.models.Role;
import com.techpal.sn.models.User;
import com.techpal.sn.payload.request.LoginRequest;
import com.techpal.sn.payload.request.SignupRequest;
import com.techpal.sn.repository.RoleRepository;
import com.techpal.sn.repository.UserRepository;
import com.techpal.sn.security.jwt.JwtUtils;
import com.techpal.sn.security.services.UserDetailsImpl;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	MetaService metaService;

	@Autowired
	UserDetailsServiceInfo userDetailsServiceInfo;

	@GetMapping("/infos")
	public ResponseEntity<?> infosUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			new MessageResponse("Veuillez vous authentifier d'abord!");
		}

		List<String> roles = auth.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(userDetails.getId(),
												 userDetails.getUidUser(),
												 userDetails.getUsername(),
												 userDetails.getEmail(),
												 roles));
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		/*UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(),
												 userDetails.getUsername(),
												 userDetails.getEmail(),
												 roles));*/
		return ResponseEntity.ok(jwt);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
		SignupRequest signUpRequest = new SignupRequest();

		signUpRequest.setEmail(userDto.getEmail());
		signUpRequest.setPassword(userDto.getPassword());
		signUpRequest.setRole(Collections.singleton(userDto.getRole()));
		signUpRequest.setUsername(userDto.getUsername());
		signUpRequest.setLastName(userDto.getLastName());
		signUpRequest.setNumeroTelephone(userDto.getNumeroTelephone());
		signUpRequest.setFirstName(userDto.getFirstName());

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
								 signUpRequest.getEmail(),
				                 encoder.encode(signUpRequest.getPassword()),
								 signUpRequest.getFirstName(),
								 signUpRequest.getLastName(),
								 signUpRequest.getNumeroTelephone());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					//System.out.println("role -----"+role);
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "ROLE_MEDECIN":
					Role medecinRole = roleRepository.findByName(ERole.ROLE_MEDECIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(medecinRole);

					break;
				case "ROLE_SECRETAIRE":
						Role scretaireRole = roleRepository.findByName(ERole.ROLE_SECRETAIRE)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(scretaireRole);

						break;
				case "ROLE_COMPTABLE":
						Role comptableRole = roleRepository.findByName(ERole.ROLE_COMPTABLE)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(comptableRole);

						break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		user.setLinkedMeta(metaService.createNew(User.class.getName()));
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}


	@GetMapping("/users")
	public List<UserDto> getAllUsers(@RequestParam(defaultValue = "0") int page,
									 @RequestParam(defaultValue = "10") int size) {
		Pageable paging = PageRequest.of(page, size);
		return UserDto.parseAll(userRepository.findAll(paging).stream().collect(Collectors.toList()));

	}

	@GetMapping(value = "/medecins")
	public List<UserDto> getUser(){
		Role userRole = roleRepository.findByName(ERole.ROLE_MEDECIN)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		return userRepository.findByRoles(userRole);
	}


	@DeleteMapping(value = "/users/{uidUser}")
	public void deleteUser(@PathVariable("uidUser") String uidUser){
		 userDetailsServiceInfo.deleteUser(uidUser);
	}

}
