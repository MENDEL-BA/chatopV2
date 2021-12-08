package com.techpal.sn.controllers;


import com.techpal.sn.dto.ConsultationDto;
import com.techpal.sn.models.User;
import com.techpal.sn.repository.ConsultationRepository;
import com.techpal.sn.repository.UserRepository;
import com.techpal.sn.security.services.ConsultationService;
import com.techpal.sn.security.services.UserDetailsServiceInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceInfo userDetailsServiceInfo;

    //@PostMapping
    @RequestMapping(value = "/consultations", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_MEDECIN')")
    public ConsultationDto createConsultations(@RequestBody ConsultationDto consultationDto) {
        if (consultationDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return ConsultationDto.parse(consultationService.createConsultation(consultationDto));
    }

    //@PostMapping
    @RequestMapping(value = "/updateConsultations", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_MEDECIN')")
    public ConsultationDto updateConsultations(@RequestBody ConsultationDto consultationDto) {
        if (consultationDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return ConsultationDto.parse(consultationService.updateConsultation(consultationDto));
    }

    //@GetMapping
    @RequestMapping(value = "/consultations", method = RequestMethod.GET)
    public List<ConsultationDto> getConsultations(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);

        return ConsultationDto.parseAll(consultationRepository.findAll(paging).stream().collect(Collectors.toList()));
    }

    //@GetMapping
    @RequestMapping(value = "/getConsultationByPatient/{uidPatient}", method = RequestMethod.GET)
    public List<ConsultationDto> getConsultationByPatient(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size,
                                                          @PathVariable String uidPatient) {

        Pageable paging = PageRequest.of(page, size);

        return ConsultationDto.parseAll(consultationService.getConsultationByPatient(uidPatient, paging).stream().collect(Collectors.toList()));
    }

    @GetMapping("/getConsultationByMedecin")
    @PreAuthorize("hasRole('ROLE_MEDECIN')")
    public List<ConsultationDto> getConsultationByMedecin(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);

        //Get current User, si j'ai le temps je ferais une fonction pour
        Optional<User> user = userDetailsServiceInfo.getUser();

        //Get current User, si j'ai le temps je ferais une fonction pour

        return ConsultationDto.parseAll(consultationService.getConsultationByMedecin(user.get().getLinkedMeta().getExternalId(), paging).stream().collect(Collectors.toList()));
    }


    //@GetMapping
    @RequestMapping(value = "/getConsultationByPatientAndMedecin/{uidPatient}/{uidMedecin}", method = RequestMethod.GET)
    public List<ConsultationDto> getConsultationByPatientAndMedecin(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "3") int size,
                                                                    @PathVariable String uidPatient,
                                                                    @PathVariable String uidMedecin) {

        Pageable paging = PageRequest.of(page, size);

        return ConsultationDto.parseAll(consultationService.getConsultationByPatientAndMedecin(uidPatient, uidMedecin, paging).stream().collect(Collectors.toList()));
    }

    //@GetMapping
    @RequestMapping(value = "/deleteConsultation/{uidConsultation}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_MEDECIN')")
    public void deleteConsultation(@PathVariable String uidConsultation) {

        if (uidConsultation == null) {
            throw new IllegalStateException("Un des parametres est null");
        }
        consultationService.deleteConsultation(uidConsultation);
    }

}