package com.techpal.sn.repository;

import com.techpal.sn.models.DossierMedical;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierMedicalRepository extends JpaRepository<DossierMedical, Long> {

    DossierMedical findDossierMedicalsByLinkedMeta(Meta meta);

    void deleteByLinkedMeta(String linkedMeta);

    DossierMedical findByPatient(Patient patient);

    DossierMedical findByMedecin(User medecin);

    DossierMedical findByMedecinAndPatient(User medecin, Patient patient);

}
