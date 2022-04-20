package com.techpal.sn;

import com.techpal.sn.models.Facture;
import com.techpal.sn.security.services.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SpringBootSecurityJwtApplication implements CommandLineRunner {
//public class SpringBootSecurityJwtApplication {

	/*private final MetaService metaService;
	private final SpecialiteService specialiteService;
	private final SpecialiteRepository specialiteRepository;

	private final PatientService patientService;

	private final LitRepository litRepository;

	private final ChambreRepository chambreRepository;

	private final HospitalisationRepository hospitalisationRepository;

	public SpringBootSecurityJwtApplication(MetaService metaService, SpecialiteService specialiteService, SpecialiteRepository specialiteRepository) {
		this.metaService = metaService;
		this.specialiteService = specialiteService;
		this.specialiteRepository = specialiteRepository;
	}*/

	@Autowired
	FactureService factureService;


	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {



		System.out.println("++++++++++++++++++++++++++++++");
		 factureService.getBeneficeOfRange(LocalDate.parse("2022-02-07"));
		/*SpecialiteMedecin specialiteMedecin = new SpecialiteMedecin();

		specialiteMedecin.setNomSpecialite("Generaliste");
		specialiteMedecin.setLinkedMeta(metaService.createNew(SpecialiteMedecin.class.getName()));
		specialiteRepository.saveAndFlush(specialiteMedecin);

		SpecialiteMedecin specialiteMedecin1 = new SpecialiteMedecin();

		specialiteMedecin1.setNomSpecialite("Cardiologue");
		specialiteMedecin1.setLinkedMeta(metaService.createNew(SpecialiteMedecin.class.getName()));
		specialiteRepository.saveAndFlush(specialiteMedecin1);

		SpecialiteMedecin specialiteMedecin2 = new SpecialiteMedecin();

		specialiteMedecin2.setNomSpecialite("Dentiste");
		specialiteMedecin2.setLinkedMeta(metaService.createNew(SpecialiteMedecin.class.getName()));
		specialiteRepository.saveAndFlush(specialiteMedecin2);*/
	}
}
