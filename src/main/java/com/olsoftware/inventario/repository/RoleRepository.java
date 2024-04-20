package com.olsoftware.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.olsoftware.inventario.entity.Role;

@Repository
public interface RoleRepository
	extends JpaRepository<Role, Long>,
			JpaSpecificationExecutor<Role> {

	boolean existsByRoleNameIgnoreCase(String roleName);

	boolean existsByRoleCodeIgnoreCase(String roleCode);
}
