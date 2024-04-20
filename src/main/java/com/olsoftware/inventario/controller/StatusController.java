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

import com.olsoftware.inventario.model.status.StatusDto;
import com.olsoftware.inventario.model.status.StatusRequest;
import com.olsoftware.inventario.service.status.StatusService;

@RestController
@RequestMapping("api/inventario/status")
public class StatusController
	extends GenericController<StatusDto, StatusRequest, String> {

	public StatusController(StatusService _service) {
		super(_service);
	}

	@Override
	@GetMapping("/getStatuses")
	public ResponseEntity<Page<StatusDto>> index(String search, Pageable pageable) {
		return super.index(search, pageable);
	}

	@Override
	@GetMapping("/getStatus/{id}")
	public ResponseEntity<StatusDto> show(String id) {
		return super.show(id);
	}

	@Override
	@PostMapping("/createStatus")
	@CacheEvict(value = "getStatuses", allEntries = true)
	public ResponseEntity<StatusDto> create(StatusRequest request) {
		return super.create(request);
	}

	@Override
	@PatchMapping("/updateStatus/{id}")
	@CacheEvict(value = { "getStatus", "findStatusById" }, key = "#id")
	public ResponseEntity<StatusDto> update(String id, StatusRequest request) {
		return super.update(id, request);
	}

	@Override
	@DeleteMapping("/deleteStatus/{id}")
	@CacheEvict(value = "getStatuses", allEntries = true)
	public ResponseEntity<Void> delete(String id) {
		return super.delete(id);
	}
}
