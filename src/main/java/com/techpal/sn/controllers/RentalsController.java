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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin
@RestController
@RequestMapping("api/rentals")
@Api(description = "Rentals management APIs")
public class RentalsController {

    private final RentalService rentalService;
    @Autowired
    public RentalsController(RentalService rentalService) {
        this.rentalService = rentalService;

    }

    @CrossOrigin
    @PostMapping
    @ApiOperation(
            value = "Api pour la creation d'un Objet Rental")
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

    @CrossOrigin
    @GetMapping
    @ApiOperation(
            value = "Api pour recuperer les rentals")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully "),
            @ApiResponse(code = 401, message = "Access denied, token manquant"),
            @ApiResponse(code = 403, message = "Recuperation failed")
    })
    public ResponseEntity<RentalsResponse> getAllRentals() {
        List<RentalDTO> rentals = rentalService.getAllRentals();
        RentalsResponse rentalsResponse = new RentalsResponse();
        rentalsResponse.setRentals(rentals);
        return ResponseEntity.ok(rentalsResponse);
    }

    @PutMapping("/{id}")
    @ApiOperation(
            value = "Api pour la modification d'un rental")
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
            value = "Api pour recuperer un rental par son ID")
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
            value = "Api pour supprimmer un rental par son ID")
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
    @RequestMapping(value = "/",method = RequestMethod.OPTIONS)
    public void handleOptionsRequest() {
    }

}
