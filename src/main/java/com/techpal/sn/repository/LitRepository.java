package com.techpal.sn.repository;

import com.techpal.sn.models.Chambre;
import com.techpal.sn.models.Lit;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LitRepository extends JpaRepository<Lit, Long> {

    Lit findByLinkedMeta(Meta meta);

    //Lit findByChambre(Chambre chambre);


    List<Lit> findByPatient(Patient patient);

    List<Lit> findLitsByEtatIsFalse();
}
