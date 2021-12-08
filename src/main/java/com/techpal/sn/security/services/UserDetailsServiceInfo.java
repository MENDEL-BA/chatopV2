package com.techpal.sn.security.services;

import com.techpal.sn.dto.UserDto;
import com.techpal.sn.models.RendezVous;
import com.techpal.sn.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDetailsServiceInfo {

    User getUserByExternalId(String externalId);

    Optional<User> getUser();

    User updateUser(UserDto userDto);

    void deleteUser(String uidUser);

    List<RendezVous> getAllRendezVousForUser(User user);
}