package com.olsoftware.inventario.service.jwt;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.olsoftware.inventario.model.user.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
@SuppressWarnings("deprecation")
public class JwtServiceImpl implements JwtService {

	@Value("${security.jwt.expiration-minutes}")
	private long expirationMinutes;

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Override
	public String generateToken(UserDto user, Map<String, Object> extraClaims) {
		Date issuedAt = new Date(System.currentTimeMillis());
		Date expiration = new Date(issuedAt.getTime() + (expirationMinutes * 60 * 1000));

		return Jwts.builder()
				.claims(extraClaims)
				.subject(user.getUsername())
				.issuedAt(issuedAt)
				.expiration(expiration)
				.header()
					.add("typ", "JWT")
					.and()
				.signWith(generateKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	@Override
	public String extractUsername(String jwt) {
		return extractAllClaims(jwt).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> extractPermissions(String jwt) {
		Claims claims = extractAllClaims(jwt);
	    Object permissionsObject = claims.get("permissions");
	    List<?> permissionsList = (List<?>) permissionsObject;

	    return permissionsList.stream()
	            .filter(Map.class::isInstance)
	            .map(Map.class::cast)
	            .map(permissionMap -> permissionMap.get("authority"))
	            .filter(String.class::isInstance)
	            .map(String.class::cast)
	            .map(SimpleGrantedAuthority::new)
	            .collect(Collectors.toList());
	}
	
	private Key generateKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	private Claims extractAllClaims(String jwt) {
		return Jwts
				.parser()
				.setSigningKey(generateKey())
				.build()
				.parseClaimsJws(jwt)
				.getBody();
	}
}
