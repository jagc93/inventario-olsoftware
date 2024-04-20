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

import com.olsoftware.inventario.model.deviceModel.DeviceModelDto;
import com.olsoftware.inventario.model.deviceModel.DeviceModelRequest;
import com.olsoftware.inventario.service.deviceModel.DeviceModelService;

@RestController
@RequestMapping("api/inventario/deviceModel")
public class DeviceModelController
	extends GenericController<DeviceModelDto, DeviceModelRequest, Long> {

	public DeviceModelController(DeviceModelService _service) {
		super(_service);
	}

	@Override
	@GetMapping("/getDeviceModels")
	public ResponseEntity<Page<DeviceModelDto>> index(String search, Pageable pageable) {
		return super.index(search, pageable);
	}

	@Override
	@GetMapping("/getDeviceModel/{id}")
	public ResponseEntity<DeviceModelDto> show(Long id) {
		return super.show(id);
	}

	@Override
	@PostMapping("/createDeviceModel")
	public ResponseEntity<DeviceModelDto> create(DeviceModelRequest request) {
		return super.create(request);
	}

	@Override
	@PatchMapping("/updateDeviceModel/{id}")
	public ResponseEntity<DeviceModelDto> update(Long id, DeviceModelRequest request) {
		return super.update(id, request);
	}

	@Override
	@DeleteMapping("/deleteDeviceModel/{id}")
	public ResponseEntity<Void> delete(Long id) {
		return super.delete(id);
	}
}
