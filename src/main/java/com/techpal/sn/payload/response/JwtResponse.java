package com.techpal.sn.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private String uidUser;
	private List<String> roles;

	private  String lastName;

	private  String firstName;

	private  String numeroTelephone;

	private  String password;

	private  String specialiteMedecin;

	public JwtResponse(String username, String email, String uidUser,String lastName,
						String firstName, String numeroTelephone,
				 	    List<String> roles,
					    String specialiteMedecin) {

		this.username = username;
		this.email = email;
		this.uidUser = uidUser;
		this.lastName = lastName;
		this.firstName = firstName;
		this.numeroTelephone = numeroTelephone;
		this.roles = roles;
		this.specialiteMedecin = specialiteMedecin;
	}

	public String getUidUser() {
		return uidUser;
	}

	public void setUidUser(String uidUser) {
		this.uidUser = uidUser;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getSpecialiteMedecin() {
		return specialiteMedecin;
	}

	public void setSpecialiteMedecin(String specialiteMedecin) {
		this.specialiteMedecin = specialiteMedecin;
	}
}
