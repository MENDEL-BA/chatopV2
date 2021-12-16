package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.HospitalisationDto;
import com.techpal.sn.dto.LitDto;
import com.techpal.sn.models.Hospitalisation;
import com.techpal.sn.models.Lit;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.repository.HospitalisationRepository;
import com.techpal.sn.repository.LitRepository;
import com.techpal.sn.security.services.HospitalisationService;
import com.techpal.sn.security.services.LitService;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalisationServiceImpl implements HospitalisationService {

    private final HospitalisationRepository hospitalisationRepository;

    private final LitRepository litRepository;

    private final MetaService metaService;

    private final LitService litService;

    private final PatientService patientService;

    public HospitalisationServiceImpl(HospitalisationRepository hospitalisationRepository, LitRepository litRepository, MetaService metaService, LitService litService, PatientService patientService) {
        this.hospitalisationRepository = hospitalisationRepository;
        this.litRepository = litRepository;
        this.metaService = metaService;
        this.litService = litService;
        this.patientService = patientService;
    }


    @Override
    public Hospitalisation createHospitalisation(HospitalisationDto hospitalisationDto) {

        if (hospitalisationDto == null || hospitalisationDto.getUidLit() == null) {
            new MessageResponse("Veuillez verifier les donnees");
        }


        LitDto litDto = new LitDto();

        if (hospitalisationDto.getUidPatient() == null) {
            new MessageResponse("Veuillez choisir le patient");
        }

        Patient patient = patientService.getPatientByExternalId(hospitalisationDto.getUidPatient());

        if (patient == null) {
            new MessageResponse("Le patient est introuvable");
        }

        Lit lit = litService.getLitByLinkedMeta(metaService.findByExternalId(hospitalisationDto.getMotifSortie()));

        if (lit == null) {
            new MessageResponse("Le lit n'esst pas repertorie, veuillez verifie");
        }

        Hospitalisation hospitalisation = new Hospitalisation();

        hospitalisation.setDateAdmission(hospitalisationDto.getDateAdmission());
        hospitalisation.setDateTransfert(hospitalisationDto.getDateTransfert());
        hospitalisation.setInfosAccompagnat(hospitalisation.getInfosAccompagnat());
        hospitalisation.setLinkedMeta(metaService.createNew(Hospitalisation.class.getName()));
        hospitalisation.setMotifAdmission(hospitalisationDto.getMotifAdmission());
        hospitalisation.setLits(lit);
        litDto.setNumero(lit.getNumero());
        litDto.setEtat(true);
        litDto.setUidPatient(patient.getLinkedMeta().getExternalId());

        litService.updateLit(litDto);


        return hospitalisationRepository.saveAndFlush(hospitalisation);
    }

    @Override
    public Hospitalisation updateHospitalisation(HospitalisationDto hospitalisationDto) {
        return null;
    }

    @Override
    public List<Hospitalisation> getHospitalisationForPatient(String uidLit) {

        if (uidLit == null) {
            new MessageResponse("Un des parametres est null");
        }

        List<Hospitalisation> hospitalisationsForPatient = new ArrayList<>();

        Lit lit = litService.getLitByLinkedMeta(metaService.findByExternalId(uidLit));

        List<Lit> litsPatient = litRepository.findByPatient(lit.getPatient());

        litsPatient.forEach(System.out::println);

        if (lit == null) {
            new MessageResponse("le lit ne peut etre recupere, veuillez verifier votre requete");
        }

        for (Lit litPatient: litsPatient) {
             hospitalisationsForPatient = hospitalisationRepository.findAllByLits(litPatient);
        }

        return hospitalisationsForPatient;
    }

    @Override
    public Hospitalisation removeHospitalisation(HospitalisationDto hospitalisationDto) {

        if (hospitalisationDto.getUidLit() == null  || hospitalisationDto.getUidHospitalisation() == null) {
            new MessageResponse("Un des parametres est null");
        }

        Hospitalisation hospitalisation = getByLinkedMeta(metaService.findByExternalId(hospitalisationDto.getUidHospitalisation()));

        if (hospitalisation == null) {
            new MessageResponse("Cette hospitaisation n'a pas été trouvée");
        }
        Lit lit = litService.getLitByLinkedMeta(metaService.findByExternalId(hospitalisationDto.getUidLit()));

        if (lit == null) {
            new MessageResponse("Cette hospitalisation n'a ete rataché àn aucun lit");
        }

        lit.setEtat(false);
        litRepository.saveAndFlush(lit); // I remove the cascade annotation in domain

        if (hospitalisationDto.getCauseDeces() != null ) {
            hospitalisation.setCauseDeces(hospitalisationDto.getCauseDeces());
            hospitalisation.setDateDeces(hospitalisationDto.getDateDeces());
        }

        hospitalisation.setMotifSortie(hospitalisationDto.getMotifSortie());
        hospitalisation.setDateSortie(LocalDate.now());

        return hospitalisationRepository.saveAndFlush(hospitalisation);
    }

    @Override
    public Hospitalisation getByLinkedMeta(Meta meta) {

        if (meta == null) {
            new MessageResponse("Un des parametres est null");
        }

        return hospitalisationRepository.findByLinkedMeta(meta);
    }
}
