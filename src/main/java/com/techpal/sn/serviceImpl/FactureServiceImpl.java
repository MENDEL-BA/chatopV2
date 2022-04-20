package com.techpal.sn.serviceImpl;


import com.techpal.sn.dto.FactureDto;
import com.techpal.sn.models.Facture;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.repository.FactureRepository;
import com.techpal.sn.security.services.FactureService;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.PatientService;
import lombok.AllArgsConstructor;
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

        System.out.println("la facture est le "+factureDto.toString());
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
        facture.setMontantFacture(factureDto.getMontantFacture());
        facture.setDatePaiement(factureDto.getDatePaiement());

        facture.setEstReglee(factureDto.getEtatFacture().equalsIgnoreCase("NON") ? true : false);

        return factureRepository.saveAndFlush(facture);
    }

    @Override
    public Integer getBeneficeOfRange(LocalDate dateEntree) {

        if (dateEntree == null) {
            new MessageResponse("Veuillez fournir deux dates valide");
        }

        int montantBenefice = 0;

        List<Facture> facturesAfterInputDate = factureRepository.findFactureByDateFacturationAfterAndEstRegleeIsTrue(dateEntree);

        if (facturesAfterInputDate.isEmpty()) {
            return 0;
        }

        for (Facture facture : facturesAfterInputDate) {
            montantBenefice += facture.getMontantFacture();

        }

        return montantBenefice;
    }

    @Override
    public List<Facture> getMontantFactureForPatientForNotReglee(String uidPatient) {

        if (uidPatient == null) {
            new MessageResponse("Un des parametres est null");
        }

        Patient patient = patientService.getPatientByExternalId(uidPatient);

        if (patient == null) {
            new MessageResponse("Le patient n'a pas été trouvé");
        }

        List<Facture> facturesForPatientAndIsRegleeIsFalse = factureRepository.findFactureByPatientAndEstRegleeIsFalse(patient);

        if (facturesForPatientAndIsRegleeIsFalse.isEmpty()) {
            new MessageResponse("Aucune facture n'a été trouvée pour le patient");
        }


        return facturesForPatientAndIsRegleeIsFalse;

    }

    @Override
    public Integer getbeneficeForToday() {

        int montantBeneficeForToday = 0;
        List<Facture> facturesToday = factureRepository.findFactureByDateFacturation(LocalDate.now());

        if (!facturesToday.isEmpty()) {
            for (Facture facture: facturesToday) {
                return montantBeneficeForToday += facture.getMontantFacture();
            }
        }

        return 0;

    }


}
