package com.techpal.sn.controllers;

import com.techpal.sn.dto.DossierMedicalDto;
import com.techpal.sn.dto.FactureDto;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.repository.DossierMedicalRepository;
import com.techpal.sn.security.services.DossierMedicalService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class DossierMedicalController {

    private final DossierMedicalService dossierMedicalService;

    private final DossierMedicalRepository dossierMedicalRepository;



    @RequestMapping(value = "/dossier_medicals", method = RequestMethod.POST)
    public DossierMedicalDto createDossierMedical(@RequestBody DossierMedicalDto dossierMedicalDto) {
        if (dossierMedicalDto == null) {
            new MessageResponse("Un des parametres est null");
        }

        return DossierMedicalDto.parse(dossierMedicalService.saveDossierMedical(dossierMedicalDto));
    }

    @RequestMapping(value = "/dossier_medicals", method = RequestMethod.PUT)
    public DossierMedicalDto updateDossierMedical(@RequestBody DossierMedicalDto dossierMedicalDto) {
        if (dossierMedicalDto == null) {
            new MessageResponse("Un des parametres est null");
        }
        // TODO : a implementer

        return null;
    }

    @RequestMapping(value = "/dossier_medical", method = RequestMethod.GET)
    public List<DossierMedicalDto> getAllFactures() {

        return DossierMedicalDto.parseAll(dossierMedicalRepository.findAll().stream().collect(Collectors.toList()));
    }
}
