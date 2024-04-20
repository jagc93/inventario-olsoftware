package com.olsoftware.inventario.service.auth;

import com.olsoftware.inventario.model.auth.AuthenticationRequest;
import com.olsoftware.inventario.model.auth.AuthenticationResponse;

public interface AuthenticationService {
	public AuthenticationResponse login(AuthenticationRequest request);
}
