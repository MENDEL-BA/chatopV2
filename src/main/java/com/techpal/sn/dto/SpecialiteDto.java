package com.techpal.sn.dto;

import com.techpal.sn.models.SpecialiteMedecin;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SpecialiteDto implements Serializable {

    private String nomSpecialite;

    private String linkedMeta;

    public SpecialiteDto(SpecialiteMedecin specialiteMedecin) {
        this.nomSpecialite = specialiteMedecin.getNomSpecialite();
        this.linkedMeta = specialiteMedecin.getLinkedMeta().getExternalId();
    }

    public String getNomSpecialite() {
        return nomSpecialite;
    }

    public void setNomSpecialite(String nomSpecialite) {
        this.nomSpecialite = nomSpecialite;
    }

    public String getLinkedMeta() {
        return linkedMeta;
    }

    public void setLinkedMeta(String linkedMeta) {
        this.linkedMeta = linkedMeta;
    }

    public static SpecialiteDto parse(SpecialiteMedecin specialiteMedecin) {
        return new SpecialiteDto(specialiteMedecin);
    }

    public static List<SpecialiteDto> parseAll(List<SpecialiteMedecin> specialiteMedecins) {
        return specialiteMedecins.stream().map(SpecialiteDto::parse).collect(Collectors.toList());
    }
}
