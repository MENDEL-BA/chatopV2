package com.techpal.sn.repository;

import com.techpal.sn.models.RentalUserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalUserMessageRepository extends JpaRepository<RentalUserMessage, Long> {
}
