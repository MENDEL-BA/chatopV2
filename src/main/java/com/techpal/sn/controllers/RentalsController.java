package com.techpal.sn.controllers;


import com.techpal.sn.dto.RentalDTO;
import com.techpal.sn.dto.RentalsResponse;
import com.techpal.sn.models.Rentals;
import com.techpal.sn.models.UserEntity;
import com.techpal.sn.repository.RentalRepository;
import com.techpal.sn.repository.UserRepository;
import com.techpal.sn.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                                          @RequestPart("picture") MultipartFile picture) {

        Rentals rental = new Rentals();
        rental.setDescription(description);
        rental.setName(name);
        rental.setPicture(picture.getOriginalFilename());
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
    public ResponseEntity<Rentals> updateRental(@PathVariable Long id, @RequestBody Rentals rental) {
        Rentals existingRental = rentalService.getRentalsById(id);
        
        if (existingRental == null) {
            return ResponseEntity.notFound().build();
        }
        
        existingRental.setName(rental.getName());
        existingRental.setUpdatedAt(Timestamp.from(Instant.now()));
        existingRental.setDescription(rental.getDescription());
        existingRental.setPrice(rental.getPrice());
        existingRental.setSurface(rental.getSurface());
        
        Rentals updatedRental = rentalRepository.save(existingRental);
        return ResponseEntity.ok(updatedRental);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rentals> detailRentals(@PathVariable String id) {
        long idR = Long.parseLong(id);
        Rentals existingRental = rentalService.getRentalsById(idR);

        if (existingRental == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(existingRental);
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
