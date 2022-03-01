package com.techpal.sn.repository;

import java.util.List;
import java.util.Optional;

import com.techpal.sn.dto.UserDto;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Role;
import com.techpal.sn.models.SpecialiteMedecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.techpal.sn.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	User findByLinkedMeta(Meta meta);

	List<UserDto> findByRoles(Role names);

	List<User> findByLocationOrAndSpecialiteMedecin(@Nullable String location, SpecialiteMedecin specialiteMedecin);

	List<User> findBySpecialiteMedecin(SpecialiteMedecin specialiteMedecin);

	List<User> findByLocation(@Nullable String location);

}
