package com.olsoftware.inventario.config.security.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.olsoftware.inventario.service.jwt.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	public JwtAuthenticationFilter(JwtService _jwtService) {
		this.jwtService = _jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.split("Bearer ").length != 2) {
			filterChain.doFilter(request, response);
			return;
		}

		String jwt = authHeader.split("Bearer ")[1].trim();

		try {
			String username = jwtService.extractUsername(jwt);
			Collection<? extends GrantedAuthority> permissions = jwtService.extractPermissions(jwt);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, permissions);

			SecurityContextHolder.getContext().setAuthentication(authToken);

			filterChain.doFilter(request, response);
		} catch(ExpiredJwtException eje) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT expired");
		} catch(Exception e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
	}
}
