package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.MetaDto;
import com.techpal.sn.models.Meta;
import com.techpal.sn.repository.MetaRepository;
import com.techpal.sn.security.jwt.IdentifierGenerator;
import com.techpal.sn.security.services.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class MetaServiceImpl implements MetaService {


    private final MetaRepository metaRepository;

    private final IdentifierGenerator identifierGenerator;


    @Autowired
    public MetaServiceImpl(MetaRepository metaRepository, IdentifierGenerator identifierGenerator) {
        this.metaRepository = metaRepository;
        this.identifierGenerator = identifierGenerator;
    }

    @Override
    public Meta createNew(String resourceType) {
        Meta meta = new Meta();
        meta.setExternalId(identifierGenerator.generate(4, 8, true));
        meta.setCreatedAt(new Date());
        meta.setResourceType(resourceType);
        //meta.setAuthor(2); //TODO: TEMPORARY, must be replaced by real logged in user account
        Meta savedMeta = metaRepository.save(meta);
        if (savedMeta == null) throw new IllegalStateException();
        return savedMeta;
    }


    @Override
    public MetaDto parse(Meta meta) {
        return new MetaDto(meta);
    }

    @Override
    public Meta findByExternalId(String linkedMeta) {
        return this.metaRepository.findByExternalId(linkedMeta);
    }


    @Override
    public MetaDto save(Meta meta) {
        return new MetaDto(this.metaRepository.save(meta));
    }
}
