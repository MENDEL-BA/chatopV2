package com.techpal.sn.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    //@NotNull
    @Column(name = "date_consultation", nullable = false)
    private LocalDate dateConsultation;

    @NotNull
    @Column(name = "diagnostic_consultation", nullable = false)
    private String diagnosticConsultation;

    @NotNull
    @Column(name = "type_consultation", nullable = false)
    private String typeConsultation;

    //@NotNull
    @Column(name = "prix_consultation")
    private String prixConsultation;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(allowSetters = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties()
    private User user;

    @JsonIgnore
    @JoinColumn(name = "linked_meta", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Meta linkedMeta;

}
