package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.RentalDTO;
import com.techpal.sn.models.Rentals;
import com.techpal.sn.repository.RentalRepository;
import com.techpal.sn.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalsServImpl implements RentalService {

    private final RentalRepository rentalsRepository;

    @Autowired
    public RentalsServImpl(RentalRepository rentalsRepository) {
        this.rentalsRepository = rentalsRepository;
    }

    @Override
    public Rentals addRentals(Rentals rentals) {
         return rentalsRepository.save(rentals);
    }

    @Override
    public Rentals getRentalsById(Long id) {
          return rentalsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Rental not found"));
    }


    @Override
    public List<RentalDTO> getAllRentals() {
         return rentalsRepository.findAllAsDTO();
    }

    @Override
    public void deleteRental(Long id) {
        rentalsRepository.deleteById(id);
    }

}
