package com.techpal.sn.security.services;


import com.techpal.sn.dto.MetaDto;
import com.techpal.sn.models.Meta;

public interface MetaService {

        Meta createNew(String resourceType);

        MetaDto parse(Meta meta);

        Meta findByExternalId(String linkedMeta);

        MetaDto save(Meta meta);

}
