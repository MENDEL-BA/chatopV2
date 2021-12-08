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
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Chambre {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "localisation_chambre", nullable = false)
    private String localisationChambre;

    @NotNull
    @Column(name = "capacite_chambre", nullable = false)
    private Integer capaciteChambre;

    @NotNull
    @Column(name = "prix_chambre", nullable = false)
    private String prixChambre;

    @NotNull
    @Column(name = "type_chambre", nullable = false)
    private String typeChambre;

    @OneToMany(mappedBy = "chambre")
    @JsonIgnoreProperties(value = { "patient", "hospitalisation", "chambre" }, allowSetters = true)
    private Set<Lit> lits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "factures", "rendezVous", "chambres", "lit", "consultation" }, allowSetters = true)
    private Patient patient;

    @JsonIgnore
    @JoinColumn(name = "linked_meta", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Meta linkedMeta;
}
