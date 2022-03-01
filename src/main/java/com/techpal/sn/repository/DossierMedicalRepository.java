package com.techpal.sn.repository;

import com.techpal.sn.models.DossierMedical;
import com.techpal.sn.models.Meta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierMedicalRepository extends JpaRepository<DossierMedical, Long> {

    DossierMedical findDossierMedicalsByLinkedMeta(Meta meta);

}
