package com.techpal.sn.repository;

import com.techpal.sn.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Ajoutez des méthodes personnalisées si nécessaire
}
