package com.olsoftware.inventario.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olsoftware.inventario.model.role.RoleDto;
import com.olsoftware.inventario.model.role.RoleRequest;
import com.olsoftware.inventario.service.role.RoleService;

@RestController
@RequestMapping("api/inventario/role")
public class RoleController
	extends GenericController<RoleDto, RoleRequest, Long> {

	public RoleController(RoleService _service) {
		super(_service);
	}

	@Override
	@GetMapping("/getRoles")
	public ResponseEntity<Page<RoleDto>> index(String search, Pageable pageable) {
		return super.index(search, pageable);
	}

	@Override
	@GetMapping("/getRole/{id}")
	public ResponseEntity<RoleDto> show(Long id) {
		return super.show(id);
	}

	@Override
	@PostMapping("/createRole")
	@CacheEvict(value = "getRoles", allEntries = true)
	public ResponseEntity<RoleDto> create(RoleRequest request) {
		return super.create(request);
	}

	@Override
	@PatchMapping("/updateRole/{id}")
	@CacheEvict(value = { "getRole", "findRoleById" }, key = "#id")
	public ResponseEntity<RoleDto> update(Long id, RoleRequest request) {
		return super.update(id, request);
	}

	@Override
	@DeleteMapping("/deleteRole/{id}")
	@CacheEvict(value = "getRoles", allEntries = true)
	public ResponseEntity<Void> delete(Long id) {
		return super.delete(id);
	}
}
