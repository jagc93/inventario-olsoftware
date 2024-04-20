package com.olsoftware.inventario.service.manufacturer;

import static com.olsoftware.inventario.constant.ManufacturerConstant.MAX_MANUFACTURER_NAME_LENGTH;
import static com.olsoftware.inventario.constant.MessagesConstant.ASSOCIATED_RECORDS;
import static com.olsoftware.inventario.exception.GenericException.getNotFound;
import static com.olsoftware.inventario.exception.GenericException.requireNotNull;
import static com.olsoftware.inventario.exception.GenericException.validateFieldLength;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.olsoftware.inventario.entity.Manufacturer;
import com.olsoftware.inventario.model.manufacturer.ManufacturerDto;
import com.olsoftware.inventario.model.manufacturer.ManufacturerMapper;
import com.olsoftware.inventario.model.manufacturer.ManufacturerRequest;
import com.olsoftware.inventario.repository.DeviceModelRepository;
import com.olsoftware.inventario.repository.ManufacturerRepository;
import com.olsoftware.inventario.service.GenericServiceImpl;
import com.olsoftware.inventario.service.status.InternalService;

@Service
public class ManufacturerServiceImpl
	extends GenericServiceImpl<ManufacturerRequest, Manufacturer, ManufacturerDto, Long>
	implements ManufacturerService, InternalService<Manufacturer, Long> {

	private static final String FIELD_MANUFACTURER_NAME = "manufacturerName";
	private final DeviceModelRepository deviceModelRepository;

	public ManufacturerServiceImpl(
			ManufacturerRepository _repository,
			DeviceModelRepository _deviceModelRepository,
			ManufacturerMapper _mapper) {
		super(_repository, _repository, _mapper);
		this.deviceModelRepository = _deviceModelRepository;
	}

	@Override
	@Cacheable("getManufacturers")
	public Page<ManufacturerDto> index(Pageable pageable, String search) {
		return super.index(pageable, search);
	}

	@Override
	@Cacheable(value = "getManufacturer", key = "#id")
	public ManufacturerDto show(Long id) {
		return super.show(id);
	}

	@Override
	@Cacheable(value = "findManufacturerById", key = "#id")
	public Manufacturer findEntityById(Long id) {
		return super.findById(id)
				.orElseThrow(() -> getNotFound(id, "DeviceType"));
	}

	@Override
	public ManufacturerDto create(ManufacturerRequest request) {
		validateRequiredFields(request);
		validateFieldsLength(request);
		return super.create(request);
	}

	@Override
	@CacheEvict(value = "getManufacturers", allEntries = true)
	public ManufacturerDto update(Long id, ManufacturerRequest request) {
		validateFieldsLength(request);
		return super.update(id, request);
	}

	@Override
	@CacheEvict(value = { "getManufacturer", "findManufacturerById" }, key = "#id")
	public void delete(Long id) {
		if (deviceModelRepository.existsByManufacturerManufacturerID(id)) {
			throw new RuntimeException(ASSOCIATED_RECORDS);
		}
		super.delete(id);
	}

	private void validateRequiredFields(ManufacturerRequest request) {
		requireNotNull(request.getManufacturerName(), FIELD_MANUFACTURER_NAME);
	}

	private void validateFieldsLength(ManufacturerRequest request) {
		validateFieldLength(request.getManufacturerName(), MAX_MANUFACTURER_NAME_LENGTH, FIELD_MANUFACTURER_NAME);
	}
}
