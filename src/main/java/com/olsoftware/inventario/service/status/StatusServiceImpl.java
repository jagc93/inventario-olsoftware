package com.olsoftware.inventario.service.status;

import static com.olsoftware.inventario.constant.MessagesConstant.ALREADY_EXIST;
import static com.olsoftware.inventario.constant.MessagesConstant.ASSOCIATED_RECORDS;
import static com.olsoftware.inventario.constant.StatusConstant.MAX_STATUS_ID_LENGTH;
import static com.olsoftware.inventario.constant.StatusConstant.MAX_STATUS_NAME_LENGTH;
import static com.olsoftware.inventario.exception.GenericException.getNotFound;
import static com.olsoftware.inventario.exception.GenericException.requireNotNull;
import static com.olsoftware.inventario.exception.GenericException.validateFieldLength;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.olsoftware.inventario.entity.Status;
import com.olsoftware.inventario.model.status.StatusDto;
import com.olsoftware.inventario.model.status.StatusMapper;
import com.olsoftware.inventario.model.status.StatusRequest;
import com.olsoftware.inventario.repository.StatusRepository;
import com.olsoftware.inventario.repository.UserRepository;
import com.olsoftware.inventario.service.GenericServiceImpl;

@Service
public class StatusServiceImpl
	extends GenericServiceImpl<StatusRequest, Status, StatusDto, String>
	implements StatusService, InternalService<Status, String> {

	private static final String TABLE_NAME = "status";
	private static final String FIELD_STATUS_ID = "statusID";
	private static final String FIELD_STATUS_NAME = "statusName";

	private final StatusRepository repository;
	private final UserRepository userRepository;

	public StatusServiceImpl(
			StatusRepository _repository,
			UserRepository _userRepository,
			StatusMapper _mapper
	) {
		super(_repository, _repository, _mapper);
		this.repository = _repository;
		this.userRepository = _userRepository;
	}

	@Override
	@Cacheable("getStatuses")
	public Page<StatusDto> index(Pageable pageable, String search) {
		return super.index(pageable, search);
	}

	@Override
	@Cacheable(value = "getStatus", key = "#id")
	public StatusDto show(String id) {
		return super.show(id);
	}

	@Override
	@Cacheable(value = "findStatusById", key = "#id")
	public Status findEntityById(String id) {
		return super.findById(id)
				.orElseThrow(() -> getNotFound(id, "Status"));
	}

	@Override
	public StatusDto create(StatusRequest request) {
		validateRequiredFields(request);
		validateFieldsLength(request);
		validateFieldsUnique(request);
		return super.create(request);
	}

	@Override
	@CacheEvict(value = "getStatuses", allEntries = true)
	public StatusDto update(String id, StatusRequest request) {
		request.setStatusID(null);
		validateFieldsLength(request);
		validateFieldsUnique(request);
		return super.update(id, request);
	}

	@Override
	@CacheEvict(value = { "getStatus", "findStatusById" }, key = "#id")
	public void delete(String id) {
		if (userRepository.existsByStatusStatusID(id)) {
			throw new RuntimeException(ASSOCIATED_RECORDS);
		}
		super.delete(id);
	}

	private void validateRequiredFields(StatusRequest request) {
		requireNotNull(request.getStatusID(), FIELD_STATUS_ID);
		requireNotNull(request.getStatusName(), FIELD_STATUS_NAME);
	}

	private void validateFieldsLength(StatusRequest request) {
		validateFieldLength(request.getStatusID(), MAX_STATUS_ID_LENGTH, FIELD_STATUS_ID);
		validateFieldLength(request.getStatusName(), MAX_STATUS_NAME_LENGTH, FIELD_STATUS_NAME);
	}

	private void validateFieldsUnique(StatusRequest request) {
		boolean existBy = repository.existsByStatusIDIgnoreCase(request.getStatusID());
		String fileName = FIELD_STATUS_ID;
		String value = request.getStatusID();
		
		if (!existBy && repository.existsByStatusNameIgnoreCase(request.getStatusName())) {
			existBy = true;
			fileName = FIELD_STATUS_NAME;
			value = request.getStatusName();
		}
		
		if (existBy) {
			throw new IllegalArgumentException(String.format(ALREADY_EXIST, TABLE_NAME, fileName, value));
		}
	}
}
