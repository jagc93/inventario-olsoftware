package com.olsoftware.inventario.service.area;

import static com.olsoftware.inventario.constant.AreaConstant.MAX_AREA_NAME_LENGTH;
import static com.olsoftware.inventario.exception.GenericException.getNotFound;
import static com.olsoftware.inventario.exception.GenericException.requireNotNull;
import static com.olsoftware.inventario.exception.GenericException.validateFieldLength;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.olsoftware.inventario.entity.Area;
import com.olsoftware.inventario.model.area.AreaDto;
import com.olsoftware.inventario.model.area.AreaMapper;
import com.olsoftware.inventario.model.area.AreaRequest;
import com.olsoftware.inventario.repository.AreaRepository;
import com.olsoftware.inventario.service.GenericServiceImpl;
import com.olsoftware.inventario.service.status.InternalService;

@Service
public class AreaServiceImpl
	extends GenericServiceImpl<AreaRequest, Area, AreaDto, Long>
	implements AreaService, InternalService<Area, Long> {

	private static final String FIELD_AREA_NAME = "areaName";

	public AreaServiceImpl(
			AreaRepository _repository,
			AreaMapper _mapper
	) {
		super(_repository, _repository, _mapper);
	}

	@Override
	@Cacheable("getAreas")
	public Page<AreaDto> index(Pageable pageable, String search) {
		return super.index(pageable, search);
	}

	@Override
	@Cacheable(value = "getArea", key = "#id")
	public AreaDto show(Long id) {
		return super.show(id);
	}

	@Override
	@Cacheable(value = "findAreaById", key = "#id")
	public Area findEntityById(Long id) {
		return super.findById(id)
				.orElseThrow(() -> getNotFound(id, "Area"));
	}

	@Override
	public AreaDto create(AreaRequest request) {
		validateRequiredFields(request);
		validateFieldsLength(request);
		return super.create(request);
	}

	@Override
	@CacheEvict(value = "getAreas", allEntries = true)
	public AreaDto update(Long id, AreaRequest request) {
		validateFieldsLength(request);
		return super.update(id, request);
	}

	@Override
	@CacheEvict(value = { "getArea", "findAreaById" }, key = "#id")
	public void delete(Long id) {
		super.delete(id);
	}

	private void validateRequiredFields(AreaRequest request) {
		requireNotNull(request.getAreaName(), FIELD_AREA_NAME);
	}

	private void validateFieldsLength(AreaRequest request) {
		validateFieldLength(request.getAreaName(), MAX_AREA_NAME_LENGTH, FIELD_AREA_NAME);
	}
}
