package com.olsoftware.inventario.service.auth;

import com.olsoftware.inventario.model.auth.AuthenticationDto;

public interface AuthSpringSecurityService {
	public AuthenticationDto login(String username);
}
