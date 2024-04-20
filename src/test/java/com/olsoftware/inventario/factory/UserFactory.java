package com.olsoftware.inventario.factory;

import static com.olsoftware.inventario.constant.UserConstant.*;

import org.springframework.stereotype.Component;

import com.olsoftware.inventario.factory.base.BaseFactory;
import com.olsoftware.inventario.factory.base.BaseFactoryImpl;
import com.olsoftware.inventario.model.user.UserDto;
import com.olsoftware.inventario.model.user.UserRequest;
import com.olsoftware.inventario.service.user.UserService;

@Component
public class UserFactory
	extends BaseFactoryImpl<UserRequest, UserDto, Long, UserService>
	implements BaseFactory<UserRequest, UserDto> {

	private final RoleFactory roleFactory;
	private final StatusFactory statusFactory;

	protected UserFactory(
			UserService _service,
			RoleFactory _roleFactory,
			StatusFactory _statusFactory
	) {
		super(_service);
		this.roleFactory = _roleFactory;
		this.statusFactory = _statusFactory;
	}

	@Override
	public UserDto create() {
		return super.create(request());
	}

	public UserDto create(UserRequest request) {
		return super.create(request);
	}

	@Override
	public UserRequest request() {
		return new UserRequest(
				faker.lorem().characters(MAX_ID_TYPE_LENGTH),
				String.valueOf(faker.random().nextInt(1, MAX_ID_NUMBER_LENGTH)),
				faker.lorem().characters(MAX_FIRST_NAME_LENGTH),
				faker.lorem().characters(MAX_MIDDLE_NAME_LENGTH),
				faker.lorem().characters(MAX_LAST_NAME_LENGTH),
				faker.lorem().characters(MAX_SECOND_LAST_NAME_LENGTH),
				faker.lorem().characters(MAX_EMAIL_ADDRESS_LENGTH),
				String.valueOf(faker.random().nextInt(1, MAX_PHONE_NUMBER_LENGTH)),
				faker.lorem().characters(MAX_USERNAME_LENGTH),
				faker.lorem().characters(MAX_PASSWORD_LENGTH),
				roleFactory.create().getRoleID(),
				statusFactory.create().getStatusID()
		);
	}
}
