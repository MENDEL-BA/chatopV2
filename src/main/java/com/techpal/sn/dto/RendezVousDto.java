package com.techpal.sn.dto;

import com.techpal.sn.models.RendezVous;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RendezVousDto implements Serializable {

    private LocalDate dateRDV;

    private String uidPatient;

    private String uidMedecin;

    private String uidRendezVous;

    private String infosPatient;

    private String infosMedecin;

    private String heureRendezVous;

    public RendezVousDto() {
    }

    public RendezVousDto(RendezVous rendezVous) {
        this.dateRDV = rendezVous.getDateRDV();
        this.uidPatient = rendezVous.getPatient().getLinkedMeta().getExternalId();
        this.uidMedecin = rendezVous.getUser().getLinkedMeta().getExternalId();
        this.uidRendezVous = rendezVous.getLinkedMeta().getExternalId();
        this.infosMedecin = rendezVous.getUser().getFirstName().concat(" ".concat(rendezVous.getUser().getLastName()));
        this.infosPatient = rendezVous.getPatient().getNomPatient().concat(" ".concat(rendezVous.getPatient().getPrenomPatient()));
        this.heureRendezVous = rendezVous.getHeureRendezVous();
    }

    public static RendezVousDto parse(RendezVous rendezVous) {
        return new RendezVousDto(rendezVous);
    }

    public static List<RendezVousDto> parseAll(List<RendezVous> rendezVous) {
        return rendezVous.stream().map(RendezVousDto::parse).collect(Collectors.toList());
    }
}
