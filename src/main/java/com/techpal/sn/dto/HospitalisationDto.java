package com.techpal.sn.dto;

import com.techpal.sn.models.Hospitalisation;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class HospitalisationDto implements Serializable {

    private LocalDate dateAdmission;

    private String motifAdmission;

    private LocalDate dateSortie;

    private String motifSortie;

    private String dateTransfert;

    private String dossierMedical;

    private String infosAccompagnant;

    private String dateDeces;

    private String causeDeces;

    private String uidHospitalisation;

    private String uidLit;

    private String uidPatient;

    private String infosPatient;

    private String numeroChambre;

    private String numeroLit;

    public HospitalisationDto() {
    }

    public HospitalisationDto(Hospitalisation hospitalisation) {
        this.dateAdmission = hospitalisation.getDateAdmission();
        this.motifAdmission = hospitalisation.getMotifAdmission();
        this.dateSortie = hospitalisation.getDateSortie();
        this.motifSortie = hospitalisation.getMotifSortie();
        this.dateTransfert = hospitalisation.getDateTransfert();
        this.dossierMedical = hospitalisation.getDossierMedical(); // Le dossier medical sera un module complet apres la premiere version
        this.infosAccompagnant = hospitalisation.getInfosAccompagnat();
        this.dateDeces = hospitalisation.getDateDeces();
        this.causeDeces = hospitalisation.getCauseDeces();
        this.uidHospitalisation = hospitalisation.getLinkedMeta().getExternalId();
        this.uidLit = hospitalisation.getLits().getLinkedMeta().getExternalId();
        this.infosPatient = hospitalisation.getLits().getPatient().getNomPatient().concat(" ".concat(hospitalisation.getLits().getPatient().getPrenomPatient()));

        //TODO: Mauvaise pratique, à refaire apres presentaion
        this.numeroLit = hospitalisation.getLits().getNumero();
        //this.numeroChambre = hospitalisation.getLits().getPatient().getc;
    }

    public String getNumeroChambre() {
        return numeroChambre;
    }

    public void setNumeroChambre(String numeroChambre) {
        this.numeroChambre = numeroChambre;
    }

    public String getNumeroLit() {
        return numeroLit;
    }

    public void setNumeroLit(String numeroLit) {
        this.numeroLit = numeroLit;
    }

    public LocalDate getDateAdmission() {
        return dateAdmission;
    }

    public void setDateAdmission(LocalDate dateAdmission) {
        this.dateAdmission = dateAdmission;
    }

    public String getMotifAdmission() {
        return motifAdmission;
    }

    public void setMotifAdmission(String motifAdmission) {
        this.motifAdmission = motifAdmission;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getMotifSortie() {
        return motifSortie;
    }

    public void setMotifSortie(String motifSortie) {
        this.motifSortie = motifSortie;
    }

    public String getDateTransfert() {
        return dateTransfert;
    }

    public void setDateTransfert(String dateTransfert) {
        this.dateTransfert = dateTransfert;
    }

    public String getDossierMedical() {
        return dossierMedical;
    }

    public void setDossierMedical(String dossierMedical) {
        this.dossierMedical = dossierMedical;
    }

    public String getInfosAccompagnant() {
        return infosAccompagnant;
    }

    public void setInfosAccompagnant(String infosAccompagnant) {
        this.infosAccompagnant = infosAccompagnant;
    }

    public String getDateDeces() {
        return dateDeces;
    }

    public void setDateDeces(String dateDeces) {
        this.dateDeces = dateDeces;
    }

    public String getCauseDeces() {
        return causeDeces;
    }

    public void setCauseDeces(String causeDeces) {
        this.causeDeces = causeDeces;
    }

    public String getUidHospitalisation() {
        return uidHospitalisation;
    }

    public void setUidHospitalisation(String uidHospitalisation) {
        this.uidHospitalisation = uidHospitalisation;
    }

    public String getUidLit() {
        return uidLit;
    }

    public void setUidLit(String uidLit) {
        this.uidLit = uidLit;
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

    public static HospitalisationDto parse(Hospitalisation hospitalisation){
        return new HospitalisationDto(hospitalisation);
    }

    public static List<HospitalisationDto> parseAll(List<Hospitalisation> hospitalisations){
        return hospitalisations.stream().map(HospitalisationDto::parse).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "HospitalisationDto{" +
                "motifAdmission='" + motifAdmission + '\'' +
                ", infosAccompagnant='" + infosAccompagnant + '\'' +
                ", uidLit='" + uidLit + '\'' +
                ", uidPatient='" + uidPatient + '\'' +
                ", infosPatient='" + infosPatient + '\'' +
                ", numeroLit='" + numeroLit + '\'' +
                '}';
    }
}