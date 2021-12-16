package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.LitDto;
import com.techpal.sn.models.Lit;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.repository.LitRepository;
import com.techpal.sn.security.services.LitService;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.PatientService;
import org.springframework.stereotype.Service;

@Service
public class LitServiceImpl implements LitService {

    private final MetaService metaService;

    private final LitRepository litRepository;

    private final PatientService patientService;

    public LitServiceImpl(MetaService metaService, LitRepository litRepository, PatientService patientService) {
        this.metaService = metaService;
        this.litRepository = litRepository;
        this.patientService = patientService;
    }

    @Override
    public Lit getLitByLinkedMeta(Meta meta) {

        if (meta == null) {
            new MessageResponse("Veulliez verifier un des paramatres");
        }

        return litRepository.findByLinkedMeta(meta);
    }

    @Override
    public Lit createLit(LitDto litDto) {
        if (litDto == null) {
            new MessageResponse("Un des parametres est null");
        }

        Lit lit = new Lit();

        lit.setEtat(false);
        lit.setNumero(litDto.getNumero());
        lit.setLinkedMeta(metaService.createNew(Lit.class.getName()));

        return litRepository.saveAndFlush(lit);
    }

    @Override
    public Lit updateLit(LitDto litDto) {

        if (litDto.getUidLit() == null) {
            new MessageResponse("Veuillez renseigner toutes les informations");
        }

        Lit lit = getLitByLinkedMeta(metaService.findByExternalId(litDto.getUidLit()));

        if (lit == null) {
            new MessageResponse("Le lit n'existe pas");
        }

        if (!litDto.getNumero().equalsIgnoreCase(lit.getNumero())) {
             lit.setNumero(litDto.getNumero());
        }
        if (litDto.getUidPatient() != null) {
            Patient patient = patientService.getPatientByExternalId(litDto.getUidPatient());
            if (patient == null) {
                new MessageResponse("Le patient est introuvable");
            }
            if (lit.getPatient() != null || lit.getPatient().equals(lit.getPatient())) {
                lit.setPatient(patient);
            }
        }

        lit.setEtat(litDto.isEtat());

        return litRepository.saveAndFlush(lit);
    }
}
