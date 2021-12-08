package com.techpal.sn.security.services;

import com.techpal.sn.dto.FactureDto;
import com.techpal.sn.models.Facture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FactureService {

    Facture createFacture(FactureDto factureDto);

    Facture getFactureByExternalId(String uidFacture);

    void deleteFacture(String uidFacture);

    Page<Facture> getFactureByPatient(String uidPatient, Pageable pageable);

    Facture updateFacture(FactureDto factureDto);


}
