package com.techpal.sn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class SpringBootSecurityJwtApplication implements CommandLineRunner {
public class SpringBootSecurityJwtApplication {

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


	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//
//		SpecialiteMedecin specialiteMedecin = new SpecialiteMedecin();
//
//		specialiteMedecin.setNomSpecialite("Generaliste");
//		specialiteMedecin.setLinkedMeta(metaService.createNew(SpecialiteMedecin.class.getName()));
//		specialiteRepository.saveAndFlush(specialiteMedecin);
//
//		SpecialiteMedecin specialiteMedecin1 = new SpecialiteMedecin();
//
//		specialiteMedecin1.setNomSpecialite("Cardiologue");
//		specialiteMedecin1.setLinkedMeta(metaService.createNew(SpecialiteMedecin.class.getName()));
//		specialiteRepository.saveAndFlush(specialiteMedecin1);
//
//		SpecialiteMedecin specialiteMedecin2 = new SpecialiteMedecin();
//
//		specialiteMedecin2.setNomSpecialite("Dentiste");
//		specialiteMedecin2.setLinkedMeta(metaService.createNew(SpecialiteMedecin.class.getName()));
//		specialiteRepository.saveAndFlush(specialiteMedecin2);
//	}
}
