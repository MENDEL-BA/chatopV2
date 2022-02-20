package com.techpal.sn.controllers;

import com.techpal.sn.dto.FactureDto;
import com.techpal.sn.dto.SpecialiteDto;
import com.techpal.sn.repository.SpecialiteRepository;
import com.techpal.sn.security.services.SpecialiteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class SpecialiteController {

    @Autowired
    SpecialiteService specialiteService;

    @Autowired
    SpecialiteRepository specialiteRepository;

    //@PostMapping
    @RequestMapping(value = "/specialites", method = RequestMethod.POST)
    public SpecialiteDto createFactures(@Valid  @RequestBody SpecialiteDto specialiteDto) {
        if (specialiteDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        return SpecialiteDto.parse(specialiteService.addOrUpdateSpecialite(specialiteDto));
    }

    //@GetMapping
    @RequestMapping(value = "/allSpecialites", method = RequestMethod.GET)
    public List<SpecialiteDto> getAllSpecialite() {

        return SpecialiteDto.parseAll(specialiteRepository.findAll());
    }



}
