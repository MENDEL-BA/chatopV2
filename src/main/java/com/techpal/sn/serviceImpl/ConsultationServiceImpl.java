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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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
    public List<Consultation> getConsultationByPatient(String uidPatient) {

        if (uidPatient == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Patient patient = patientService.getPatientByExternalId(uidPatient);

        if (patient == null) {
            throw new IllegalStateException("le patient n'existe pas");
        }

        return consultationRepository.findByPatient(patient);
    }

    @Override
    public List<Consultation> getConsultationByMedecin(String uidMedecin) {

        if (uidMedecin == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        User medecin = userDetailsServiceInfo.getUserByExternalId(uidMedecin);

        if (medecin == null) {
            throw new IllegalStateException("Le medecin est introuvable");
        }

        return consultationRepository.findByUser(medecin);
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
    public List<Consultation> getConsultationByPatientAndMedecin(String uidPatient, String uidMedecin) {

        if (uidPatient == null || uidMedecin == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Patient patient = patientService.getPatientByExternalId(uidPatient);

        User medecin = userDetailsServiceInfo.getUserByExternalId(uidMedecin);

        if (medecin == null || patient == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return consultationRepository.findByPatientAndUser(patient, medecin);
    }
}
