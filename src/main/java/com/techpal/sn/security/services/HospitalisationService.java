package com.techpal.sn.security.services;

import com.techpal.sn.dto.HospitalisationDto;
import com.techpal.sn.models.Hospitalisation;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.User;

import java.util.List;

public interface HospitalisationService {

    Hospitalisation createHospitalisation(HospitalisationDto hospitalisationDto);

    Hospitalisation updateHospitalisation(HospitalisationDto hospitalisationDto);

    List<Hospitalisation> getHospitalisationForPatient(String uidLit);


    List<Hospitalisation> getHospitalisationForMedecin(User user);

    Hospitalisation removeHospitalisation(HospitalisationDto hospitalisationDto);

    Hospitalisation getByLinkedMeta(Meta meta);
}
