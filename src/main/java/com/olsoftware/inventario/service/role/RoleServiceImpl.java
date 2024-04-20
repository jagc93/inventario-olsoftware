package com.olsoftware.inventario.service.role;

import static com.olsoftware.inventario.constant.MessagesConstant.ALREADY_EXIST;
import static com.olsoftware.inventario.constant.MessagesConstant.ASSOCIATED_RECORDS;
import static com.olsoftware.inventario.constant.RoleConstant.MAX_ROLE_CODE_LENGTH;
import static com.olsoftware.inventario.constant.RoleConstant.MAX_ROLE_NAME_LENGTH;
import static com.olsoftware.inventario.exception.GenericException.getNotFound;
import static com.olsoftware.inventario.exception.GenericException.requireNotNull;
import static com.olsoftware.inventario.exception.GenericException.validateFieldLength;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.olsoftware.inventario.entity.Role;
import com.olsoftware.inventario.model.role.RoleDto;
import com.olsoftware.inventario.model.role.RoleMapper;
import com.olsoftware.inventario.model.role.RoleRequest;
import com.olsoftware.inventario.repository.RoleRepository;
import com.olsoftware.inventario.repository.UserRepository;
import com.olsoftware.inventario.service.GenericServiceImpl;
import com.olsoftware.inventario.service.status.InternalService;

@Service
public class RoleServiceImpl
	extends GenericServiceImpl<RoleRequest, Role, RoleDto, Long>
	implements RoleService, InternalService<Role, Long> {

	private static final String TABLE_NAME = "role";
	private static final String FIELD_ROLE_NAME = "roleName";
	private static final String FIELD_ROLE_CODE = "roleCode";

	private final RoleRepository repository;
	private final UserRepository userRepository;

	public RoleServiceImpl(
			RoleRepository _repository,
			UserRepository _userRepository,
			RoleMapper _mapper
	) {
		super(_repository, _repository, _mapper);
		this.repository = _repository;
		this.userRepository = _userRepository;
	}

	@Override
	@Cacheable("getRoles")
	public Page<RoleDto> index(Pageable pageable, String search) {
		return super.index(pageable, search);
	}

	@Override
	@Cacheable(value = "getRole", key = "#id")
	public RoleDto show(Long id) {
		return super.show(id);
	}

	@Override
	@Cacheable(value = "findRoleById", key = "#id")
	public Role findEntityById(Long id) {
		return super.findById(id)
				.orElseThrow(() -> getNotFound(id, "Role"));
	}

	@Override
	public RoleDto create(RoleRequest request) {
		validateRequiredFields(request);
		validateFieldsLength(request);
		validateFieldsUnique(request);
		return super.create(request);
	}

	@Override
	@CacheEvict(value = "getRoles", allEntries = true)
	public RoleDto update(Long id, RoleRequest request) {
		validateFieldsLength(request);
		validateFieldsUnique(request);
		return super.update(id, request);
	}

	@Override
	@CacheEvict(value = { "getRole", "findRoleById" }, key = "#id")
	public void delete(Long id) {
		if (userRepository.existsByRoleRoleID(id)) {
			throw new RuntimeException(ASSOCIATED_RECORDS);
		}
		super.delete(id);
	}

	private void validateRequiredFields(RoleRequest request) {
		requireNotNull(request.getRoleName(), FIELD_ROLE_NAME);
		requireNotNull(request.getRoleCode(), FIELD_ROLE_CODE);
	}

	private void validateFieldsLength(RoleRequest request) {
		validateFieldLength(request.getRoleName(), MAX_ROLE_NAME_LENGTH, FIELD_ROLE_NAME);
		validateFieldLength(request.getRoleCode(), MAX_ROLE_CODE_LENGTH, FIELD_ROLE_CODE);
	}

	private void validateFieldsUnique(RoleRequest request) {
		boolean existBy = repository.existsByRoleNameIgnoreCase(request.getRoleName());
		String fileName = FIELD_ROLE_NAME;
		String value = request.getRoleName();

		if (!existBy && repository.existsByRoleCodeIgnoreCase(request.getRoleCode())) {
			existBy = true;
			fileName = FIELD_ROLE_CODE;
			value = request.getRoleCode();
		}

		if (existBy) {
			throw new IllegalArgumentException(String.format(ALREADY_EXIST, TABLE_NAME, fileName, value));
		}
	}
}
