package com.olsoftware.inventario.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.olsoftware.inventario.service.auth.AuthSpringSecurityService;

@Component
public class SecurityBeansInjector {

	private final PasswordEncoder passwordEncoder;
	private final AuthSpringSecurityService authSpringSecurityService;

	public SecurityBeansInjector(
			PasswordEncoder _passwordEncoder,
			AuthSpringSecurityService _authenticationService
	) {
		this.passwordEncoder = _passwordEncoder;
		this.authSpringSecurityService = _authenticationService;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> {
			return authSpringSecurityService.login(username);
		};
	}
}
