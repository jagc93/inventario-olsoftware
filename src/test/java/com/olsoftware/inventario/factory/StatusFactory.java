package com.olsoftware.inventario.factory;

import static com.olsoftware.inventario.constant.StatusConstant.*;

import org.springframework.stereotype.Component;

import com.olsoftware.inventario.factory.base.BaseFactory;
import com.olsoftware.inventario.factory.base.BaseFactoryImpl;
import com.olsoftware.inventario.model.status.StatusDto;
import com.olsoftware.inventario.model.status.StatusRequest;
import com.olsoftware.inventario.service.status.StatusService;

@Component
public class StatusFactory
	extends BaseFactoryImpl<StatusRequest, StatusDto, String, StatusService>
	implements BaseFactory<StatusRequest, StatusDto>{

	protected StatusFactory(StatusService _service) {
		super(_service);
	}

	@Override
	public StatusDto create() {
		return super.create(request());
	}

	public StatusDto create(StatusRequest request) {
		return super.create(request);
	}

	@Override
	public StatusRequest request() {
		return new StatusRequest(
				faker.lorem().characters(MAX_STATUS_ID_LENGTH),
				faker.lorem().characters(MAX_STATUS_NAME_LENGTH)
		);
	}
}
