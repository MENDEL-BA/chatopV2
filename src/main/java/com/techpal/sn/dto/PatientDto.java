package com.techpal.sn.dto;

 import com.techpal.sn.models.Patient;
 import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PatientDto implements Serializable {

    private String nomPatient;

    private String prenomPatient;

    private String emailPatient;

    private String numeroTelephonePatient;

    private String situationMatrimoniale;

    private String assuranceMedicale;

    private String codeAssurance;

    private String nomAndPrenomICE;

    private String numeroTelephoneICE;

    private String externalid;

    public PatientDto() {
    }

    public PatientDto(Patient patient){
        this.nomPatient = patient.getNomPatient();
        this.prenomPatient = patient.getPrenomPatient();
        this.emailPatient = patient.getEmailPatient();
        this.numeroTelephonePatient = patient.getNumeroTelephonePatient();
        this.situationMatrimoniale = patient.getSituationMatrimoniale();
        this.assuranceMedicale = patient.getAssuranceMedicale();
        this.codeAssurance = patient.getCodeAssurance();
        this.nomAndPrenomICE = patient.getNomAndPrenomICE();
        this.numeroTelephoneICE = patient.getNumeroTelephoneICE();
        this.externalid = patient.getLinkedMeta().getExternalId();
    }


    public static PatientDto parse(Patient patient) {
        return new PatientDto(patient);
    }

    public static List<PatientDto> parseAll(List<Patient> Patients) {
        return Patients.stream().map(PatientDto::parse).collect(Collectors.toList());
    }

}
