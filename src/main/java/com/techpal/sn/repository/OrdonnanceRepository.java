package com.techpal.sn.repository;

import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Ordonnance;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface OrdonnanceRepository extends JpaRepository<Ordonnance, Long> {

    List<Ordonnance> findByMedecinOrPatient(@Nullable User medecin, @Nullable Patient patient);

    List<Ordonnance> findAll();

    List<Ordonnance> findByDatePrescription(LocalDate datePrescription);

    Ordonnance findByLinkedMeta(Meta meta);
}
