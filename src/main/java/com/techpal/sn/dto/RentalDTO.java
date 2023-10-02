package com.techpal.sn.dto;

import com.techpal.sn.models.Rentals;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class RentalDTO implements Serializable {
    private Long id;
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;
    private int owner_id;
    private Date created_at;
    private Date updated_at;

    // Constructeur par défaut
    public RentalDTO() {
    }
    // Constructeur prenant une liste de Rental en paramètre
    public RentalDTO(List<Rentals> rentals) {
        // Initialiser une liste de RentalDTO
        List<RentalDTO> rentalDTOList = new ArrayList<>();

        // Parcourir la liste de Rental et créer des RentalDTO correspondants
        for (Rentals rental : rentals) {
            RentalDTO rentalDTO = new RentalDTO();
            rentalDTO.setId(rental.getId());
            rentalDTO.setName(rental.getName());
            rentalDTO.setSurface(rental.getSurface());
            rentalDTO.setPrice(rental.getPrice());
            rentalDTO.setPicture(rental.getPicture());
            rentalDTO.setDescription(rental.getDescription());
            rentalDTO.setOwner_id(rental.getOwner().getId());
            rentalDTO.setCreated_at(rental.getCreatedAt());
            rentalDTO.setUpdated_at(rental.getUpdatedAt());

            rentalDTOList.add(rentalDTO);
        }

    }
}
