package com.techpal.sn.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "external_id", nullable = false, length = 255)
    private String externalId;

    @Basic(optional = false)
    @Column(name = "resource_type", nullable = false, length = 55)
    private String resourceType;

    @Basic(optional = false)
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "last_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @Column(name = "created_by")
    private Long author;

    public Meta() {
    }

    public Meta(Long id) {
        this.id = id;
    }

    public Meta(Long id, String externalId, String resourceType, Date createdAt) {
        this.id = id;
        this.externalId = externalId;
        this.resourceType = resourceType;
        this.createdAt = createdAt;
    }
}
