package com.olsoftware.inventario.service.deviceModel;

import static com.olsoftware.inventario.constant.DeviceModelConstant.MAX_MODEL_NAME_LENGTH;
import static com.olsoftware.inventario.constant.MessagesConstant.ASSOCIATED_RECORDS;
import static com.olsoftware.inventario.exception.GenericException.getNotFound;
import static com.olsoftware.inventario.exception.GenericException.requireNotNull;
import static com.olsoftware.inventario.exception.GenericException.validateFieldLength;

import org.springframework.stereotype.Service;

import com.olsoftware.inventario.entity.DeviceModel;
import com.olsoftware.inventario.entity.Manufacturer;
import com.olsoftware.inventario.model.deviceModel.DeviceModelDto;
import com.olsoftware.inventario.model.deviceModel.DeviceModelMapper;
import com.olsoftware.inventario.model.deviceModel.DeviceModelRequest;
import com.olsoftware.inventario.repository.DeviceInventoryRepository;
import com.olsoftware.inventario.repository.DeviceModelRepository;
import com.olsoftware.inventario.service.GenericServiceImpl;
import com.olsoftware.inventario.service.status.InternalService;

@Service
public class DeviceModelServiceImpl
	extends GenericServiceImpl<DeviceModelRequest, DeviceModel, DeviceModelDto, Long>
	implements DeviceModelService {

	private static final String FIELD_MODEL_NAME = "modelName";
	private static final String FIELD_MANUFACTURER = "manufacturerID";

	private final DeviceInventoryRepository deviceInventoryRepository;
	private final InternalService<Manufacturer, Long> manufacturerService;
	private final DeviceModelMapper mapper;
	
	public DeviceModelServiceImpl(
			DeviceModelRepository _repository,
			DeviceInventoryRepository _deviceInventoryRepository,
			InternalService<Manufacturer, Long> _manufacturerService,
			DeviceModelMapper _mapper
	) {
		super(_repository, _repository, _mapper);
		this.deviceInventoryRepository = _deviceInventoryRepository;
		this.manufacturerService = _manufacturerService;
		this.mapper = _mapper;
	}

	@Override
	public DeviceModelDto create(DeviceModelRequest request) {
		validateRequiredFields(request);
		validateFieldsLength(request);

		DeviceModel deviceModel = mapper.toEntity(request);
		buildDeviceModel(deviceModel, request);

		return super.create(request);
	}

	@Override
	public DeviceModelDto update(Long id, DeviceModelRequest request) {
		validateFieldsLength(request);

		DeviceModel entity = super.findById(id)
				.orElseThrow(() -> getNotFound(id, "deviceModel"));

		mapper.updateFromRequest(request, entity);
		buildDeviceModel(entity, request);

		return super.update(entity);
	}

	@Override
	public void delete(Long id) {
		if (deviceInventoryRepository.existsByDeviceModelDeviceModelID(id)) {
			throw new RuntimeException(ASSOCIATED_RECORDS);
		}
		super.delete(id);
	}

	private void buildDeviceModel(DeviceModel deviceModel, DeviceModelRequest request) {
		if (request.getManufacturerID() != null) {
			deviceModel.setManufacturer(manufacturerService.findEntityById(request.getManufacturerID()));
		}
	}

	private void validateRequiredFields(DeviceModelRequest request) {
		requireNotNull(request.getModelName(), FIELD_MODEL_NAME);
		requireNotNull(request.getManufacturerID(), FIELD_MANUFACTURER);
	}

	private void validateFieldsLength(DeviceModelRequest request) {
		validateFieldLength(request.getModelName(), MAX_MODEL_NAME_LENGTH, FIELD_MODEL_NAME);
	}
}
