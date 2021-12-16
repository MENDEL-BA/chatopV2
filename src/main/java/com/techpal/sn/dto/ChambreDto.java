package com.techpal.sn.dto;

import com.techpal.sn.models.Chambre;
import com.techpal.sn.models.Chambre;

import java.util.List;
import java.util.stream.Collectors;

public class ChambreDto {

    private String localisationChambre;

    private int capaciteChambre;

    private String prixChambre;

    private String typeChambre;

    private String uidLit;

    private String uidChambre;

    public ChambreDto(Chambre chambre) {
        this.localisationChambre = chambre.getLocalisationChambre();
        this.capaciteChambre = chambre.getCapaciteChambre();
        this.prixChambre = chambre.getPrixChambre();
        this.typeChambre = chambre.getTypeChambre();
        this.uidLit = chambre.getLits().getLinkedMeta().getExternalId();
        this.uidChambre = chambre.getLinkedMeta().getExternalId();
    }

    public String getLocalisationChambre() {
        return localisationChambre;
    }

    public void setLocalisationChambre(String localisationChambre) {
        this.localisationChambre = localisationChambre;
    }

    public int getCapaciteChambre() {
        return capaciteChambre;
    }

    public void setCapaciteChambre(int capaciteChambre) {
        this.capaciteChambre = capaciteChambre;
    }

    public String getPrixChambre() {
        return prixChambre;
    }

    public void setPrixChambre(String prixChambre) {
        this.prixChambre = prixChambre;
    }

    public String getTypeChambre() {
        return typeChambre;
    }

    public void setTypeChambre(String typeChambre) {
        this.typeChambre = typeChambre;
    }

    public String getUidLit() {
        return uidLit;
    }

    public void setUidLit(String uidLit) {
        this.uidLit = uidLit;
    }

    public String getUidChambre() {
        return uidChambre;
    }

    public void setUidChambre(String uidChambre) {
        this.uidChambre = uidChambre;
    }

    public static ChambreDto parse(Chambre Chambre) {
        return new ChambreDto(Chambre);
    }

    public static List<ChambreDto> parseAll(List<Chambre> Chambres) {
        return Chambres.stream().map(ChambreDto::parse).collect(Collectors.toList());
    }
}
