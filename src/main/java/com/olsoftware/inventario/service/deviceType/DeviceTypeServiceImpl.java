package com.olsoftware.inventario.service.deviceType;

import static com.olsoftware.inventario.constant.DeviceTypeConstant.MAX_DEVICE_TYPE_LENGTH;
import static com.olsoftware.inventario.constant.MessagesConstant.ASSOCIATED_RECORDS;
import static com.olsoftware.inventario.exception.GenericException.getNotFound;
import static com.olsoftware.inventario.exception.GenericException.requireNotNull;
import static com.olsoftware.inventario.exception.GenericException.validateFieldLength;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.olsoftware.inventario.entity.DeviceType;
import com.olsoftware.inventario.model.deviceType.DeviceTypeDto;
import com.olsoftware.inventario.model.deviceType.DeviceTypeMapper;
import com.olsoftware.inventario.model.deviceType.DeviceTypeRequest;
import com.olsoftware.inventario.repository.DeviceInventoryRepository;
import com.olsoftware.inventario.repository.DeviceTypeRepository;
import com.olsoftware.inventario.service.GenericServiceImpl;
import com.olsoftware.inventario.service.status.InternalService;

@Service
public class DeviceTypeServiceImpl
	extends GenericServiceImpl<DeviceTypeRequest, DeviceType, DeviceTypeDto, Long>
	implements DeviceTypeService, InternalService<DeviceType, Long> {

	private static final String FIELD_DEVICE_TYPE = "deviceType";
	private final DeviceInventoryRepository deviceInventoryRepository;

	public DeviceTypeServiceImpl(
			DeviceTypeRepository _repository,
			DeviceInventoryRepository _deviceInventoryRepository,
			DeviceTypeMapper _mapper
	) {
		super(_repository, _repository, _mapper);
		this.deviceInventoryRepository = _deviceInventoryRepository;
	}

	@Override
	@Cacheable("getDeviceTypes")
	public Page<DeviceTypeDto> index(Pageable pageable, String search) {
		return super.index(pageable, search);
	}

	@Override
	@Cacheable(value = "getDeviceType", key = "#id")
	public DeviceTypeDto show(Long id) {
		return super.show(id);
	}

	@Override
	@Cacheable(value = "findDeviceTypeById", key = "#id")
	public DeviceType findEntityById(Long id) {
		return super.findById(id)
				.orElseThrow(() -> getNotFound(id, "DeviceType"));
	}

	@Override
	public DeviceTypeDto create(DeviceTypeRequest request) {
		validateRequiredFields(request);
		validateFieldsLength(request);
		return super.create(request);
	}

	@Override
	@CacheEvict(value = "getDeviceTypes", allEntries = true)
	public DeviceTypeDto update(Long id, DeviceTypeRequest request) {
		validateFieldsLength(request);
		return super.update(id, request);
	}

	@Override
	@CacheEvict(value = { "getDeviceType", "findDeviceTypeById" }, key = "#id")
	public void delete(Long id) {
		if (deviceInventoryRepository.existsByDeviceTypeDeviceTypeID(id)) {
			throw new RuntimeException(ASSOCIATED_RECORDS);
		}
		super.delete(id);
	}

	private void validateRequiredFields(DeviceTypeRequest request) {
		requireNotNull(request.getDeviceType(), FIELD_DEVICE_TYPE);
	}

	private void validateFieldsLength(DeviceTypeRequest request) {
		validateFieldLength(request.getDeviceType(), MAX_DEVICE_TYPE_LENGTH, FIELD_DEVICE_TYPE);
	}
}
