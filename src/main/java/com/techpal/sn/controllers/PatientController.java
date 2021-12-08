package com.techpal.sn.controllers;


import com.techpal.sn.dto.PatientDto;
import com.techpal.sn.repository.PatientRepository;
import com.techpal.sn.security.services.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientController {

    private final PatientService patientService;

    private final PatientRepository patientRepository;

    @PostMapping("/patient")
    public PatientDto createNewPatient(@RequestBody PatientDto patientDto) {
        return PatientDto.parse(patientService.createPatient(patientDto));
    }

    @PutMapping("/patient")
    public PatientDto updatePatient(@RequestBody PatientDto patientDto) {
        return PatientDto.parse(patientService.updatePatient(patientDto));
    }

    @DeleteMapping("/deletePatient/{uidPatient}")
    public void deletePatient(@PathVariable("uidPatient")  String uidPatient) {
        patientService.deletePatient(uidPatient);
    }

    @GetMapping("/patients")
    public List<PatientDto> getAllPatient(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);
        return PatientDto.parseAll(patientRepository.findAll(paging).stream().collect(Collectors.toList()));
    }

    @GetMapping("/getPatientByUid")
    public PatientDto getAPatientByUid(@RequestParam String uidPatient) {

        return PatientDto.parse(patientService.getPatientByExternalId(uidPatient));
    }
}
