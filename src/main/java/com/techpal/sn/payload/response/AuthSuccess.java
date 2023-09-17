package com.techpal.sn.payload.response;

import lombok.Data;

@Data
public class AuthSuccess {

    private String token;

    public AuthSuccess(String token) {
        this.token = token;
    }
    public AuthSuccess() {
    }

    @Override
    public String toString() {
        return "AuthSuccess{" +
                "token='" + token + '\'' +
                '}';
    }
}
