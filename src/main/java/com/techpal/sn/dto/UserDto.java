package com.techpal.sn.dto;

import com.techpal.sn.models.User;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto implements Serializable {

    private  String username;

    private  String lastName;

    private  String firstName;

    private  String numeroTelephone;

    private  String email;

    private  String role;

    private  String password;

    private  String uidUser;

   // private  String id_token;

    public UserDto() {
    }

    public UserDto(User appUser) {
        this.firstName = appUser.getFirstName();
        this.username = appUser.getUsername();
        this.lastName = appUser.getLastName();
        this.email = appUser.getEmail();
        this.numeroTelephone = appUser.getNumeroTelephone();
        this.uidUser = appUser.getLinkedMeta().getExternalId();
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }


    public static UserDto parse(User appUser) {
        return new UserDto(appUser);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", numeroTelephone='" + numeroTelephone + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public static List<UserDto> parseAll(List<User> appUsers) {
        return appUsers.stream().map(UserDto::parse).collect(Collectors.toList());
    }


}
