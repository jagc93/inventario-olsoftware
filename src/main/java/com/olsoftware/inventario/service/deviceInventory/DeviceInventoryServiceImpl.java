package com.olsoftware.inventario.service.deviceInventory;

import static com.olsoftware.inventario.constant.DeviceInventoryConstant.MAX_COMMENTS_LENGTH;
import static com.olsoftware.inventario.constant.DeviceInventoryConstant.MAX_DEVICE_NAME_LENGTH;
import static com.olsoftware.inventario.constant.DeviceInventoryConstant.MAX_SERIAL_NUMBER_LENGTH;
import static com.olsoftware.inventario.exception.GenericException.getNotFound;
import static com.olsoftware.inventario.exception.GenericException.requireNotNull;
import static com.olsoftware.inventario.exception.GenericException.validateFieldLength;

import org.springframework.stereotype.Service;

import com.olsoftware.inventario.entity.Area;
import com.olsoftware.inventario.entity.DeviceInventory;
import com.olsoftware.inventario.entity.DeviceType;
import com.olsoftware.inventario.model.deviceInventory.DeviceInventoryDto;
import com.olsoftware.inventario.model.deviceInventory.DeviceInventoryMapper;
import com.olsoftware.inventario.model.deviceInventory.DeviceInventoryRequest;
import com.olsoftware.inventario.repository.DeviceInventoryRepository;
import com.olsoftware.inventario.repository.DeviceModelRepository;
import com.olsoftware.inventario.repository.DeviceStatusRepository;
import com.olsoftware.inventario.repository.UserRepository;
import com.olsoftware.inventario.service.GenericServiceImpl;
import com.olsoftware.inventario.service.status.InternalService;

@Service
public class DeviceInventoryServiceImpl
	extends GenericServiceImpl<DeviceInventoryRequest, DeviceInventory, DeviceInventoryDto, Long>
	implements DeviceInventoryService {

	private static final String FIELD_DEVICE_NAME = "deviceName";
	private static final String FIELD_SERIAL_NUMBER = "serialNumber";
	private static final String FIELD_COMMENTS = "comments";
	private static final String FIELD_USER = "userID";
	private static final String FIELD_AREA = "areaID";
	private static final String FIELD_DEVICE_STATUS = "deviceStatusID";
	private static final String FIELD_DEVICE_TYPE = "deviceTypeID";
	private static final String FIELD_DEVICE_MODEL = "deviceModelID";

	private final UserRepository userRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final InternalService<Area, Long> areaService;
	private final InternalService<DeviceType, Long> deviceTypeService;
	private final DeviceModelRepository deviceModelRepository;
	private final DeviceInventoryMapper mapper;

	public DeviceInventoryServiceImpl(
			DeviceInventoryRepository _repository,
			UserRepository _userRepository,
			DeviceStatusRepository _deviceStatusRepository,
			InternalService<Area, Long> _areaService,
			InternalService<DeviceType, Long> _deviceTypeService,
			DeviceModelRepository _deviceModelRepository,
			DeviceInventoryMapper _mapper
	) {
		super(_repository, _repository, _mapper);
		this.userRepository = _userRepository;
		this.deviceStatusRepository = _deviceStatusRepository;
		this.areaService = _areaService;
		this.deviceTypeService = _deviceTypeService;
		this.deviceModelRepository = _deviceModelRepository;
		this.mapper = _mapper;
	}

	@Override
	public DeviceInventoryDto create(DeviceInventoryRequest request) {
		validateRequiredFields(request);
		validateFieldsLength(request);

		DeviceInventory deviceInventory = mapper.toEntity(request);
		buildDeviceInventory(deviceInventory, request);

		return super.createByEntity(deviceInventory);
	}

	@Override
	public DeviceInventoryDto update(Long id, DeviceInventoryRequest request) {
		validateFieldsLength(request);

		DeviceInventory entity = super.findById(id)
				.orElseThrow(() -> getNotFound(id, "deviceInventory"));

		mapper.updateFromRequest(request, entity);
		buildDeviceInventory(entity, request);

		return super.update(entity);
	}

	private void buildDeviceInventory(DeviceInventory deviceInventory, DeviceInventoryRequest request) {
		if (request.getUserID() != null) {
			deviceInventory.setUser(userRepository.findById(request.getUserID())
					.orElseThrow(() -> getNotFound(request.getUserID(), "user")));
		}

		if (request.getAreaID() != null) {
			deviceInventory.setArea(areaService.findEntityById(request.getAreaID()));
		}

		if (request.getDeviceStatusID() != null) {
			deviceInventory.setDeviceStatus(deviceStatusRepository.findById(request.getDeviceStatusID())
					.orElseThrow(() -> getNotFound(request.getDeviceStatusID(), "deviceStatus")));
		}

		if (request.getDeviceTypeID() != null) {
			deviceInventory.setDeviceType(deviceTypeService.findEntityById(request.getDeviceTypeID()));
		}

		if (request.getDeviceModelID() != null) {
			deviceInventory.setDeviceModel(deviceModelRepository.findById(request.getDeviceModelID())
					.orElseThrow(() -> getNotFound(request.getDeviceModelID(), "deviceModel")));
		}
	}

	private void validateRequiredFields(DeviceInventoryRequest request) {
		requireNotNull(request.getDeviceName(), FIELD_DEVICE_NAME);
		requireNotNull(request.getSerialNumber(), FIELD_SERIAL_NUMBER);
		requireNotNull(request.getUserID(), FIELD_USER);
		requireNotNull(request.getAreaID(), FIELD_AREA);
		requireNotNull(request.getDeviceStatusID(), FIELD_DEVICE_STATUS);
		requireNotNull(request.getDeviceTypeID(), FIELD_DEVICE_TYPE);
		requireNotNull(request.getDeviceModelID(), FIELD_DEVICE_MODEL);
	}

	private void validateFieldsLength(DeviceInventoryRequest request) {
		validateFieldLength(request.getDeviceName(), MAX_DEVICE_NAME_LENGTH, FIELD_DEVICE_NAME);
		validateFieldLength(request.getSerialNumber(), MAX_SERIAL_NUMBER_LENGTH, FIELD_SERIAL_NUMBER);
		validateFieldLength(request.getComments(), MAX_COMMENTS_LENGTH, FIELD_COMMENTS);
	}
}
