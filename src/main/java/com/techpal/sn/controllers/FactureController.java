package com.techpal.sn.controllers;

import com.techpal.sn.dto.FactureDto;
import com.techpal.sn.repository.FactureRepository;
import com.techpal.sn.security.services.FactureService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class FactureController {

    private final FactureService factureService;

    private final FactureRepository factureRepository;

    @PostMapping
    @RequestMapping(value = "/factures", method = RequestMethod.POST)
    public FactureDto createRendezVous(@RequestBody FactureDto factureDto) {
        if (factureDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return FactureDto.parse(factureService.createFacture(factureDto));
    }

    @PostMapping
    @RequestMapping(value = "/updateFactures", method = RequestMethod.POST)
    public FactureDto updateFacture(@RequestBody FactureDto factureDto) {

        if (factureDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return FactureDto.parse(factureService.updateFacture(factureDto));
    }

    @PostMapping
    @RequestMapping(value = "/deleteFactures", method = RequestMethod.POST)
    public void deleteFactures(@RequestParam String uidFacture) {

        if (uidFacture == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        factureService.deleteFacture(uidFacture);
    }

    @GetMapping
    @RequestMapping(value = "/factures", method = RequestMethod.GET)
    public List<FactureDto> getAllFactures(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);

        return FactureDto.parseAll(factureRepository.findAll(paging).stream().collect(Collectors.toList()));
    }

    @GetMapping
    @RequestMapping(value = "/getFacturesByUid", method = RequestMethod.GET)
    public FactureDto getFacturesByUid(@RequestParam String uidFacture) {

        if (uidFacture == null) {
            throw new IllegalArgumentException("Un des parametres est null");
        }

        return FactureDto.parse(factureService.getFactureByExternalId(uidFacture));
    }
}
