package com.techpal.sn.controllers;

import com.techpal.sn.dto.LitDto;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.repository.LitRepository;
import com.techpal.sn.security.services.LitService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LitController {

    private final LitRepository litRepository;

    private final LitService litService;

    public LitController(LitRepository litRepository, LitService litService) {
        this.litRepository = litRepository;
        this.litService = litService;
    }

    @RequestMapping(value = "/lits", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_MEDECIN', 'ROLE_SECRETAIRE')")
    public List<LitDto> getLits(){
        return LitDto.parseAll(litRepository.findLitsByEtatIsFalse());
    }

    @RequestMapping(value = "/lits", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public LitDto createLits(@RequestBody LitDto litDto){

        if (litDto == null) {
            new MessageResponse("Un des parametre est null");
        }
        return LitDto.parse(litService.createLit(litDto));
    }
}
