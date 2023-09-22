package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.RentalDTO;
import com.techpal.sn.models.Rentals;
import com.techpal.sn.models.UserEntity;
import com.techpal.sn.repository.RentalRepository;
import com.techpal.sn.repository.UserRepository;
import com.techpal.sn.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalsServImpl implements RentalService {

    @Value("${file.path.rental}")
    private String imageUploadPath;

    private final RentalRepository rentalsRepository;

    private final UserRepository userRepository;

    @Autowired
    public RentalsServImpl(RentalRepository rentalsRepository, UserRepository userRepository) {
        this.rentalsRepository = rentalsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Rentals addRentals(String name,
                              Double surface,
                              Double price,
                              String description,
                              MultipartFile picture) throws IOException {
        Rentals rental = new Rentals();
        rental.setDescription(description);
        rental.setName(name);
        String imageUrl = saveImage(picture);
        rental.setPicture(imageUrl);
        rental.setSurface(BigDecimal.valueOf(surface));
        rental.setPrice(BigDecimal.valueOf(price));
        rental.setSurface(BigDecimal.valueOf(surface));
        rental.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Optional<UserEntity> userEntity = userRepository.findByEmail(userDetails.getUsername());

        rental.setOwner(userEntity.get());
        return rentalsRepository.save(rental);
    }

    @Override
    public Rentals updateRental(long id,String name, Double surface, Double price, String description) {
        Rentals existingRental = getRentalsById(id);

        if (existingRental == null) {
            ResponseEntity.notFound().build();
        }

        existingRental.setName(name);
        existingRental.setUpdatedAt(Timestamp.from(Instant.now()));
        existingRental.setDescription(description);
        existingRental.setPrice(BigDecimal.valueOf(price));
        existingRental.setSurface(BigDecimal.valueOf(surface));

        return rentalsRepository.save(existingRental);
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

    @Override
    public RentalDTO detailRentals(long id) {
        Rentals existingRental = getRentalsById(id);

        if (existingRental == null) {
            ResponseEntity.notFound().build();
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
        return rentalDTO;
    }

    @Override
    public String saveImage(MultipartFile imageFile) throws IOException {
        // Générez un nom de fichier unique en utilisant UUID
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        // Assurez-vous que le répertoire de téléchargement existe, sinon créez-le
        File uploadDirectory = new File(imageUploadPath);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        // Créez le chemin complet du fichier
        Path imagePath = Path.of(imageUploadPath, fileName);

        // Copiez le contenu du fichier téléchargé vers le répertoire de stockage
        Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        // Retournez l'URL de l'image stockée
        return imageUploadPath + fileName; // Vous pouvez ajuster l'URL selon votre structure de serveur
    }

}
