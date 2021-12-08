package com.techpal.sn.repository;


import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findAll(Pageable pageable);

    Patient findByLinkedMeta(Meta meta);

    Patient findByNumeroTelephonePatient(String nnumeroTelephone);

}
