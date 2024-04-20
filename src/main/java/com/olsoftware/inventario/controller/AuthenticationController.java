package com.olsoftware.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olsoftware.inventario.model.auth.AuthenticationRequest;
import com.olsoftware.inventario.model.auth.AuthenticationResponse;
import com.olsoftware.inventario.service.auth.AuthenticationService;

@RestController
@RequestMapping("api/inventario")
public class AuthenticationController {

	private final AuthenticationService service;

	public AuthenticationController(AuthenticationService _service) {
		this.service = _service;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(service.login(request));
	}
}
