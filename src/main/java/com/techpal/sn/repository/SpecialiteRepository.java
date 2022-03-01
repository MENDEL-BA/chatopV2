package com.techpal.sn.repository;

import com.techpal.sn.models.Meta;
import com.techpal.sn.models.SpecialiteMedecin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialiteRepository extends JpaRepository<SpecialiteMedecin, Long> {


    SpecialiteMedecin findByLinkedMeta(Meta meta);

    SpecialiteMedecin findByNomSpecialite(String nomSpecialite);

    List<SpecialiteMedecin> findAll();
}
