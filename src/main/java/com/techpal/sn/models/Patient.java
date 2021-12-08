package com.techpal.sn.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
//@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Patient {

    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    @NotNull
    private String nomPatient;

    @NotNull
    private String prenomPatient;

    private String emailPatient;

    @NotNull
    @Column(unique=true)
    private String numeroTelephonePatient;

    @NotNull
    @Column(name = "situation_matrimoniale", nullable = false)
    private String situationMatrimoniale;

    @Column(name = "assurance_medicale")
    private String assuranceMedicale;

    @Column(name = "code_assurance")
    private String codeAssurance;

    @NotNull
    @Column(name = "nom_and_prenom_ice", nullable = false)
    private String nomAndPrenomICE;

    @NotNull
    @Column(name = "numero_telephone_ice", nullable = false, unique = true)
    private String numeroTelephoneICE;

    @OneToMany(mappedBy = "patient")
    @JsonIgnoreProperties(value = { "patient" }, allowSetters = true)
    private Set<Facture> factures = new HashSet<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.DETACH)
    @JsonIgnoreProperties(value = { "patient" }, allowSetters = true)
    private Set<RendezVous> rendezVous = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @JsonIgnoreProperties(value = { "lits", "patient" }, allowSetters = true)
    private Set<Chambre> chambres = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @JsonIgnoreProperties(value = { "patient" }, allowSetters = true)
    private Set<Consultation> consultations = new HashSet<>();


    @JsonIgnoreProperties(value = { "patient", "hospitalisation", "chambre" }, allowSetters = true)
    @OneToOne(mappedBy = "patient")
    private Lit lit;

    @JsonIgnore
    @JoinColumn(name = "linked_meta", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Meta linkedMeta;

}
