package com.techpal.sn.controllers;


import com.techpal.sn.dto.RentalDTO;
import com.techpal.sn.dto.RentalsResponse;
import com.techpal.sn.models.Rentals;
import com.techpal.sn.models.UserEntity;
import com.techpal.sn.repository.RentalRepository;
import com.techpal.sn.repository.UserRepository;
import com.techpal.sn.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin
@RestController
@RequestMapping("api/rentals")
public class RentalsController {

    private static final String IMAGE_UPLOAD_DIR = "chemin/vers/dossier/images/";

    private final RentalService rentalService;

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;

    @Autowired
    public RentalsController(RentalService rentalService, RentalRepository rentalRepository,
                             UserRepository userRepository) {
        this.rentalService = rentalService;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> createRental(@RequestParam("name") String name,
                                          @RequestParam("surface") Double surface,
                                          @RequestParam("price") Double price,
                                          @RequestParam("description") String description,
                                          @RequestPart("picture") MultipartFile picture) throws IOException {

        Rentals rental = new Rentals();
        rental.setDescription(description);
        rental.setName(name);
        String imageUrl = rentalService.saveImage(picture);
        rental.setPicture(imageUrl);
        rental.setSurface(BigDecimal.valueOf(surface));
        rental.setPrice(BigDecimal.valueOf(price));
        rental.setSurface(BigDecimal.valueOf(surface));
        rental.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Optional<UserEntity> userEntity = userRepository.findByEmail(userDetails.getUsername());

        rental.setOwner(userEntity.get());
        Rentals savedRental = rentalService.addRentals(rental);
        return ResponseEntity.ok(savedRental);

    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> getAllRentals() {
        List<RentalDTO> rentals = rentalService.getAllRentals();
        RentalsResponse rentalsResponse = new RentalsResponse();
        rentalsResponse.setRentals(rentals);
        return ResponseEntity.ok(rentalsResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRental(@PathVariable long id, @RequestParam("name") String name,
                                               @RequestParam("surface") Double surface,
                                               @RequestParam("price") Double price,
                                               @RequestParam("description") String description) {

        Rentals existingRental = rentalService.getRentalsById(id);
        
        if (existingRental == null) {
            return ResponseEntity.notFound().build();
        }
        
        existingRental.setName(name);
        existingRental.setUpdatedAt(Timestamp.from(Instant.now()));
        existingRental.setDescription(description);
        existingRental.setPrice(BigDecimal.valueOf(price));
        existingRental.setSurface(BigDecimal.valueOf(surface));
        
        rentalRepository.save(existingRental);
        return ResponseEntity.ok(HttpStatus.OK.toString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> detailRentals(@PathVariable String id) {
        long idR = Long.parseLong(id);
        Rentals existingRental = rentalService.getRentalsById(idR);

        if (existingRental == null) {
            return ResponseEntity.notFound().build();
        }
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setCreated_at(existingRental.getCreatedAt());
        rentalDTO.setId(existingRental.getId());
        rentalDTO.setDescription(existingRental.getDescription());
        rentalDTO.setName(existingRental.getName());
        rentalDTO.setPicture(existingRental.getPicture());
        rentalDTO.setSurface(existingRental.getSurface());
        rentalDTO.setPrice(existingRental.getPrice());
        rentalDTO.setOwner_id(existingRental.getOwner().getId());
        rentalDTO.setUpdated_at(existingRental.getUpdatedAt());
        return ResponseEntity.ok(rentalDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRental(@PathVariable Long id) {
        Rentals existingRental = rentalService.getRentalsById(id);
        
        if (existingRental == null) {
            return ResponseEntity.notFound().build();
        }
        
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }
    @RequestMapping(value = "/",method = RequestMethod.OPTIONS)
    public void handleOptionsRequest() {
    }

}
