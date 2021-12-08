package com.techpal.sn.security.services;

import com.techpal.sn.dto.UserDto;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.RendezVous;
import com.techpal.sn.repository.RendezVousRepository;
import com.techpal.sn.repository.RoleRepository;
import com.techpal.sn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techpal.sn.models.User;
import org.springframework.web.client.RestClientException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailsServiceInfo {
	@Autowired
	UserRepository userRepository;

	@Autowired
	MetaService metaService;

	@Autowired
	RendezVousRepository rendezVousRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

	@Override
	public User getUserByExternalId(String externalId) {
		if (externalId == null) {
			throw new IllegalStateException("Le uuid du user est null");
		}

		Meta meta = metaService.findByExternalId(externalId);

		if (meta == null) {
			throw new IllegalStateException("Le meta est null");
		}

		return userRepository.findByLinkedMeta(meta);
	}

	public Optional<User> getUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}

		Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username).
				orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
		return user;
	}

	@Override
	public User updateUser(UserDto userDto) {

		if (userDto == null || userDto.getUidUser() == null) {
 			throw new RestClientException("Un des parametres est null");
		}

		User user = getUserByExternalId(userDto.getUidUser());

		if (user == null) {
			throw new RestClientException("L'utilisateur n'existe pas");
		}
		Set<String> roleUser = new HashSet<>();



		//Set<String> strRoles = user.getRoles();
		/*Set<Role> roles = new HashSet<>();
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

		user.setRoles(roles);*/
		return null;
	}

	@Override
	public void deleteUser(String uidUser) {
		if (uidUser == null) {
			throw new RestClientException("Un des parametres est null");
		}

		User user = getUserByExternalId(uidUser);

		if (user == null) {
			throw new RestClientException("L'utilisateur n'existe pas");
		}

		userRepository.delete(user);
	}

	@Override
	public List<RendezVous> getAllRendezVousForUser(User user) {

		if (user == null) {
			throw new RestClientException("L'utilisatuer est null");
		}

		return rendezVousRepository.findAllByUser(user);
	}

}
