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
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_facturation", nullable = false)
    private LocalDate dateFacturation;

    @NotNull
    @Column(name = "date_payement", nullable = false)
    private LocalDate datePaiement;

    @Column(name = "est_reglee")
    private Boolean estReglee;

    @Column(name = "montant_net")
    private Integer montantFacture;

    @ManyToOne
    @JsonIgnoreProperties(value = { "factures", "rendezVous", "chambres", "lit", "consultation" }, allowSetters = true)
    private Patient patient;

    @JsonIgnore
    @JoinColumn(name = "linked_meta", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Meta linkedMeta;
}
