package com.olsoftware.inventario.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olsoftware.inventario.model.deviceInventory.DeviceInventoryDto;
import com.olsoftware.inventario.model.deviceInventory.DeviceInventoryRequest;
import com.olsoftware.inventario.service.deviceInventory.DeviceInventoryService;

@RestController
@RequestMapping("api/inventario/deviceInventory")
public class DeviceInventoryController
	extends GenericController<DeviceInventoryDto, DeviceInventoryRequest, Long> {

	public DeviceInventoryController(DeviceInventoryService _service) {
		super(_service);
	}

	@Override
	@GetMapping("/getDeviceInventories")
	public ResponseEntity<Page<DeviceInventoryDto>> index(String search, Pageable pageable) {
		return super.index(search, pageable);
	}

	@Override
	@GetMapping("/getDeviceInventory/{id}")
	public ResponseEntity<DeviceInventoryDto> show(Long id) {
		return super.show(id);
	}

	@Override
	@PostMapping("/createDeviceInventory")
	public ResponseEntity<DeviceInventoryDto> create(DeviceInventoryRequest request) {
		return super.create(request);
	}

	@Override
	@PatchMapping("/updateDeviceInventory/{id}")
	public ResponseEntity<DeviceInventoryDto> update(Long id, DeviceInventoryRequest request) {
		return super.update(id, request);
	}

	@Override
	@DeleteMapping("/deleteDeviceInventory/{id}")
	public ResponseEntity<Void> delete(Long id) {
		return super.delete(id);
	}
}
