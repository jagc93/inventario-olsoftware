package com.olsoftware.inventario.service.auth;

import static com.olsoftware.inventario.exception.GenericException.requireNotNull;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.olsoftware.inventario.model.auth.AuthenticationDto;
import com.olsoftware.inventario.model.auth.AuthenticationRequest;
import com.olsoftware.inventario.model.auth.AuthenticationResponse;
import com.olsoftware.inventario.service.jwt.JwtService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthenticationServiceImpl(
			AuthenticationManager _authenticationManager,
			JwtService _jwtService
	) {
		this.authenticationManager = _authenticationManager;
		this.jwtService = _jwtService;
	}

	public AuthenticationResponse login(AuthenticationRequest request) {
		validateRequiredFields(request);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				request.getUsername(),
				request.getPassword()
		);
		Authentication authentication = authenticationManager.authenticate(authToken);
		AuthenticationDto authenticationDto = (AuthenticationDto) authentication.getPrincipal();
		return AuthenticationResponse
				.builder()
				.accessToken(jwtService.generateToken(authenticationDto, generateExtraClaims(authenticationDto)))
				.build();
	}

	private Map<String, Object> generateExtraClaims(AuthenticationDto authentication) {
		Map<String, Object> extraClaims = new HashMap<>();
		extraClaims.put("user_id", authentication.getUserID());
		extraClaims.put("name", authentication.getFirstName());
		extraClaims.put("last_name", authentication.getLastName());
		extraClaims.put("permissions", authentication.getAuthorities());
		return extraClaims;
	}

	private void validateRequiredFields(AuthenticationRequest request) {
		requireNotNull(request.getUsername(), "username");
		requireNotNull(request.getPassword(), "password");
	}
}
