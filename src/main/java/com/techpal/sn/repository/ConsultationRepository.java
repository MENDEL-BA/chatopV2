package com.techpal.sn.repository;


import com.techpal.sn.models.Consultation;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    Page<Consultation> findAll(Pageable pageable);

    Page<Consultation> findByPatient(Patient patient, Pageable pageable);

    Page<Consultation> findByPatientAndUser(Patient patient, User medecin, Pageable pageable);

    Page<Consultation> findByUser(User medecin, Pageable pageable);

    Consultation findByLinkedMeta(Meta meta);

    void deleteByLinkedMeta(Meta meta);
}
