package com.techpal.sn.security.services;


import com.techpal.sn.dto.ConsultationDto;
import com.techpal.sn.models.Consultation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultationService {

    Consultation createConsultation(ConsultationDto ConsultationDto);

    Consultation getConsultationByExternalId(String uidConsultation);

    void deleteConsultation(String uidConsultation);

    Page<Consultation> getConsultationByPatient(String uidPatient, Pageable pageable);

    Page<Consultation> getConsultationByMedecin(String uidMedecin, Pageable pageable);

    Consultation updateConsultation(ConsultationDto consultationDto);

    Page<Consultation> getConsultationByPatientAndMedecin(String uidPatient, String uidMedecin, Pageable pageable);

}
