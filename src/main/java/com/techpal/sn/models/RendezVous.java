package com.techpal.sn.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techpal.sn.security.services.UserDetailsImpl;
import com.techpal.sn.security.services.UserDetailsServiceInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
//@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class RendezVous {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_rdv", nullable = false)
    private LocalDate dateRDV;

    //@NotNull
    @Column(name = "heure_rdv")
    private String heureRendezVous;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JsonIgnoreProperties(value = { "factures", "rendezVous", "chambres", "lit", "consultation" }, allowSetters = true)
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties()
    private User user;

    @JsonIgnore
    @JoinColumn(name = "linked_meta", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Meta linkedMeta;



}
