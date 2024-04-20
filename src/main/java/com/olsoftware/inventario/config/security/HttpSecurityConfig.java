package com.olsoftware.inventario.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.olsoftware.inventario.config.security.filter.JwtAuthenticationFilter;
import com.olsoftware.inventario.enums.Permission;

@EnableWebSecurity
@Configuration
public class HttpSecurityConfig {

	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public HttpSecurityConfig(
			AuthenticationProvider _authenticationProvider,
			JwtAuthenticationFilter _jwtAuthenticationFilter
	) {
		this.authenticationProvider = _authenticationProvider;
		this.jwtAuthenticationFilter = _jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.cors(Customizer.withDefaults())
		.csrf(csrfConfig -> csrfConfig.disable())
		.sessionManagement(sessionManagementConfig -> sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		.cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
		.authorizeHttpRequests(authConfig -> {
			authConfig.requestMatchers(HttpMethod.POST, "/api/inventario/login").permitAll();
			authConfig.requestMatchers(HttpMethod.GET, "/password-generate").permitAll();
			authConfig.requestMatchers("/error").permitAll();
			authConfig.requestMatchers(HttpMethod.GET, "/doc/swagger-ui/**", "/v3/api-docs/**").permitAll();
			authConfig.requestMatchers(HttpMethod.GET, "api/inventario/deviceInventory/**").hasAnyAuthority(Permission.SUPERVISOR.name());
			authConfig.requestMatchers("/**").hasAnyAuthority(Permission.ADMIN.name());
			authConfig.anyRequest().denyAll();
		});

		return http.build();
	}

	private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
