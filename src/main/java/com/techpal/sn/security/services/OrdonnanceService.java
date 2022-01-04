package com.techpal.sn.security.services;

import com.techpal.sn.dto.OrdonnanceDto;
import com.techpal.sn.models.Ordonnance;

import java.util.List;

public interface OrdonnanceService {

    Ordonnance createOrdonnance(OrdonnanceDto ordonnanceDto);

    Ordonnance updateOrdonnance(OrdonnanceDto ordonnanceDto);

    Ordonnance getOrdonnanceByExternalId(String externalId);

    List<Ordonnance> getOrdonnanceByMedecinAndPatient(String externalIdMedecin, String externalIdPatient);
}
