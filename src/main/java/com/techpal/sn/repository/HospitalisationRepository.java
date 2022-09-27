package com.techpal.sn.repository;

import com.techpal.sn.dto.HospitalisationDto;
import com.techpal.sn.models.Hospitalisation;
import com.techpal.sn.models.Lit;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalisationRepository extends JpaRepository<Hospitalisation, Long> {

    List<Hospitalisation> findAll();

    List<Hospitalisation> findAllByLits(Lit lit);

    List<Hospitalisation> findAllByUser(User user);

    Hospitalisation findByLinkedMeta(Meta meta);
}