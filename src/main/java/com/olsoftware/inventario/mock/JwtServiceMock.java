package com.olsoftware.inventario.mock;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.olsoftware.inventario.model.auth.AuthenticationDto;
import com.olsoftware.inventario.model.role.RoleDto;
import com.olsoftware.inventario.model.status.StatusDto;
import com.olsoftware.inventario.service.jwt.JwtService;

@Component
public class JwtServiceMock {

	private final JwtService jwtService;

	public JwtServiceMock(JwtService _jwtService) {
		this.jwtService = _jwtService;
	}

	public String generateToken(AuthenticationDto authentication) {
		if (authentication == null) {
			authentication = getAutenticationDto();
		}
		return jwtService.generateToken(authentication, extraClaims(authentication));
	}

	public AuthenticationDto getAutenticationDto() {
		AuthenticationDto dto = new AuthenticationDto();
		dto.setUserID(1L);
		dto.setIdentificationType("CC");
		dto.setIdentificationNumber("111");
		dto.setFirstName("Test");
		dto.setMiddleName("Name");
		dto.setLastName("Last");
		dto.setSecondLastName("Name");
		dto.setEmailAddress("test.name@email.com");
		dto.setPhoneNumber("3111111111");
		dto.setUsername("test111");
		dto.setPassword("test111");
		dto.setRole(getRoleDto());
		dto.setStatus(getStatusDto());
		dto.setCreationDate(new Date());
		return dto;
	}
	
	public String getToken() {
		return getToken(null);
	}

	public String getToken(AuthenticationDto authentication) {
		return String.format("Bearer %s", generateToken(authentication));
	}

	public String getTokenExpired() {
		return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJwZXJtaXNzaW9ucyI6W3siYXV0aG9yaXR5IjoiQURNSU4ifV0sIm5hbWUiOiJQZXBlIiwibGFzdF9uYW1lIjoiUGVyZXoiLCJzdWIiOiJwZXBlcmV6MTExIiwiaWF0IjoxNzEzNDA5NjQ5LCJleHAiOjE3MTM0MTE0NDl9.bFkuDisan0-uum9ybKgFamILceTrjVvR71bQk51qoL8";
	}

	private Map<String, Object> extraClaims(AuthenticationDto authentication) {
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("user_id", authentication.getUserID());
		extraClaims.put("name", authentication.getFirstName());
		extraClaims.put("last_name", authentication.getLastName());
		extraClaims.put("permissions", authentication.getAuthorities());
		return extraClaims;
	}

	private RoleDto getRoleDto() {
		RoleDto dto = new RoleDto();
		dto.setRoleID(1L);
		dto.setRoleName("Role test");
		dto.setRoleCode("ADMIN");
		return dto;
	}

	private StatusDto getStatusDto() {
		StatusDto dto = new StatusDto();
		dto.setStatusID("A");
		dto.setStatusName("Activo");
		return dto;
	}
}
