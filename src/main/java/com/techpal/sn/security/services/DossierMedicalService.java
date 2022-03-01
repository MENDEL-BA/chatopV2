package com.techpal.sn.security.services;

import com.techpal.sn.dto.DossierMedicalDto;
import com.techpal.sn.models.DossierMedical;

public interface DossierMedicalService {


    DossierMedical saveDossierMedical(DossierMedicalDto dossierMedicalDto);

    DossierMedical deleteDossierMedical(DossierMedicalDto dossierMedicalDto);

    DossierMedical getByExternalId(String externalId);


}
