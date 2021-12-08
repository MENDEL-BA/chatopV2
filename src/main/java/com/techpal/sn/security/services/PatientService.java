package com.techpal.sn.security.services;


import com.techpal.sn.dto.PatientDto;
import com.techpal.sn.models.Patient;

public interface PatientService {

    Patient createPatient(PatientDto patientDto);

    Patient updatePatient(PatientDto patientDto);

    void deletePatient(String uidPatient);

    Patient getPatientByExternalId(String uidPatient);

}
