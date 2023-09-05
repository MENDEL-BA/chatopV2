package com.techpal.sn.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {
   
    private Long id;
    private String email;
    private String name;
    private Date created_at;
    private Date updated_at;
    private List<String> roles;

    public UserInfoResponse(Long id, String email, String name, Date created_at, Date updated_at) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
