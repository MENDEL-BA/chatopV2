package com.techpal.sn.repository;

import com.techpal.sn.models.Facture;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {


    List<Facture> findAll();

    List<Facture> findByPatient(Patient patient);

    Facture findByLinkedMeta(Meta meta);

    void deleteByLinkedMeta(Meta meta);


}
