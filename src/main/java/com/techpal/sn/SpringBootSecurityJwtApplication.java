package com.techpal.sn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class SpringBootSecurityJwtApplication implements CommandLineRunner {
public class SpringBootSecurityJwtApplication {

//	private final MetaService metaService;
//
//	private final PatientService patientService;
//
//	private final LitRepository litRepository;
//
//	private final ChambreRepository chambreRepository;
//
//	private final HospitalisationRepository hospitalisationRepository;
//
//	public SpringBootSecurityJwtApplication(MetaService metaService, PatientService patientService, LitRepository litRepository, ChambreRepository chambreRepository, HospitalisationRepository hospitalisationRepository) {
//		this.metaService = metaService;
//		this.patientService = patientService;
//		this.litRepository = litRepository;
//		this.chambreRepository = chambreRepository;
//		this.hospitalisationRepository = hospitalisationRepository;
//	}

//	@Bean
//	public CommandLineRunner websocketDemo() {
//		return (args) -> {
//			while (true) {
//				try {
//					Thread.sleep(3*1000); // Each 3 sec.
//					progress.put("num1", randomWithRange(0, 100));
//					progress.put("num2", randomWithRange(0, 100));
//					messagingTemplate.convertAndSend("/topic/progress", this.progress);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		};

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}

	//Override
	//public void run(String... args) throws Exception {
		/*Chambre chambre = new Chambre();
		Lit lit = new Lit();

		lit.setEtat(true);
		lit.setPatient(patientService.getPatientByExternalId("5pcn-tu5l-kqc3-hj77-j35l-dt77-odr2-hh6m"));
		lit.setNumero("Lt1");
		lit.setLinkedMeta(metaService.createNew(Lit.class.getName()));

		Lit litSave = litRepository.saveAndFlush(lit);

		Lit lit1 = new Lit();
		lit1.setEtat(true);
		lit1.setPatient(patientService.getPatientByExternalId("rq0g-4v7l-qlec-4f59-7a7b-eg81-kppb-mrad"));
		lit1.setNumero("Lt2");
		lit1.setLinkedMeta(metaService.createNew(Lit.class.getName()));

		Lit litSave1 = litRepository.saveAndFlush(lit1);

		chambre.setCapaciteChambre(10);
		chambre.setPrixChambre("25000");
		chambre.setTypeChambre("Individuelle");
		chambre.setLocalisationChambre("Deuxieme etage");
		chambre.setLinkedMeta(metaService.createNew(Chambre.class.getName()));
		chambre.setLits(litSave);

		chambreRepository.saveAndFlush(chambre);

		Chambre chambre1 = new Chambre();
		chambre1.setCapaciteChambre(10);
		chambre1.setPrixChambre("15000");
		chambre1.setTypeChambre("commune");
		chambre1.setLocalisationChambre("Deuxieme etage à droite");
		chambre1.setLinkedMeta(metaService.createNew(Chambre.class.getName()));
		chambre1.setLits(litSave1);

		chambreRepository.saveAndFlush(chambre1);

		Hospitalisation hospitalisation = new Hospitalisation();

		hospitalisation.setMotifAdmission("Douleurs au niveau du ventre");
		hospitalisation.setDateAdmission(LocalDate.now());
		hospitalisation.setDateTransfert("12/09/2020");
		hospitalisation.setLits(litSave);
		hospitalisation.setLinkedMeta(metaService.createNew(Hospitalisation.class.getName()));
		hospitalisation.setInfosAccompagnat("Modou Gueye");

		Hospitalisation hospitalisationSave = hospitalisationRepository.saveAndFlush(hospitalisation);
*/

	//}
}
