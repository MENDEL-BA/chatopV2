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

    private LocalDate datePaiement;

    private Boolean estReglee;

    private String etatFacture;

    private Integer montantFacture;

    private String uidFacture;

    private String uidPatient;

    private String infosPatients;

    private FactureDto(Facture facture){
        this.datePaiement = facture.getDatePaiement();
        this.montantFacture = facture.getMontantFacture();
        this.uidFacture = facture.getLinkedMeta().getExternalId();
        this.estReglee = facture.getEstReglee();
        this.uidPatient = facture.getPatient().getLinkedMeta().getExternalId();
        this.infosPatients = facture.getPatient().getNomPatient().concat(" ".concat(facture.getPatient().getPrenomPatient()));
        this.etatFacture = etatFactureClient(facture);
    }

    private String etatFactureClient(Facture facture) {
        if (facture.getEstReglee()) {
            return this.etatFacture = "Réglée";
        } else {
            return this.etatFacture = "Non Payée";
        }
    }

    public static FactureDto parse(Facture Facture) {
        return new FactureDto(Facture);
    }

    public static List<FactureDto> parseAll(List<Facture> factures) {
        return factures.stream().map(FactureDto::parse).collect(Collectors.toList());
    }

}
