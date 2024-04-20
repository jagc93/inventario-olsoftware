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

import com.olsoftware.inventario.model.area.AreaDto;
import com.olsoftware.inventario.model.area.AreaRequest;
import com.olsoftware.inventario.service.area.AreaService;

@RestController
@RequestMapping("api/inventario/area")
public class AreaController extends GenericController<AreaDto, AreaRequest, Long> {

	public AreaController(AreaService _service) {
		super(_service);
	}

	@Override
	@GetMapping("/getAreas")
	public ResponseEntity<Page<AreaDto>> index(String search, Pageable pageable) {
		return super.index(search, pageable);
	}

	@Override
	@GetMapping("/getArea/{id}")
	public ResponseEntity<AreaDto> show(Long id) {
		return super.show(id);
	}

	@Override
	@PostMapping("/createArea")
	@CacheEvict(value = "getAreas", allEntries = true)
	public ResponseEntity<AreaDto> create(AreaRequest request) {
		return super.create(request);
	}

	@Override
	@PatchMapping("/updateArea/{id}")
	@CacheEvict(value = { "getArea", "findAreaById" }, key = "#id")
	public ResponseEntity<AreaDto> update(Long id, AreaRequest request) {
		return super.update(id, request);
	}

	@Override
	@DeleteMapping("/deleteArea/{id}")
	@CacheEvict(value = "getAreas", allEntries = true)
	public ResponseEntity<Void> delete(Long id) {
		return super.delete(id);
	}
}
