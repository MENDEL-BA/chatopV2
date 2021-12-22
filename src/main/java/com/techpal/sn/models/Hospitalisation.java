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
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Hospitalisation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_admission", nullable = false)
    private LocalDate dateAdmission;

    @NotNull
    @Column(name = "motif_admission", nullable = false)
    private String motifAdmission;

    @Column(name = "date_sortie")
    private LocalDate dateSortie;

    @Column(name = "motif_sortie")
    private String motifSortie;

    @Column(name = "date_transfert")
    private String dateTransfert;

    @Column(name = "dossier_medical")
    private String dossierMedical;

    @NotNull
    @Column(name = "infos_accompagnat", nullable = false)
    private String infosAccompagnat;

    @Column(name = "date_deces")
    private String dateDeces;

    @Column(name = "cause_deces")
    private String causeDeces;

    @ManyToOne
    //@JsonIgnoreProperties(value = { "patient", "chambre" }, allowSetters = true)
    private Lit lits;

    @JsonIgnore
    @JoinColumn(name = "linked_meta", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Meta linkedMeta;

    @ManyToOne
    @JsonIgnoreProperties()
    private User user;

}
