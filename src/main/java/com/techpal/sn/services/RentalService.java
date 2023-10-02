package com.techpal.sn.services;

import com.techpal.sn.dto.RentalDTO;
import com.techpal.sn.models.Rentals;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public interface RentalService {
    
     Rentals addRentals(String name,
                        Double surface,
                        Double price,
                        String description,
                        MultipartFile picture) throws IOException;
     Rentals updateRental(long id,String name,
                                 Double surface,
                                 Double price,
                                 String description);
     List<Rentals> getAllRentals();

     Rentals getRentalsById(Long id);

     void deleteRental(Long id);

     RentalDTO detailRentals(long id);

     String saveImage(MultipartFile imageFile) throws IOException;
}
