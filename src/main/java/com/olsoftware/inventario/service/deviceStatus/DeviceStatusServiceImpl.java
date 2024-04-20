package com.olsoftware.inventario.service.deviceStatus;

import static com.olsoftware.inventario.constant.DeviceStatusConstant.MAX_DEVICE_STATUS_NAME_LENGTH;
import static com.olsoftware.inventario.constant.MessagesConstant.ASSOCIATED_RECORDS;
import static com.olsoftware.inventario.exception.GenericException.requireNotNull;
import static com.olsoftware.inventario.exception.GenericException.validateFieldLength;

import org.springframework.stereotype.Service;

import com.olsoftware.inventario.entity.DeviceStatus;
import com.olsoftware.inventario.model.deviceStatus.DeviceStatusDto;
import com.olsoftware.inventario.model.deviceStatus.DeviceStatusMapper;
import com.olsoftware.inventario.model.deviceStatus.DeviceStatusRequest;
import com.olsoftware.inventario.repository.DeviceInventoryRepository;
import com.olsoftware.inventario.repository.DeviceStatusRepository;
import com.olsoftware.inventario.service.GenericServiceImpl;

@Service
public class DeviceStatusServiceImpl
	extends GenericServiceImpl<DeviceStatusRequest, DeviceStatus, DeviceStatusDto, Long>
	implements DeviceStatusService {

	private static final String FIELD_DEVICE_STATUS_NAME = "deviceStatusName";
	private final DeviceInventoryRepository deviceInventoryRepository;

	public DeviceStatusServiceImpl(
			DeviceStatusRepository _repository,
			DeviceInventoryRepository _deviceInventoryRepository,
			DeviceStatusMapper _mapper
	) {
		super(_repository, _repository, _mapper);
		this.deviceInventoryRepository = _deviceInventoryRepository;
	}

	@Override
	public DeviceStatusDto create(DeviceStatusRequest request) {
		validateRequiredFields(request);
		validateFieldsLength(request);
		return super.create(request);
	}

	@Override
	public DeviceStatusDto update(Long id, DeviceStatusRequest request) {
		validateFieldsLength(request);
		return super.update(id, request);
	}

	@Override
	public void delete(Long id) {
		if (deviceInventoryRepository.existsByDeviceStatusDeviceStatusID(id)) {
			throw new RuntimeException(ASSOCIATED_RECORDS);
		}
		super.delete(id);
	}

	private void validateRequiredFields(DeviceStatusRequest request) {
		requireNotNull(request.getDeviceStatusName(), FIELD_DEVICE_STATUS_NAME);
	}

	private void validateFieldsLength(DeviceStatusRequest request) {
		validateFieldLength(request.getDeviceStatusName(), MAX_DEVICE_STATUS_NAME_LENGTH, FIELD_DEVICE_STATUS_NAME);
	}	
}
