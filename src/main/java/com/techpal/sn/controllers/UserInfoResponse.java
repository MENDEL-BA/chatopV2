package com.techpal.sn.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {
   
    private Long id;
    private String email;
    private List<String> roles;

    public UserInfoResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
