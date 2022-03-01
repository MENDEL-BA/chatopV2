package com.techpal.sn.security.services;

import com.techpal.sn.dto.FactureDto;
import com.techpal.sn.models.Facture;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FactureService {

    Facture createFacture(FactureDto factureDto);

    Facture getFactureByExternalId(String uidFacture);

    void deleteFacture(String uidFacture);

    List<Facture> getFactureByPatient(String uidPatient);

    Facture updateFacture(FactureDto factureDto);


}
