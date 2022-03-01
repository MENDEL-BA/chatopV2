package com.techpal.sn.serviceImpl;


import com.techpal.sn.dto.FactureDto;
import com.techpal.sn.models.Facture;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.repository.FactureRepository;
import com.techpal.sn.security.services.FactureService;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class FactureServiceImpl implements FactureService {

    private final FactureRepository factureRepository;

    private final PatientService patientService;

    private final MetaService metaService;

    @Override
    public Facture createFacture(FactureDto factureDto) {
        if (factureDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Facture facture = new Facture();

        Patient patient = patientService.getPatientByExternalId(factureDto.getUidPatient());

        if (patient == null){
            throw new IllegalStateException("Le patient est introuvable");
        }

        facture.setDateFacturation(LocalDate.now());
        facture.setMontantFacture(factureDto.getMontantFacture());
        facture.setLinkedMeta(metaService.createNew(Facture.class.getName()));
        facture.setDatePaiement(factureDto.getDatePaiement());
        if (factureDto.getEtatFacture().equalsIgnoreCase("Oui")) {
            facture.setEstReglee(true);
        } else {
            facture.setEstReglee(false);
        }

        facture.setPatient(patient);

        return factureRepository.saveAndFlush(facture);
    }

    @Override
    public Facture getFactureByExternalId(String uidFacture) {

        if (uidFacture == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Meta meta = metaService.findByExternalId(uidFacture);

        if (meta == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return factureRepository.findByLinkedMeta(meta);
    }

    @Override
    public void deleteFacture(String uidFacture) {

        if (uidFacture == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Meta meta = metaService.findByExternalId(uidFacture);

        if (meta == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        factureRepository.deleteByLinkedMeta(meta);
    }

    @Override
    public List<Facture> getFactureByPatient(String uidPatient) {

        if (uidPatient == null ) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Patient patient = patientService.getPatientByExternalId(uidPatient);

        if (patient == null ) {
            throw new IllegalStateException("aucune donnee");
        }

        return factureRepository.findByPatient(patient);
    }

    @Override
    public Facture updateFacture(FactureDto factureDto) {
        if (factureDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Facture facture = getFactureByExternalId(factureDto.getUidFacture());

        if (facture == null) {
            throw new IllegalStateException("La facture n'existe pas");
        }

        if (!facture.getPatient().getLinkedMeta().getExternalId().equalsIgnoreCase(factureDto.getUidPatient())) {
            Patient patient = patientService.getPatientByExternalId(factureDto.getUidPatient());
            facture.setPatient(patient);
        }

        facture.setDateFacturation(LocalDate.now());
        facture.setMontantFacture(facture.getMontantFacture());
        facture.setDatePaiement(factureDto.getDatePaiement());
        facture.setEstReglee(factureDto.getEstReglee());

        return factureRepository.saveAndFlush(facture);
    }

}
