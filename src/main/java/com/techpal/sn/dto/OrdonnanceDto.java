package com.techpal.sn.dto;

import com.techpal.sn.models.Ordonnance;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class OrdonnanceDto implements Serializable {

    private String medicamentsPrescrits;

    private LocalDate datePrescription;

    private String uidMedecin;

    private String infosMedecin;

    private String infosPatient;

    private String uidPatient;

    private String linkedMeta;

    public OrdonnanceDto(Ordonnance ordonnance) {
        this.medicamentsPrescrits = ordonnance.getMedicamentsPrescrits();
        this.datePrescription = ordonnance.getDatePrescription();
        this.uidMedecin = ordonnance.getMedecin().getLinkedMeta().getExternalId();
        this.infosMedecin = ordonnance.getMedecin().getFirstName().concat(" ".concat(ordonnance.getMedecin().getLastName()));
        this.infosPatient = ordonnance.getPatient().getNomPatient().concat(" ".concat(ordonnance.getPatient().getPrenomPatient()));
        this.uidPatient = ordonnance.getPatient().getLinkedMeta().getExternalId();
        this.linkedMeta = ordonnance.getLinkedMeta().getExternalId();
    }

    public String getMedicamentsPrescrits() {
        return medicamentsPrescrits;
    }

    public void setMedicamentsPrescrits(String medicamentsPrescrits) {
        this.medicamentsPrescrits = medicamentsPrescrits;
    }

    public LocalDate getDatePrescription() {
        return datePrescription;
    }

    public void setDatePrescription(LocalDate datePrescription) {
        this.datePrescription = datePrescription;
    }

    public String getUidMedecin() {
        return uidMedecin;
    }

    public void setUidMedecin(String uidMedecin) {
        this.uidMedecin = uidMedecin;
    }

    public String getInfosMedecin() {
        return infosMedecin;
    }

    public void setInfosMedecin(String infosMedecin) {
        this.infosMedecin = infosMedecin;
    }

    public String getInfosPatient() {
        return infosPatient;
    }

    public void setInfosPatient(String infosPatient) {
        this.infosPatient = infosPatient;
    }

    public String getUidPatient() {
        return uidPatient;
    }

    public void setUidPatient(String uidPatient) {
        this.uidPatient = uidPatient;
    }

    public String getLinkedMeta() {
        return linkedMeta;
    }

    public void setLinkedMeta(String linkedMeta) {
        this.linkedMeta = linkedMeta;
    }

    public static OrdonnanceDto parse(Ordonnance ordonnance) {
        return new OrdonnanceDto(ordonnance);
    }

    public static List<OrdonnanceDto> parseAll(List<Ordonnance> ordonnances) {
        return ordonnances.stream().map(OrdonnanceDto::parse).collect(Collectors.toList());
    }
}
