package com.techpal.sn.serviceImpl;
import com.techpal.sn.dto.RendezVousDto;
import com.techpal.sn.models.Meta;
import com.techpal.sn.models.Patient;
import com.techpal.sn.models.RendezVous;
import com.techpal.sn.models.User;
import com.techpal.sn.repository.RendezVousRepository;
import com.techpal.sn.security.services.MetaService;
import com.techpal.sn.security.services.PatientService;
import com.techpal.sn.security.services.RendezVousService;
import com.techpal.sn.security.services.UserDetailsServiceInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RendezVousServiceImpl implements RendezVousService {

    private final RendezVousRepository rendezVousRepository;

    private final MetaService metaService;

    private final PatientService patientService;

    private final UserDetailsService userDetailsService;

    private final UserDetailsServiceInfo userDetailsServiceInfo;

    @Override
    public RendezVous createRendezVous(RendezVousDto rendezVousDto) {

        if (rendezVousDto.getUidMedecin() == null || rendezVousDto.getUidPatient() == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        User appUser = userDetailsServiceInfo.getUserByExternalId(rendezVousDto.getUidMedecin());

        Patient patient = patientService.getPatientByExternalId(rendezVousDto.getUidPatient());

       boolean hasConflict = verifyConflict(rendezVousDto.getDateRDV(), rendezVousDto.getHeureRendezVous(), appUser, patient);

        System.out.println("le hasconflict service "+hasConflict);
        if (hasConflict) {
            throw new RuntimeException("Il existe dejà un rendez-vous à cette date et à cette heure");
        }
        if (patient == null || appUser == null) {
            throw new IllegalStateException("un des parametres est null");
        }

        RendezVous rendezVous = new RendezVous();

        rendezVous.setDateRDV(rendezVousDto.getDateRDV());
        rendezVous.setUser(appUser);
        rendezVous.setPatient(patient);
        rendezVous.setLinkedMeta(metaService.createNew(RendezVous.class.getName()));
        rendezVous.setHeureRendezVous(rendezVousDto.getHeureRendezVous());
        rendezVous.setLinkedMeta(metaService.createNew(RendezVous.class.getName()));

        return rendezVousRepository.saveAndFlush(rendezVous);
    }

    @Override
    public RendezVous updateRendezVous(RendezVousDto rendezVousDto) {

        if (rendezVousDto == null || rendezVousDto.getUidRendezVous() == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        User appUser = userDetailsServiceInfo.getUserByExternalId(rendezVousDto.getUidMedecin());

        Patient patient = patientService.getPatientByExternalId(rendezVousDto.getUidPatient());

        RendezVous rendezVous = getRendezVousByExternalId(rendezVousDto.getUidRendezVous());
        rendezVous.setDateRDV(rendezVousDto.getDateRDV());
        rendezVous.setUser(appUser);
        rendezVous.setPatient(patient);
        rendezVous.setHeureRendezVous(rendezVousDto.getHeureRendezVous());

        return rendezVousRepository.saveAndFlush(rendezVous);
    }

    @Override
    public RendezVous getRendezVousByExternalId(String uidPatient) {
        if (uidPatient == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Meta meta = metaService.findByExternalId(uidPatient);

        if (meta != null) {
            return rendezVousRepository.findByLinkedMeta(meta);
        } else {
            return new RendezVous();
        }

    }

    @Override
    public Page<RendezVous> getRendezVousForMedecin(String uidMedecin, Pageable pageable) {
        if (uidMedecin == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        User appUser = userDetailsServiceInfo.getUserByExternalId(uidMedecin);

        if (appUser == null) {
            throw new IllegalStateException("aucune donnee");
        }
        return rendezVousRepository.findByUser(appUser, pageable);
    }

    @Override
    public Page<RendezVous> getRendezVousForPatient(String uidPatient, Pageable pageable) {
        if (uidPatient == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Patient patient = patientService.getPatientByExternalId(uidPatient);

        if (patient != null) {
            throw new IllegalStateException("aucuns donnee");
        }
        return rendezVousRepository.findByPatient(patient, pageable);
    }

    @Override
    public Page<RendezVous> getRendezVousForPatientAndMedcin(String uidPatient, String uidMedecin, Pageable pageable) {
        if (uidPatient == null || uidMedecin == null) {
            throw new IllegalStateException("Un des parametres est null");
        }

        Patient patient = patientService.getPatientByExternalId(uidPatient);

        User medecin = userDetailsServiceInfo.getUserByExternalId(uidMedecin);

        if (patient == null || medecin == null) {
            throw new IllegalStateException("aucune donnee");
        }

        return rendezVousRepository.findByUserAndPatient(medecin, patient, pageable);
    }

    @Override
    public void deleteRendezVous(String uidRv) {
        if (uidRv == null) {
            throw new IllegalStateException("Un des parametres est null");
        }
        RendezVous rendezVous = getRendezVousByExternalId(uidRv);

        if (rendezVous == null) {
            throw new IllegalStateException("Le rendez-vous est null");
        }

        rendezVousRepository.delete(rendezVous);
    }

    //TODO: Cette fonction prend en parametre quatre varaibles : dateRendezVous, heureRendezVous, patient
    /* Verifie si le rendez vous du patient n'empiete pas sur un autre rendez-vous, ou si le medecin n'a pas
    un autre rendez-vous à la mm heure ou si le patient lui mm n'a pas un rendez-vous avec un autre medecin
     */

    @Override
    public boolean verifyConflict(LocalDate dateRendezVous, String heureRendezVous, User user, Patient patient) {

            //AtomicBoolean hasConflict = new AtomicBoolean(false);
            boolean hasConflict = false;

            if (dateRendezVous == null || heureRendezVous == null || user == null || patient == null) {
                throw new RuntimeException("Un des parametres est null");
            }

            List<RendezVous> rendezVousList =  rendezVousRepository.findAllByUser(user);

            for (RendezVous rendezVousUser: rendezVousList ) {
                for (RendezVous rendezVousPatient : patient.getRendezVous()) {
                    if (rendezVousPatient.getDateRDV().isEqual(dateRendezVous) && rendezVousPatient.getHeureRendezVous().equalsIgnoreCase(heureRendezVous)) {
                        //if (rendezVousUser.getUser().getRoles().equals("ROLE_MEDECIN")) {
                            if (rendezVousPatient.getDateRDV().isEqual(rendezVousUser.getDateRDV()) &&
                                    rendezVousUser.getDateRDV().isEqual(dateRendezVous) && rendezVousUser.getHeureRendezVous().equalsIgnoreCase(heureRendezVous)) {
                                hasConflict = true;
                          //  }
                        }

                    }
                }
            }

            /*rendezVousList.forEach(rendezVousUser ->{
                patient.getRendezVous().forEach(rendezVousPatient -> {
                    if (rendezVousPatient.getDateRDV().isEqual(dateRendezVous) || rendezVousPatient.getHeureRendezVous().equalsIgnoreCase(heureRendezVous)) {
                        if (rendezVousUser.getUser().getRoles().equals("ROLE_MEDECIN")) {
                            if (rendezVousPatient.getDateRDV().isEqual(rendezVousUser.getDateRDV()) ||
                                    rendezVousUser.getDateRDV().isEqual(dateRendezVous) || rendezVousUser.getHeureRendezVous().equalsIgnoreCase(heureRendezVous)) {
                                hasConflict = true;
                            }
                        }

                    }
                });
            });*/

            //System.out.println("le return "+hasConflict);

            return hasConflict;

    }
}
