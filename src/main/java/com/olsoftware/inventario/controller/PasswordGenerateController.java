package com.olsoftware.inventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olsoftware.inventario.service.passwordGenerate.PasswordGenerateService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("password-generate")
public class PasswordGenerateController {

	private final PasswordGenerateService service;

	public PasswordGenerateController(PasswordGenerateService _service) {
		this.service = _service;
	}

	@Hidden
	@GetMapping
	public ResponseEntity<String> generate() {
		return ResponseEntity.ok(service.generate());
	}
}
