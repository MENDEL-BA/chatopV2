package com.techpal.sn.models;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "RENTAL_USER_MESSAGES")
@Data
@IdClass(RentalUserMessage.class)
public class RentalUserMessage implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rentals rental;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

}
