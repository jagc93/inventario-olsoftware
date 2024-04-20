package com.olsoftware.inventario.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	private String identificationType;
	private String identificationNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private String secondLastName;
	private String emailAddress;
	private String phoneNumber;
	private String username;
	private String password;
	private Long roleID;
	private String statusID;
}
