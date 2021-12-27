package com.techpal.sn.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "titre_menu", nullable = false)
    private String titreMenu;

    @ManyToOne
    //@Column(name = "user", nullable = false)
    private User user;

    @Column(name = "is_authorized", nullable = false)
    private boolean isAuthorized;

    @JsonIgnore
    @JoinColumn(name = "linked_meta", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Meta externalId;

}
