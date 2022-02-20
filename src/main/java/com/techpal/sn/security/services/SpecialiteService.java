package com.techpal.sn.security.services;

import com.techpal.sn.dto.SpecialiteDto;
import com.techpal.sn.models.SpecialiteMedecin;

public interface SpecialiteService {

    SpecialiteMedecin addOrUpdateSpecialite(SpecialiteDto specialiteDto);

    SpecialiteMedecin updateSpecialite(SpecialiteDto specialiteDto);

    SpecialiteMedecin getByExternalId(String externalId);


}
