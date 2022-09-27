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
import org.springframework.web.client.RestClientException;

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
    public void deleteDossierMedical(DossierMedicalDto dossierMedicalDto) {
        if (dossierMedicalDto != null || dossierMedicalDto.getUidDossier() != null) {
            dossierMedicalRepository.deleteByLinkedMeta(dossierMedicalDto.getUidDossier());
        }else {
            new RestClientException("Le dossier medical n'a pas été trouvé");
        }
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

    @Override
    public DossierMedical getByPatient(String uidPatient) {
        if (uidPatient == null) {
            new RestClientException("Oupps Une erreur s'est produite");
        }

        DossierMedical dossierMedical = null;

        Patient patient = patientService.getPatientByExternalId(uidPatient);
        if (patient != null) {
            dossierMedical = dossierMedicalRepository.findByPatient(patient);
        }

        return new DossierMedical();
    }

    @Override
    public DossierMedical getByMedecin(String uidMedecin) {
        return null;
    }

    @Override
    public DossierMedical getByMedecinAndPatient(User medecin, Patient patient) {
        return null;
    }
}
