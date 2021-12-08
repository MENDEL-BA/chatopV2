package com.techpal.sn.security.services;


import com.techpal.sn.dto.RendezVousDto;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.RendezVous;
import com.techpal.sn.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface RendezVousService {

    RendezVous createRendezVous(RendezVousDto rendezVousDto);

    RendezVous updateRendezVous(RendezVousDto rendezVousDto);

    RendezVous getRendezVousByExternalId(String uidPatient);

    Page<RendezVous> getRendezVousForMedecin(String uidMedecin, Pageable pageable);

    Page<RendezVous> getRendezVousForPatient(String uidPatient, Pageable pageable);

    Page<RendezVous> getRendezVousForPatientAndMedcin(String uidPatient, String uidMedecin, Pageable pageable);

    void deleteRendezVous(String uidRv);

    boolean verifyConflict(LocalDate dateRendezVous, String heureRendezVous, User user, Patient patient);
}
