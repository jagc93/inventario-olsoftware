package com.olsoftware.inventario.model.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.olsoftware.inventario.enums.Permission;
import com.olsoftware.inventario.model.user.UserDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationDto extends UserDto implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> permissions = new ArrayList<>();

		if (this.getRole() != null && this.getRole().getRoleCode() != null) {
			Permission permission = parsePermission(this.getRole().getRoleCode());
			if (permission != null) {
				permissions.add(permission.name());
				permissions.add(String.format("ROLE_%s", permission.name()));
			}
		}

		return permissions.stream()
				.map(SimpleGrantedAuthority::new)
	    		.collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.getStatus() != null && this.getStatus().getStatusID().equalsIgnoreCase("A");
	}

	private Permission parsePermission(String value) {
	    for (Permission permission : Permission.values()) {
	        if (permission.name().equalsIgnoreCase(value)) {
	            return permission;
	        }
	    }

	    return null;
	}
}
