package com.techpal.sn.controllers;

import com.techpal.sn.dto.RendezVousDto;
import com.techpal.sn.models.User;
import com.techpal.sn.repository.RendezVousRepository;
import com.techpal.sn.security.services.RendezVousService;
import com.techpal.sn.security.services.UserDetailsServiceInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RendezVousController {

    private final RendezVousService rendezVousService;

    private final RendezVousRepository rendezVousRepository;

    private final UserDetailsServiceInfo userDetailsServiceInfo;

    @RequestMapping(value = "/rendezVous", method = RequestMethod.POST)
    public RendezVousDto createRendezVous(@RequestBody RendezVousDto rendezVousDto) {
        if (rendezVousDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }
        return RendezVousDto.parse(rendezVousService.createRendezVous(rendezVousDto));
    }

    @RequestMapping(value = "/rendezVous", method = RequestMethod.PUT)
    public RendezVousDto updateRendezVous(@RequestBody RendezVousDto rendezVousDto) {
        if (rendezVousDto == null) {
            throw new IllegalStateException("Un des parametres est null");
        }
        return RendezVousDto.parse(rendezVousService.updateRendezVous(rendezVousDto));
    }

    //@GetMapping("/rendezVous")
    public List<RendezVousDto> getAllRendezVous(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size) {

        Pageable paging = PageRequest.of(page, size);
        return RendezVousDto.parseAll(rendezVousRepository.findAll(paging).stream().collect(Collectors.toList()));
    }

    @GetMapping("/rendezVous")
    public List<RendezVousDto> allRendezVous(){
        return RendezVousDto.parseAll(rendezVousRepository.findAll());
    }

    @GetMapping("/rendezVousForMedecin")
    public List<RendezVousDto> getRendezVousForMedecin(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);
        Optional<User> user = userDetailsServiceInfo.getUser();
        return RendezVousDto.parseAll(rendezVousService.getRendezVousForMedecin(user.get().getLinkedMeta().getExternalId(),paging).stream().collect(Collectors.toList()));
    }

    @GetMapping("/rendezVousForPatient/{uidPatient}")
    public List<RendezVousDto> getRendezVousForPatient(@RequestParam String uidPatient,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);
        return RendezVousDto.parseAll(rendezVousService.getRendezVousForPatient(uidPatient, paging).stream().collect(Collectors.toList()));
    }

    @GetMapping("/rendezVousForPatientAndMedecin/{uidPatient}/{uidMedecin}")
    public List<RendezVousDto> getRendezVousForPatientAndMedcin(@RequestParam String uidPatient,
                                                                @RequestParam String uidMedecin,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size) {

        Pageable paging = PageRequest.of(page, size);
        return RendezVousDto.parseAll(rendezVousService.getRendezVousForPatientAndMedcin(uidPatient, uidMedecin, paging).stream().collect(Collectors.toList()));
    }

    @GetMapping("/getRendezVousByUid/{uidRendezVous}")
    public RendezVousDto getRendezVousByExternalId(@RequestParam String uidRendezVous) {

        if (uidRendezVous == null) {
            throw new IllegalStateException("Un des parametres est null");
        }
        return RendezVousDto.parse(rendezVousService.getRendezVousByExternalId(uidRendezVous));
    }

    @DeleteMapping("/deleteRV/{uidRv}")
    public void deletePatient(@PathVariable("uidRv")  String uidRv) {
        rendezVousService.deleteRendezVous(uidRv);
    }
}
