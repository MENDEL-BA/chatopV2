package com.techpal.sn.dto;


import com.techpal.sn.models.Meta;

import java.io.Serializable;
import java.util.Date;

public class MetaDto implements Serializable {

    private String uid;
    private Date createdAt;


    public MetaDto(){}

    public MetaDto(Meta meta){
        this.uid = meta.getExternalId();
        this.createdAt = meta.getCreatedAt();
    }


    public static MetaDto parse(Meta meta){
        return new MetaDto(meta);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
