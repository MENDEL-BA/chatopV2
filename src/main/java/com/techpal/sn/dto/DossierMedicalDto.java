package com.techpal.sn.dto;

import com.techpal.sn.models.DossierMedical;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DossierMedicalDto implements Serializable {

    private LocalDate date_dos;

    private String libelle_Dos;

    private String uidPatient;

    private String infosPatient;

    private String uidMedecin;

    private String infosMedecin;

    private String uidDossier;

    public DossierMedicalDto(DossierMedical dossierMedical) {
        this.date_dos = dossierMedical.getDate_Dos();
        this.libelle_Dos = dossierMedical.getLibelle_Dos();
        this.uidPatient = dossierMedical.getPatient().getLinkedMeta().getExternalId();
        this.infosPatient = dossierMedical.getPatient().getNomPatient().concat(" ".concat(dossierMedical.getPatient().getPrenomPatient()));
        this.uidMedecin = dossierMedical.getMedecin().getLinkedMeta().getExternalId();
        this.infosMedecin = dossierMedical.getMedecin().getFirstName().concat(" ".concat(dossierMedical.getMedecin().getLastName()));
        this.uidDossier = dossierMedical.getLinkedMeta().getExternalId();
    }


    public LocalDate getDate_dos() {
        return date_dos;
    }

    public void setDate_dos(LocalDate date_dos) {
        this.date_dos = date_dos;
    }

    public String getLibelle_Dos() {
        return libelle_Dos;
    }

    public void setLibelle_Dos(String libelle_Dos) {
        this.libelle_Dos = libelle_Dos;
    }

    public String getUidPatient() {
        return uidPatient;
    }

    public void setUidPatient(String uidPatient) {
        this.uidPatient = uidPatient;
    }

    public String getInfosPatient() {
        return infosPatient;
    }

    public void setInfosPatient(String infosPatient) {
        this.infosPatient = infosPatient;
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

    public String getUidDossier() {
        return uidDossier;
    }

    public void setUidDossier(String uidDossier) {
        this.uidDossier = uidDossier;
    }

    public static DossierMedicalDto parse(DossierMedical dossierMedical) {
        return new DossierMedicalDto(dossierMedical);
    }

    public static List<DossierMedicalDto> parseAll(List<DossierMedical> dossierMedicals) {
        return dossierMedicals.stream().map(DossierMedicalDto::parse).collect(Collectors.toList());
    }

}
