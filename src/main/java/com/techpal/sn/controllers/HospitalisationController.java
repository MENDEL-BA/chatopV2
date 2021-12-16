package com.techpal.sn.controllers;

import com.techpal.sn.dto.HospitalisationDto;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.repository.HospitalisationRepository;
import com.techpal.sn.security.services.HospitalisationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HospitalisationController {

    private final HospitalisationService hospitalisationService;

    private final HospitalisationRepository hospitalisationRepository;

    public HospitalisationController(HospitalisationService hospitalisationService, HospitalisationRepository hospitalisationRepository) {
        this.hospitalisationService = hospitalisationService;
        this.hospitalisationRepository = hospitalisationRepository;
    }

    @RequestMapping(value = "/hospitalisations", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_MEDECIN', 'ROLE_SECRETAIRE')")
    public HospitalisationDto createConsultations(@RequestBody HospitalisationDto hospitalisationDto) {
        if (hospitalisationDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return HospitalisationDto.parse(hospitalisationService.createHospitalisation(hospitalisationDto));
    }

    @RequestMapping(value = "/hospitalisations", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_MEDECIN', 'ROLE_SECRETAIRE')")
    public List<HospitalisationDto> getAllHospitalisations() {

        return HospitalisationDto.parseAll(hospitalisationRepository.findAll());
    }

    //@RequestMapping(value = "/hospitalisationsForPatient", method = RequestMethod.GET)
    @GetMapping("/hospitalisationsForPatient/{uidlit}")
    @PreAuthorize("hasAnyRole('ROLE_MEDECIN', 'ROLE_SECRETAIRE')")
    public List<HospitalisationDto> getHospitalisationByPatient(@PathVariable String uidlit) {

        if (uidlit == null) {
            new MessageResponse("un des parametres est null");
        }
        return HospitalisationDto.parseAll(hospitalisationService.getHospitalisationForPatient(uidlit));
    }
}
