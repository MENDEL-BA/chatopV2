package com.techpal.sn.repository;

import com.techpal.sn.models.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    Meta findByExternalId(String externalId);
}
