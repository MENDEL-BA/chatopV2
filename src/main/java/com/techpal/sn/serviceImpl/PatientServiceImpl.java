package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.PatientDto;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.repository.PatientRepository;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.PatientService;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final MetaService metaservice;

    public PatientServiceImpl(PatientRepository patientRepository, MetaService metaservice) {
        this.patientRepository = patientRepository;
        this.metaservice = metaservice;
    }

    @Override
    public Patient createPatient(PatientDto patientDto) {

        if (patientDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Patient patient = new Patient();

        patient.setNomPatient(patientDto.getNomPatient());
        patient.setPrenomPatient(patientDto.getPrenomPatient());
        patient.setAssuranceMedicale(patientDto.getAssuranceMedicale());
        patient.setNumeroTelephonePatient(patientDto.getNumeroTelephonePatient());
        patient.setEmailPatient(patientDto.getEmailPatient());
        patient.setAssuranceMedicale(patientDto.getAssuranceMedicale());
        patient.setCodeAssurance(patientDto.getCodeAssurance());
        patient.setNomAndPrenomICE(patientDto.getNomAndPrenomICE());
        patient.setNumeroTelephoneICE(patientDto.getNumeroTelephoneICE());
        patient.setLinkedMeta(metaservice.createNew(Patient.class.getName()));
        patient.setSituationMatrimoniale(patientDto.getSituationMatrimoniale());

        return patientRepository.saveAndFlush(patient);
    }

    @Override
    public Patient updatePatient(PatientDto patientDto) {
        System.out.println("Im in update");
        if (patientDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Patient patient = new Patient();

        if (patientDto.getExternalid() != null) {
            patient = getPatientByExternalId(patientDto.getExternalid());
        } else {
            patient = patientRepository.findByNumeroTelephonePatient(patient.getNumeroTelephonePatient());
        }

        if(patient == null){
            throw new IllegalStateException("Le patient n'existe plus");
        }

        patient.setNomPatient(patientDto.getNomPatient());
        patient.setPrenomPatient(patientDto.getPrenomPatient());
        patient.setAssuranceMedicale(patientDto.getAssuranceMedicale());
        patient.setNumeroTelephonePatient(patientDto.getNumeroTelephonePatient());
        patient.setEmailPatient(patientDto.getEmailPatient());
        patient.setAssuranceMedicale(patientDto.getAssuranceMedicale());
        patient.setCodeAssurance(patientDto.getCodeAssurance());
        patient.setNomAndPrenomICE(patientDto.getNomAndPrenomICE());
        patient.setNumeroTelephoneICE(patientDto.getNumeroTelephoneICE());
        patient.setSituationMatrimoniale(patientDto.getSituationMatrimoniale());

        return patientRepository.saveAndFlush(patient);
    }

    @Override
    public void deletePatient(String uidPatient) {

        if (uidPatient == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Patient patient = getPatientByExternalId(uidPatient);

        patientRepository.delete(patient);
    }

    @Override
    public Patient getPatientByExternalId(String uidPatient) {

        if (uidPatient == null) {
            throw new IllegalStateException("Le uid du patient est null");
        }

        Meta meta = metaservice.findByExternalId(uidPatient);

        if (meta == null) {
            throw new IllegalStateException("Le meta est null");
        }

        return patientRepository.findByLinkedMeta(meta);
    }


}
