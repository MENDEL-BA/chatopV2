package com.techpal.sn.dto;

import com.techpal.sn.models.Facture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class FactureDto implements Serializable {

    private LocalDate datepaiement;

    private Boolean estReglee;

    private Integer montantFacture;

    private String uidFacture;

    private String uidPatient;

    private FactureDto(Facture facture){
        this.datepaiement = facture.getDatePaiement();
        this.montantFacture = facture.getMontantFacture();
        this.uidFacture = facture.getLinkedMeta().getExternalId();
        this.estReglee = facture.getEstReglee();
        this.uidPatient = facture.getPatient().getLinkedMeta().getExternalId();
    }

    public static FactureDto parse(Facture Facture) {
        return new FactureDto(Facture);
    }

    public static List<FactureDto> parseAll(List<Facture> factures) {
        return factures.stream().map(FactureDto::parse).collect(Collectors.toList());
    }

}
