package com.techpal.sn.repository;

import com.techpal.sn.dto.RentalDTO;
import com.techpal.sn.models.Rentals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rentals, Long> {

    Optional<Rentals> findById(Long id);

    @Query("SELECT NEW com.techpal.sn.dto.RentalDTO(r.id, r.name, r.surface, r.price, r.picture, r.description, r.owner.id, r.createdAt, r.updatedAt) FROM Rentals r")
    List<RentalDTO> findAllAsDTO();
}
