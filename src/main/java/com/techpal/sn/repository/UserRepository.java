package com.techpal.sn.repository;

import java.util.List;
import java.util.Optional;

import com.techpal.sn.dto.UserDto;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techpal.sn.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	User findByLinkedMeta(Meta meta);

	Page<User> findAll(Pageable pageable);

	List<UserDto> findByRoles(Role names);

}
