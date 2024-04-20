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

import com.olsoftware.inventario.model.deviceType.DeviceTypeDto;
import com.olsoftware.inventario.model.deviceType.DeviceTypeRequest;
import com.olsoftware.inventario.service.deviceType.DeviceTypeService;

@RestController
@RequestMapping("api/inventario/deviceType")
public class DeviceTypeController
	extends GenericController<DeviceTypeDto, DeviceTypeRequest, Long> {

	public DeviceTypeController(DeviceTypeService _service) {
		super(_service);
	}

	@Override
	@GetMapping("/getDeviceTypes")
	public ResponseEntity<Page<DeviceTypeDto>> index(String search, Pageable pageable) {
		return super.index(search, pageable);
	}

	@Override
	@GetMapping("/getDeviceType/{id}")
	public ResponseEntity<DeviceTypeDto> show(Long id) {
		return super.show(id);
	}

	@Override
	@PostMapping("/createDeviceType")
	@CacheEvict(value = "getDeviceTypes", allEntries = true)
	public ResponseEntity<DeviceTypeDto> create(DeviceTypeRequest request) {
		return super.create(request);
	}

	@Override
	@PatchMapping("/updateDeviceType/{id}")
	@CacheEvict(value = { "getDeviceType", "findDeviceTypeById" }, key = "#id")
	public ResponseEntity<DeviceTypeDto> update(Long id, DeviceTypeRequest request) {
		return super.update(id, request);
	}

	@Override
	@DeleteMapping("/deleteDeviceType/{id}")
	@CacheEvict(value = "getDeviceTypes", allEntries = true)
	public ResponseEntity<Void> delete(Long id) {
		return super.delete(id);
	}
}
