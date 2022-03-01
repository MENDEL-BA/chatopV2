package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.DossierMedicalDto;
import com.techpal.sn.models.DossierMedical;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.User;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.repository.DossierMedicalRepository;
import com.techpal.sn.security.services.DossierMedicalService;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.PatientService;
import com.techpal.sn.security.services.UserDetailsServiceInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DossierMedicalServiceImpl implements DossierMedicalService {

    private final DossierMedicalRepository dossierMedicalRepository;

    private final MetaService metaService;

    private final UserDetailsServiceInfo userDetailsServiceInfo;

    private final PatientService patientService;

    public DossierMedicalServiceImpl(DossierMedicalRepository dossierMedicalRepository, MetaService metaService,
                                     UserDetailsServiceInfo userDetailsServiceInfo, PatientService patientService) {
        this.dossierMedicalRepository = dossierMedicalRepository;
        this.metaService = metaService;
        this.userDetailsServiceInfo = userDetailsServiceInfo;
        this.patientService = patientService;
    }

    @Override
    public DossierMedical saveDossierMedical(DossierMedicalDto dossierMedicalDto) {

        if (dossierMedicalDto == null) {
            new MessageResponse("Un des parametres est null");
        }
        User user = null;
        Patient patient = null;

        if(dossierMedicalDto.getUidMedecin() != null && dossierMedicalDto.getUidPatient() != null) {
            user = userDetailsServiceInfo.getUserByExternalId(dossierMedicalDto.getUidMedecin());
            patient = patientService.getPatientByExternalId(dossierMedicalDto.getUidPatient());
        } else {
            new MessageResponse("Un des parametres est null");
        }


        DossierMedical dossierMedical = new DossierMedical();

        dossierMedical.setDate_Dos(LocalDate.now());
        dossierMedical.setLinkedMeta(metaService.createNew(DossierMedical.class.getName()));
        dossierMedical.setPatient(patient);
        dossierMedical.setMedecin(user);
        return dossierMedicalRepository.saveAndFlush(dossierMedical);
    }

    @Override
    public DossierMedical deleteDossierMedical(DossierMedicalDto dossierMedicalDto) {
        return null;
    }

    @Override
    public DossierMedical getByExternalId(String externalId) {

        if (externalId == null) {
            new MessageResponse("Un des parametres est null");
        }

        Meta meta = metaService.findByExternalId(externalId);

        if (meta != null) {
            return dossierMedicalRepository.findDossierMedicalsByLinkedMeta(meta);
        }
        return new DossierMedical();
    }
}
