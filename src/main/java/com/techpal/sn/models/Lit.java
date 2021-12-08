package com.techpal.sn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Lit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "etat")
    private Boolean etat;

    @JsonIgnoreProperties(value = { "factures", "rendezVous", "chambres", "lit", "consultation" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lits" }, allowSetters = true)
    private Hospitalisation hospitalisation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "lits", "patient" }, allowSetters = true)
    private Chambre chambre;

}
