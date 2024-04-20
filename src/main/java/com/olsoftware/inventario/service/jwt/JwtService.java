package com.olsoftware.inventario.service.jwt;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

import com.olsoftware.inventario.model.user.UserDto;

public interface JwtService {
	public String generateToken(UserDto user, Map<String, Object> extraClaims);
	public String extractUsername(String jwt);
	public Collection<? extends GrantedAuthority> extractPermissions(String jwt);
}
