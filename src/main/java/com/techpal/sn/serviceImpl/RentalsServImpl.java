package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.RentalDTO;
import com.techpal.sn.models.Rentals;
import com.techpal.sn.repository.RentalRepository;
import com.techpal.sn.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class RentalsServImpl implements RentalService {

    private final String imageUploadPath = "/Users/mendel/Documents/imagesRental";

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
