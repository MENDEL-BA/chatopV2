package com.techpal.sn.dto;

 import com.techpal.sn.models.Consultation;
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
public class ConsultationDto implements Serializable {

    private LocalDate dateConsultation;

    private String diagnosticConsultation;

    private String typeConsultation;

    private String prixConsultation;

    private String uidPatient;

    private String uidMedecin;

    private String uidConsultation;

    private String infosMedecin;

    private String infosPatient;

    public ConsultationDto(Consultation consultation) {
        this.dateConsultation = consultation.getDateConsultation();
        this.diagnosticConsultation = consultation.getDiagnosticConsultation();
        this.typeConsultation = consultation.getTypeConsultation();
        this.prixConsultation = consultation.getPrixConsultation();
        this.uidPatient = consultation.getPatient().getLinkedMeta().getExternalId();
        this.uidMedecin = consultation.getUser().getLinkedMeta().getExternalId();
        this.uidConsultation = consultation.getLinkedMeta().getExternalId();

        this.infosPatient = consultation.getPatient().getNomPatient().concat(" ".concat(
                consultation.getPatient().getPrenomPatient()
        ));
    }

    public static ConsultationDto parse(Consultation consultation) {
        return new ConsultationDto(consultation);
    }

    public static List<ConsultationDto> parseAll(List<Consultation> consultations) {
        return consultations.stream().map(ConsultationDto::parse).collect(Collectors.toList());
    }


}
