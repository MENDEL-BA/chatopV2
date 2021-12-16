package com.techpal.sn.dto;

import com.techpal.sn.models.Lit;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class LitDto implements Serializable {

    private String numero;

    private boolean etat;

    private String uidPatient;

    private String infosPatient;

    private String uidLit;

    public LitDto(Lit lit) {
        this.numero = lit.getNumero();
        this.etat = lit.isEtat();
        this.uidPatient = lit.getPatient().getLinkedMeta().getExternalId();
        this.infosPatient = lit.getPatient().getNomPatient().concat(" ".concat(lit.getPatient().getPrenomPatient())); // delete it after we finish
        this.uidLit = lit.getLinkedMeta().getExternalId();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
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

    public String getUidLit() {
        return uidLit;
    }

    public void setUidLit(String uidLit) {
        this.uidLit = uidLit;
    }

    public static LitDto parse(Lit lit) {
        return new LitDto(lit);
    }

    public static List<LitDto> parseAll(List<Lit> lits) {
        return lits.stream().map(LitDto::parse).collect(Collectors.toList());
    }
}
