package com.techpal.sn.security.services;


import com.techpal.sn.dto.PatientDto;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.User;

import java.util.List;

public interface PatientService {

    Patient createPatient(PatientDto patientDto);

    Patient updatePatient(PatientDto patientDto);

    void deletePatient(String uidPatient);

    Patient getPatientByExternalId(String uidPatient);

    List<Patient> getPatientForMedecin(String uidUser);

}
