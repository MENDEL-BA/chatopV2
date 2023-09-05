package com.techpal.sn.services;

import com.techpal.sn.dto.RentalDTO;
import com.techpal.sn.models.Rentals;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RentalService {
    
     Rentals addRentals(Rentals rentals);

     List<RentalDTO> getAllRentals();

     Rentals getRentalsById(Long id);

     void deleteRental(Long id);
}
