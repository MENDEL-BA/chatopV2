package com.techpal.sn.controllers;


import com.techpal.sn.dto.FactureDto;
import com.techpal.sn.dto.OrdonnanceDto;
import com.techpal.sn.security.services.OrdonnanceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class OrdonnanceController {

    private final OrdonnanceService ordonnanceService;

    @PostMapping
    @RequestMapping(value = "/ordonnances", method = RequestMethod.POST)
    public OrdonnanceDto createOrdonnance(@RequestBody OrdonnanceDto ordonnanceDto) {
        if (ordonnanceDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return OrdonnanceDto.parse(ordonnanceService.createOrdonnance(ordonnanceDto));
    }

    @PostMapping
    @RequestMapping(value = "/updateOrdonnances", method = RequestMethod.POST)
    public OrdonnanceDto updateFacture(@RequestBody OrdonnanceDto ordonnanceDto) {

        if (ordonnanceDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return OrdonnanceDto.parse(ordonnanceService.updateOrdonnance(ordonnanceDto));
    }

    @GetMapping
    @RequestMapping(value = "/ordonnanceByMedecinAndPatient", method = RequestMethod.GET)
    public List<OrdonnanceDto> getAllFactures(@RequestParam String uidMedecin,
                                              @RequestParam String uidPatient) {

        return OrdonnanceDto.parseAll(ordonnanceService.getOrdonnanceByMedecinAndPatient(uidMedecin, uidPatient).stream().collect(Collectors.toList()));
    }
}
