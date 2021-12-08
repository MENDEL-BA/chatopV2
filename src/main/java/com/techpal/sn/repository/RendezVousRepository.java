package com.techpal.sn.repository;

import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.RendezVous;
import com.techpal.sn.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    Page<RendezVous> findAll(Pageable pageable);

    Page<RendezVous> findByUser(User user, Pageable pageable);

    Page<RendezVous> findByPatient(Patient patient, Pageable pageable);

    Page<RendezVous> findByUserAndPatient(User user, Patient patient, Pageable pageable);

    RendezVous findByLinkedMeta(Meta meta);

    List<RendezVous> findAllByUser(User user);

}
