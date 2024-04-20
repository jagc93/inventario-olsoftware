package com.olsoftware.inventario.model.user;

import java.util.Date;

import com.olsoftware.inventario.model.role.RoleDto;
import com.olsoftware.inventario.model.status.StatusDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private Long userID;
	private String identificationType;
	private String identificationNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private String secondLastName;
	private String emailAddress;
	private String phoneNumber;
	private String username;
	private RoleDto role;
	private StatusDto status;
	private Date creationDate;
	private Date modificationDate;
}
