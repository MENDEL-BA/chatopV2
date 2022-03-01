package com.techpal.sn.repository;


import com.techpal.sn.models.Consultation;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    List<Consultation> findAll();

    List<Consultation> findByPatient(Patient patient);

    List<Consultation> findByPatientAndUser(Patient patient, User medecin);

    List<Consultation> findByUser(User medecin);

    Consultation findByLinkedMeta(Meta meta);

    void deleteByLinkedMeta(Meta meta);
}
