package com.techpal.sn.repository;

import com.techpal.sn.models.Menu;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByUserAndIsAuthorizedIsTrue(User user);

    Menu findByExternalId(Meta meta);

}
