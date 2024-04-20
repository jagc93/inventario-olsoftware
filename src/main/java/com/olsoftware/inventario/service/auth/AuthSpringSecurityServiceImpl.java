package com.olsoftware.inventario.service.auth;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.olsoftware.inventario.entity.User;
import com.olsoftware.inventario.model.auth.AuthenticationDto;
import com.olsoftware.inventario.model.auth.AuthenticationMapper;
import com.olsoftware.inventario.repository.UserRepository;

@Service
public class AuthSpringSecurityServiceImpl implements AuthSpringSecurityService {

	private final UserRepository repository;
	private final AuthenticationMapper mapper;
	private final PasswordEncoder passwordEncoder;

	public AuthSpringSecurityServiceImpl(
			UserRepository _repository,
			AuthenticationMapper _mapper,
			PasswordEncoder _passwordEncoder
	) {
		this.repository = _repository;
		this.mapper = _mapper;
		this.passwordEncoder = _passwordEncoder;
	}

	public AuthenticationDto login(String username) {
		User user = repository.findByUsername(username);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return Optional.of(user)
				.map(mapper::toDto)
				.get();
	}
}
