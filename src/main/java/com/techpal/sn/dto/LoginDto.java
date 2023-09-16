package com.techpal.sn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LoginDto implements Serializable {
    private String email;
    private String password;

    public LoginDto() {
    }
}
