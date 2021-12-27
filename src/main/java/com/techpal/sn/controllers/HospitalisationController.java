package com.techpal.sn.controllers;

import com.techpal.sn.dto.HospitalisationDto;
import com.techpal.sn.models.User;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.repository.HospitalisationRepository;
import com.techpal.sn.security.services.HospitalisationService;
import com.techpal.sn.security.services.UserDetailsServiceInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HospitalisationController {

    private final HospitalisationService hospitalisationService;

    private final HospitalisationRepository hospitalisationRepository;

    private final UserDetailsServiceInfo userDetailsServiceInfo;

    public HospitalisationController(HospitalisationService hospitalisationService, HospitalisationRepository hospitalisationRepository, UserDetailsServiceInfo userDetailsServiceInfo) {
        this.hospitalisationService = hospitalisationService;
        this.hospitalisationRepository = hospitalisationRepository;
        this.userDetailsServiceInfo = userDetailsServiceInfo;
    }

    @RequestMapping(value = "/hospitalisations", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_MEDECIN', 'ROLE_SECRETAIRE')")
    public HospitalisationDto createConsultations(@RequestBody HospitalisationDto hospitalisationDto) {
        if (hospitalisationDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return HospitalisationDto.parse(hospitalisationService.createHospitalisation(hospitalisationDto));
    }


    @RequestMapping(value = "/removeHospitalisation", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_MEDECIN', 'ROLE_SECRETAIRE')")
    public HospitalisationDto removeHospitalisation(@RequestBody HospitalisationDto hospitalisationDto) {
        if (hospitalisationDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return HospitalisationDto.parse(hospitalisationService.removeHospitalisation(hospitalisationDto));
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

    @RequestMapping(value = "/hospitalisationsForMedecin", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_MEDECIN')")
    public List<HospitalisationDto> getHospilationForMedecin() {

        Optional<User> medecin = userDetailsServiceInfo.getUser();
        if (medecin == null) {
            new MessageResponse("Le medecin est introuvable");
        }

        return HospitalisationDto.parseAll(hospitalisationService.getHospitalisationForMedecin(medecin.get()));
    }
}
