package com.techpal.sn.dto;

import java.math.BigDecimal;
import java.util.Date;

public class RentalDTO {
    private Long id;
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;
    private int owner_id;
    private Date created_at;
    private Date updated_at;

    // Constructeur par défaut
    public RentalDTO() {
    }

    // Constructeur avec tous les attributs
    public RentalDTO(Long id, String name, BigDecimal surface, BigDecimal price, String picture,
                     String description, int owner_id, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.owner_id = owner_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getters et setters (ajoutez-les pour tous les attributs)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSurface() {
        return surface;
    }

    public void setSurface(BigDecimal surface) {
        this.surface = surface;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
