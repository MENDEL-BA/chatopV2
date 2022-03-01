package com.techpal.sn.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class DossierMedical {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_creation_dossier", nullable = false)
    private LocalDate date_Dos;

    @Column(name = "libelle_Dos")
    private String  libelle_Dos;

    @ManyToOne
    @JsonIgnoreProperties(value = { "factures", "rendezVous", "chambres", "lit", "consultation" }, allowSetters = true)
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties(allowSetters = true)
    private User medecin;

    @JsonIgnore
    @JoinColumn(name = "linked_meta", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Meta linkedMeta;
}
