package com.techpal.sn.serviceImpl;

import com.techpal.sn.dto.SpecialiteDto;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.SpecialiteMedecin;
import com.techpal.sn.payload.response.MessageResponse;
import com.techpal.sn.repository.SpecialiteRepository;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.SpecialiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialiteServiceImpl implements SpecialiteService {

    @Autowired
    SpecialiteRepository specialiteRepository;

    @Autowired
    MetaService metaService;


    @Override
    public SpecialiteMedecin addOrUpdateSpecialite(SpecialiteDto specialiteDto) {

        if (specialiteDto == null) {
            new MessageResponse("Un des parametres est null");
        }

        if (specialiteDto.getLinkedMeta() != null) {
            updateSpecialite(specialiteDto);
        }


        SpecialiteMedecin specialiteMedecin = new SpecialiteMedecin();

        specialiteMedecin.setNomSpecialite(specialiteDto.getNomSpecialite().trim());
        specialiteMedecin.setLinkedMeta(metaService.createNew(SpecialiteMedecin.class.getName()));

        return specialiteRepository.saveAndFlush(specialiteMedecin);
    }

    @Override
    public SpecialiteMedecin updateSpecialite(SpecialiteDto specialiteDto) {

        if (specialiteDto.getLinkedMeta() != null) {
            SpecialiteMedecin specialiteMedecin = getByExternalId(specialiteDto.getLinkedMeta());
            if (specialiteMedecin == null) {
                new MessageResponse("Un des parametres est null");
            }

            specialiteMedecin.setNomSpecialite(specialiteDto.getNomSpecialite().trim());

            return specialiteRepository.saveAndFlush(specialiteMedecin);
        } else {
            return new SpecialiteMedecin();
        }
    }

    @Override
    public SpecialiteMedecin getByExternalId(String externalId) {
        if (externalId == null) {
            new MessageResponse("Un des parametres est null");
        }

        Meta meta = metaService.findByExternalId(externalId);

        if (meta == null) {
            new MessageResponse("Un des parametres est null");
        }

        return specialiteRepository.findByLinkedMeta(meta);
    }

    @Override
    public SpecialiteMedecin getByNomSpecialite(String nomSpecialite) {

        if (nomSpecialite == null) {
            new MessageResponse("Un des parametres est null");
        }
        return specialiteRepository.findByNomSpecialite(nomSpecialite.trim());
    }
}
