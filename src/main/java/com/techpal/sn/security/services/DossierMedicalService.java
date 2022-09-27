package com.techpal.sn.security.services;

import com.techpal.sn.dto.DossierMedicalDto;
import com.techpal.sn.models.DossierMedical;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.User;

public interface DossierMedicalService {


    DossierMedical saveDossierMedical(DossierMedicalDto dossierMedicalDto);

    void deleteDossierMedical(DossierMedicalDto dossierMedicalDto);

    DossierMedical getByExternalId(String externalId);

    DossierMedical getByPatient(String  uidPatient);

    DossierMedical getByMedecin(String uidMedecin);

    DossierMedical getByMedecinAndPatient(User medecin, Patient patient);



}
