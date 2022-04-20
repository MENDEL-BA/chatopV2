package com.techpal.sn.security.services;

import com.techpal.sn.dto.FactureDto;
import com.techpal.sn.models.Facture;

import java.time.LocalDate;
import java.util.List;

public interface FactureService {

    Facture createFacture(FactureDto factureDto);

    Facture getFactureByExternalId(String uidFacture);

    void deleteFacture(String uidFacture);

    List<Facture> getFactureByPatient(String uidPatient);

    Facture updateFacture(FactureDto factureDto);

    Integer getBeneficeOfRange(LocalDate dateDebut);

    List<Facture> getMontantFactureForPatientForNotReglee(String uidPatient);

    Integer getbeneficeForToday();


}
