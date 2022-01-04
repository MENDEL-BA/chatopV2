package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.OrdonnanceDto;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Ordonnance;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.User;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.repository.OrdonnanceRepository;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.OrdonnanceService;
import com.techpal.sn.security.services.PatientService;
import com.techpal.sn.security.services.UserDetailsServiceInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional //Remove this at production, add profiles annotation early
public class OrdonnanceServiceImpl implements OrdonnanceService {

    private final OrdonnanceRepository ordonnanceRepository;

    private final MetaService metaService;

    private final UserDetailsServiceInfo userDetailsService;

    private final PatientService patientService;

    public OrdonnanceServiceImpl(OrdonnanceRepository ordonnanceRepository, MetaService metaService,
                                 UserDetailsServiceInfo userDetailsService, PatientService patientService) {
        this.ordonnanceRepository = ordonnanceRepository;
        this.metaService = metaService;
        this.userDetailsService = userDetailsService;
        this.patientService = patientService;
    }

    @Override
    public Ordonnance createOrdonnance(OrdonnanceDto ordonnanceDto) {

        if (Objects.isNull(ordonnanceDto)) {
            new MessageResponse("Un des parametres est null");
        }

        Ordonnance ordonnance = new Ordonnance();

        ordonnance.setMedicamentsPrescrits(ordonnanceDto.getMedicamentsPrescrits());
        ordonnance.setDatePrescription(LocalDate.now());
        ordonnance.setLinkedMeta(metaService.createNew(Ordonnance.class.getName()));

        if (ordonnanceDto.getUidMedecin() == null || ordonnanceDto.getUidPatient() == null) {
            new MessageResponse("Veuillez renseigner le medecin");
        }

        User medecin = userDetailsService.getUserByExternalId(ordonnanceDto.getUidMedecin());

        if (medecin == null) {
            new MessageResponse("Le medecin est introuvable");
        }

        Patient patient = patientService.getPatientByExternalId(ordonnanceDto.getUidPatient());

        if (patient == null) {
            new MessageResponse("Le patient est introuvable");
        }

        ordonnance.setMedecin(medecin);
        ordonnance.setPatient(patient);

        return ordonnanceRepository.saveAndFlush(ordonnance);
    }

    @Override
    public Ordonnance updateOrdonnance(OrdonnanceDto ordonnanceDto) {

        if (ordonnanceDto.getLinkedMeta() == null) {
            new MessageResponse("Un des parametres est null");
        }

        Ordonnance ordonnance = getOrdonnanceByExternalId(ordonnanceDto.getLinkedMeta());

        if (ordonnance == null) {
            new MessageResponse("Un des parametres est null");
        }

        if (ordonnance.getMedecin().getLinkedMeta().getExternalId() != ordonnanceDto.getUidMedecin()){
            User medecin = userDetailsService.getUserByExternalId(ordonnanceDto.getUidMedecin());
            ordonnance.setMedecin(medecin);
        }

        if (ordonnance.getPatient().getLinkedMeta().getExternalId() != ordonnanceDto.getUidPatient()) {
            Patient patient = patientService.getPatientByExternalId(ordonnanceDto.getUidPatient());
            ordonnance.setPatient(patient);
        }

        ordonnance.setDatePrescription(ordonnanceDto.getDatePrescription());

        return ordonnanceRepository.saveAndFlush(ordonnance);
    }

    @Override
    public Ordonnance getOrdonnanceByExternalId(String externalId) {

        if (externalId == null) {
            new MessageResponse("Un des parametres est null");
        }

        Meta meta = metaService.findByExternalId(externalId);

        if (meta == null) {
            new MessageResponse("L'objet semble ne plus exister");
        }

        return ordonnanceRepository.findByLinkedMeta(meta);
    }

    @Override
    public List<Ordonnance> getOrdonnanceByMedecinAndPatient(String externalIdMedecin, String externalIdPatient) {

        User medecin = null;
        Patient patient = null;
        if (externalIdMedecin != null && !StringUtils.isEmpty(externalIdMedecin)) {
             medecin = userDetailsService.getUserByExternalId(externalIdMedecin);
        }

        if ( externalIdPatient != null && !StringUtils.isEmpty(externalIdPatient)) {
             patient = patientService.getPatientByExternalId(externalIdPatient);
        }
        return ordonnanceRepository.findByMedecinOrPatient(medecin, patient);
    }
}
