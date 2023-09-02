package com.techpal.sn.repository;

import com.techpal.sn.models.ERole;
import com.techpal.sn.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
      Optional<Role> findByName(ERole name);

}
