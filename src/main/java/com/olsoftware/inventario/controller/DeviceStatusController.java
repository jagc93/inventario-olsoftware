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

import com.olsoftware.inventario.model.deviceStatus.DeviceStatusDto;
import com.olsoftware.inventario.model.deviceStatus.DeviceStatusRequest;
import com.olsoftware.inventario.service.deviceStatus.DeviceStatusService;

@RestController
@RequestMapping("api/inventario/deviceStatus")
public class DeviceStatusController
	extends GenericController<DeviceStatusDto, DeviceStatusRequest, Long> {

	public DeviceStatusController(DeviceStatusService _service) {
		super(_service);
	}

	@Override
	@GetMapping("/getDeviceStatuses")
	public ResponseEntity<Page<DeviceStatusDto>> index(String search, Pageable pageable) {
		return super.index(search, pageable);
	}

	@Override
	@GetMapping("/getDeviceStatus/{id}")
	public ResponseEntity<DeviceStatusDto> show(Long id) {
		return super.show(id);
	}

	@Override
	@PostMapping("/createDeviceStatus")
	public ResponseEntity<DeviceStatusDto> create(DeviceStatusRequest request) {
		return super.create(request);
	}

	@Override
	@PatchMapping("/updateDeviceStatus/{id}")
	public ResponseEntity<DeviceStatusDto> update(Long id, DeviceStatusRequest request) {
		return super.update(id, request);
	}

	@Override
	@DeleteMapping("/deleteDeviceStatus/{id}")
	public ResponseEntity<Void> delete(Long id) {
		return super.delete(id);
	}
}
