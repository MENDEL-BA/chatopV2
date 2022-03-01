package com.techpal.sn.security.services;


import com.techpal.sn.dto.ConsultationDto;
import com.techpal.sn.models.Consultation;

import java.util.List;

public interface ConsultationService {

    Consultation createConsultation(ConsultationDto ConsultationDto);

    Consultation getConsultationByExternalId(String uidConsultation);

    void deleteConsultation(String uidConsultation);

    List<Consultation> getConsultationByPatient(String uidPatient);

    List<Consultation> getConsultationByMedecin(String uidMedecin);

    Consultation updateConsultation(ConsultationDto consultationDto);

    List<Consultation> getConsultationByPatientAndMedecin(String uidPatient, String uidMedecin);

}
