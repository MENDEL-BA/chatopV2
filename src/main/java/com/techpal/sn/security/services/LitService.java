package com.techpal.sn.security.services;

import com.techpal.sn.dto.LitDto;
import com.techpal.sn.models.Lit;
import com.techpal.sn.models.Meta;

public interface LitService {

    Lit getLitByLinkedMeta(Meta meta);

    Lit createLit(LitDto litDto);

    Lit updateLit(LitDto litDto);
}
