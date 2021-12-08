package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.ConsultationDto;
import com.techpal.sn.models.Consultation;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.User;
import com.techpal.sn.repository.ConsultationRepository;
import com.techpal.sn.security.services.ConsultationService;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.PatientService;
import com.techpal.sn.security.services.UserDetailsServiceInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {


    private final ConsultationRepository consultationRepository;

    private final MetaService metaService;

    private final UserDetailsService myUserDetailsService;

    private final UserDetailsServiceInfo userDetailsServiceInfo;

    private final PatientService patientService;

    @Override
    public Consultation createConsultation(ConsultationDto consultationDto) {

        if (consultationDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Consultation consultation = new Consultation();

        Patient patient = patientService.getPatientByExternalId(consultationDto.getUidPatient());

        Optional<User> medecin = userDetailsServiceInfo.getUser();

        if (medecin == null || patient == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        consultation.setDateConsultation(LocalDate.now());
        consultation.setPrixConsultation(consultationDto.getPrixConsultation());
        consultation.setTypeConsultation(consultationDto.getTypeConsultation());
        consultation.setDiagnosticConsultation(consultationDto.getDiagnosticConsultation());
        consultation.setLinkedMeta(metaService.createNew(Consultation.class.getName()));
        consultation.setPatient(patient);
        consultation.setUser(medecin.get());

        return consultationRepository.saveAndFlush(consultation);
    }

    @Override
    public Consultation getConsultationByExternalId(String uidConsultation) {

        if (uidConsultation == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Meta meta = metaService.findByExternalId(uidConsultation);

        if (meta != null) {
            return consultationRepository.findByLinkedMeta(meta);
        } else {
            return new Consultation();
        }
    }

    @Override
    @Transactional
    public void deleteConsultation(String uidConsultation) {

        if (uidConsultation == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Meta meta = metaService.findByExternalId(uidConsultation);

        if (meta == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        consultationRepository.deleteByLinkedMeta(meta);
    }

    @Override
    public Page<Consultation> getConsultationByPatient(String uidPatient, Pageable pageable) {

        if (uidPatient == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Patient patient = patientService.getPatientByExternalId(uidPatient);

        if (patient == null) {
            throw new IllegalStateException("le patient n'existe pas");
        }

        return consultationRepository.findByPatient(patient, pageable);
    }

    @Override
    public Page<Consultation> getConsultationByMedecin(String uidMedecin, Pageable pageable) {

        if (uidMedecin == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        User medecin = userDetailsServiceInfo.getUserByExternalId(uidMedecin);

        if (medecin == null) {
            throw new IllegalStateException("Le medecin est introuvable");
        }

        return consultationRepository.findByUser(medecin, pageable);
    }

    @Override
    public Consultation updateConsultation(ConsultationDto consultationDto) {

        if (consultationDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Consultation consultation = getConsultationByExternalId(consultationDto.getUidConsultation());

        if (consultation == null) {
            throw new IllegalStateException("La consultation n'existe pas");
        }

        if (!consultation.getPatient().getLinkedMeta().getExternalId().equalsIgnoreCase(consultationDto.getUidPatient())) {
            Patient patient = patientService.getPatientByExternalId(consultationDto.getUidPatient());
            consultation.setPatient(patient);
        }

        consultation.setDateConsultation(consultationDto.getDateConsultation());
        consultation.setPrixConsultation(consultationDto.getPrixConsultation());
        consultation.setDiagnosticConsultation(consultationDto.getDiagnosticConsultation());
        consultation.setTypeConsultation(consultationDto.getTypeConsultation());

        return consultationRepository.saveAndFlush(consultation);
    }

    @Override
    public Page<Consultation> getConsultationByPatientAndMedecin(String uidPatient, String uidMedecin, Pageable pageable) {

        if (uidPatient == null || uidMedecin == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Patient patient = patientService.getPatientByExternalId(uidPatient);

        User medecin = userDetailsServiceInfo.getUserByExternalId(uidMedecin);

        if (medecin == null || patient == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return consultationRepository.findByPatientAndUser(patient, medecin, pageable);
    }
}
