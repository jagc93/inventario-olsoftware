package com.olsoftware.inventario.factory;

import static com.olsoftware.inventario.constant.RoleConstant.*;

import org.springframework.stereotype.Component;

import com.olsoftware.inventario.factory.base.BaseFactory;
import com.olsoftware.inventario.factory.base.BaseFactoryImpl;
import com.olsoftware.inventario.model.role.RoleDto;
import com.olsoftware.inventario.model.role.RoleRequest;
import com.olsoftware.inventario.service.role.RoleService;

@Component
public class RoleFactory
	extends BaseFactoryImpl<RoleRequest, RoleDto, Long, RoleService>
	implements BaseFactory<RoleRequest, RoleDto> {

	protected RoleFactory(RoleService _service) {
		super(_service);
	}

	@Override
	public RoleDto create() {
		return super.create(request());
	}

	public RoleDto create(RoleRequest request) {
		return super.create(request);
	}

	@Override
	public RoleRequest request() {
		return new RoleRequest(
				faker.lorem().characters(MAX_ROLE_NAME_LENGTH),
				faker.lorem().characters(MAX_ROLE_CODE_LENGTH)
		);
	}
}
