package com.techpal.sn.controllers;


import com.techpal.sn.dto.RentalDTO;
import com.techpal.sn.dto.RentalResponse;
import com.techpal.sn.dto.RentalsResponse;
import com.techpal.sn.models.Rentals;
import com.techpal.sn.services.RentalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

//(origins = "*", allowedHeaders = "*")

@RestController
@RequestMapping("api/rentals")
@Api(description = "Rentals management APIs")
public class RentalsController {

    private final RentalService rentalService;
    private final ModelMapper modelMapper;

    public RentalsController(RentalService rentalService, ModelMapper modelMapper) {
        this.rentalService = rentalService;
        this.modelMapper = modelMapper;
    }

    
    @PostMapping
    @ApiOperation(
            value = "Api pour la creation d'un Objet Rental" ,authorizations = { @Authorization(value="jwtToken") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<?> createRental(@RequestParam("name") String name,
                                          @RequestParam("surface") Double surface,
                                          @RequestParam("price") Double price,
                                          @RequestParam("description") String description,
                                          @RequestPart("picture") MultipartFile picture) throws IOException {
        Rentals savedRental = rentalService.addRentals(name,
                surface,
                price,
                description,
                picture);
        return ResponseEntity.status(HttpStatus.OK).body(savedRental);

    }

    
    @GetMapping
    @ApiOperation(
            value = "Api pour recuperer les rentals",authorizations = { @Authorization(value="jwtToken") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<RentalsResponse> getAllRentals() {
        List<Rentals> rentals = rentalService.getAllRentals();
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(rental -> {
                    RentalDTO rentalDTO = modelMapper.map(rental, RentalDTO.class);
                    rentalDTO.setOwner_id(rental.getOwner().getId());
                    return rentalDTO;
                })
                .collect(Collectors.toList());

        RentalsResponse rentalsResponse = new RentalsResponse();
        rentalsResponse.setRentals(rentalDTOs);

        return ResponseEntity.ok(rentalsResponse);

    }

    @PutMapping("/{id}")
    @ApiOperation(
            value = "Api pour la modification d'un rental",authorizations = { @Authorization(value="jwtToken") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<RentalResponse> updateRental(@PathVariable long id, @RequestParam("name") String name,
                                                       @RequestParam("surface") Double surface,
                                                       @RequestParam("price") Double price,
                                                       @RequestParam("description") String description) {

        Rentals existingRental = rentalService.updateRental(id,name,
                surface,
                price,
                description);
        RentalResponse rentalResponse = new RentalResponse();
        rentalResponse.setMessage(HttpStatus.OK.toString());
        return ResponseEntity.status(HttpStatus.OK).body(rentalResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation(
            value = "Api pour recuperer un rental par son ID",authorizations = { @Authorization(value="jwtToken") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<RentalDTO> detailRentals(@PathVariable String id) {
        long idR = Long.parseLong(id);
        RentalDTO existingRental = rentalService.detailRentals(idR);

        if (existingRental == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(existingRental);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(
            value = "Api pour supprimmer un rental par son ID",authorizations = { @Authorization(value="jwtToken") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<?> deleteRental(@PathVariable Long id) {
        Rentals existingRental = rentalService.getRentalsById(id);
        
        if (existingRental == null) {
            return ResponseEntity.notFound().build();
        }
        
        rentalService.deleteRental(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
