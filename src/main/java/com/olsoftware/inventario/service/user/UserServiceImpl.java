package com.olsoftware.inventario.service.user;

import static com.olsoftware.inventario.constant.MessagesConstant.ALREADY_EXIST;
import static com.olsoftware.inventario.constant.MessagesConstant.ASSOCIATED_RECORDS;
import static com.olsoftware.inventario.constant.UserConstant.MAX_EMAIL_ADDRESS_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_FIRST_NAME_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_ID_NUMBER_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_ID_TYPE_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_LAST_NAME_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_MIDDLE_NAME_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_PASSWORD_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_PHONE_NUMBER_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_SECOND_LAST_NAME_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_USERNAME_LENGTH;
import static com.olsoftware.inventario.exception.GenericException.getNotFound;
import static com.olsoftware.inventario.exception.GenericException.requireNotEmpty;
import static com.olsoftware.inventario.exception.GenericException.requireNotNull;
import static com.olsoftware.inventario.exception.GenericException.validateFieldLength;

import org.springframework.stereotype.Service;

import com.olsoftware.inventario.entity.Role;
import com.olsoftware.inventario.entity.Status;
import com.olsoftware.inventario.entity.User;
import com.olsoftware.inventario.model.user.UserDto;
import com.olsoftware.inventario.model.user.UserMapper;
import com.olsoftware.inventario.model.user.UserRequest;
import com.olsoftware.inventario.repository.DeviceInventoryRepository;
import com.olsoftware.inventario.repository.UserRepository;
import com.olsoftware.inventario.service.GenericServiceImpl;
import com.olsoftware.inventario.service.status.InternalService;

@Service
public class UserServiceImpl
	extends GenericServiceImpl<UserRequest, User, UserDto, Long>
	implements UserService {

	private static final String TABLE_NAME = "user";
	private static final String FIELD_IDENTIFICATION_TYPE = "identificationType";
	private static final String FIELD_IDENTIFICATION_NUMBER = "identificationNumber";
	private static final String FIELD_FIRST_NAME = "firstName";
	private static final String FIELD_MIDDLE_NAME = "middleName";
	private static final String FIELD_LAST_NAME = "lastName";
	private static final String FIELD_SECOND_LAST_NAME = "secondLastName";
	private static final String FIELD_EMAIL_ADDRESS = "emailAddress";
	private static final String FIELD_PHONE_NUMBER = "phoneNumber";
	private static final String FIELD_USERNAME = "username";
	private static final String FIELD_PASSWORD = "password";
	private static final String FIELD_ROLE = "roleID";
	private static final String FIELD_STATUS = "statusID";

	private final UserRepository repository;
	private final InternalService<Status, String> statusService;
	private final InternalService<Role, Long> roleService;
	private final DeviceInventoryRepository deviceInventoryRepository;
	private final UserMapper mapper;

	public UserServiceImpl(
			UserRepository _repository,
			InternalService<Status, String> _statusService,
			InternalService<Role, Long> _roleService,
			DeviceInventoryRepository _deviceInventoryRepository,
			UserMapper _mapper
	) {
		super(_repository, _repository, _mapper);
		this.repository = _repository;
		this.statusService = _statusService;
		this.roleService = _roleService;
		this.deviceInventoryRepository= _deviceInventoryRepository;
		this.mapper = _mapper;
	}

	@Override
	public UserDto create(UserRequest request) {
		validateRequiredFields(request);
		validateFieldsLength(request);
		validateFieldsUnique(request, null);

		User user = mapper.toEntity(request);
		buildUser(user, request);

		return super.createByEntity(user);
	}

	@Override
	public UserDto update(Long id, UserRequest request) {
		validateFieldsLength(request);
		validateFieldsUnique(request, id);

		User user = super.findById(id)
				.orElseThrow(() -> getNotFound(id, TABLE_NAME));

		mapper.updateFromRequest(request, user);
		buildUser(user, request);

		return super.update(user);
	}

	@Override
	public void delete(Long id) {
		if (deviceInventoryRepository.existsByUserUserID(id)) {
			throw new RuntimeException(ASSOCIATED_RECORDS);
		}
		super.delete(id);
	}

	private void buildUser(User user, UserRequest request) {
		if (request.getStatusID() != null) {
			user.setStatus(statusService.findEntityById(request.getStatusID()));
		}

		if (request.getRoleID() != null) {
			user.setRole(roleService.findEntityById(request.getRoleID()));
		}
	}

	private void validateRequiredFields(UserRequest request) {
		requireNotEmpty(request.getIdentificationType(), FIELD_IDENTIFICATION_TYPE);
		requireNotEmpty(request.getIdentificationNumber(), FIELD_IDENTIFICATION_NUMBER);
		requireNotEmpty(request.getFirstName(), FIELD_FIRST_NAME);
		requireNotEmpty(request.getLastName(), FIELD_LAST_NAME);
		requireNotEmpty(request.getEmailAddress(), FIELD_EMAIL_ADDRESS);
		requireNotEmpty(request.getUsername(), FIELD_USERNAME);
		requireNotEmpty(request.getPassword(), FIELD_PASSWORD);
		requireNotNull(request.getRoleID(), FIELD_ROLE);
		requireNotNull(request.getStatusID(), FIELD_STATUS);
	}

	private void validateFieldsLength(UserRequest request) {
		validateFieldLength(request.getIdentificationType(), MAX_ID_TYPE_LENGTH, FIELD_IDENTIFICATION_TYPE);
		validateFieldLength(request.getIdentificationNumber(), MAX_ID_NUMBER_LENGTH, FIELD_IDENTIFICATION_NUMBER);
		validateFieldLength(request.getFirstName(), MAX_FIRST_NAME_LENGTH, FIELD_FIRST_NAME);
		validateFieldLength(request.getMiddleName(), MAX_MIDDLE_NAME_LENGTH, FIELD_MIDDLE_NAME);
		validateFieldLength(request.getLastName(), MAX_LAST_NAME_LENGTH, FIELD_LAST_NAME);
		validateFieldLength(request.getSecondLastName(), MAX_SECOND_LAST_NAME_LENGTH, FIELD_SECOND_LAST_NAME);
		validateFieldLength(request.getEmailAddress(), MAX_EMAIL_ADDRESS_LENGTH, FIELD_EMAIL_ADDRESS);
		validateFieldLength(request.getPhoneNumber(), MAX_PHONE_NUMBER_LENGTH, FIELD_PHONE_NUMBER);
		validateFieldLength(request.getUsername(), MAX_USERNAME_LENGTH, FIELD_USERNAME);
		validateFieldLength(request.getPassword(), MAX_PASSWORD_LENGTH, FIELD_PASSWORD);
	}

	private void validateFieldsUnique(UserRequest request, Long id) {
		boolean existBy = validateEmailAddressUnique(request.getEmailAddress(), id);
		String fileName = FIELD_EMAIL_ADDRESS;
		String value = request.getEmailAddress();

		if (!existBy && validateUsernameUnique(request.getUsername(), id)) {
			existBy = true;
			fileName = FIELD_USERNAME;
			value = request.getUsername();
		}

		if (existBy) {
			throw new IllegalArgumentException(String.format(ALREADY_EXIST, TABLE_NAME, fileName, value));
		}
	}

	private boolean validateEmailAddressUnique(String emailAddress, Long id) {
		if (id != null) {
			return repository.existsByEmailAddressIgnoreCaseAndUserIDNot(emailAddress, id);
		}

		return repository.existsByEmailAddressIgnoreCase(emailAddress);
	}

	private boolean validateUsernameUnique(String username, Long id) {
		if (id != null) {
			return repository.existsByUsernameIgnoreCaseAndUserIDNot(username, id);
		}

		return repository.existsByUsernameIgnoreCase(username);
	}
}
