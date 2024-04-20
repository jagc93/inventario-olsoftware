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

import com.olsoftware.inventario.model.manufacturer.ManufacturerDto;
import com.olsoftware.inventario.model.manufacturer.ManufacturerRequest;
import com.olsoftware.inventario.service.manufacturer.ManufacturerService;

@RestController
@RequestMapping("api/inventario/manufacturer")
public class ManufacturerController
	extends GenericController<ManufacturerDto, ManufacturerRequest, Long> {

	public ManufacturerController(ManufacturerService _service) {
		super(_service);
	}

	@Override
	@GetMapping("/getManufacturers")
	public ResponseEntity<Page<ManufacturerDto>> index(String search, Pageable pageable) {
		return super.index(search, pageable);
	}

	@Override
	@GetMapping("/getManufacturer/{id}")
	public ResponseEntity<ManufacturerDto> show(Long id) {
		return super.show(id);
	}

	@Override
	@PostMapping("/createManufacturer")
	@CacheEvict(value = "getManufacturers", allEntries = true)
	public ResponseEntity<ManufacturerDto> create(ManufacturerRequest request) {
		return super.create(request);
	}

	@Override
	@PatchMapping("/updateManufacturer/{id}")
	@CacheEvict(value = { "getManufacturer", "findManufacturerById" }, key = "#id")
	public ResponseEntity<ManufacturerDto> update(Long id, ManufacturerRequest request) {
		return super.update(id, request);
	}

	@Override
	@DeleteMapping("/deleteManufacturer/{id}")
	@CacheEvict(value = "getManufacturers", allEntries = true)
	public ResponseEntity<Void> delete(Long id) {
		return super.delete(id);
	}
}
