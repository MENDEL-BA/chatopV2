package com.techpal.sn.services;

import com.techpal.sn.dto.RentalDTO;
import com.techpal.sn.models.Rentals;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public interface RentalService {
    
     Rentals addRentals(Rentals rentals);

     List<RentalDTO> getAllRentals();

     Rentals getRentalsById(Long id);

     void deleteRental(Long id);

     String saveImage(MultipartFile imageFile) throws IOException;
}
