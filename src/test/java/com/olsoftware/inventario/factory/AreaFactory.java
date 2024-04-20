package com.olsoftware.inventario.factory;

import static com.olsoftware.inventario.constant.AreaConstant.MAX_AREA_NAME_LENGTH;

import org.springframework.stereotype.Component;

import com.olsoftware.inventario.factory.base.BaseFactory;
import com.olsoftware.inventario.factory.base.BaseFactoryImpl;
import com.olsoftware.inventario.model.area.AreaDto;
import com.olsoftware.inventario.model.area.AreaRequest;
import com.olsoftware.inventario.service.area.AreaService;

@Component
public class AreaFactory
	extends BaseFactoryImpl<AreaRequest, AreaDto, Long, AreaService>
	implements BaseFactory<AreaRequest, AreaDto> {

	protected AreaFactory(AreaService _service) {
		super(_service);
	}

	@Override
	public AreaDto create() {
		return super.create(request());
	}

	public AreaDto create(AreaRequest request) {
		return super.create(request);
	}

	@Override
	public AreaRequest request() {
		return new AreaRequest(
				faker.lorem().characters(MAX_AREA_NAME_LENGTH)
		);
	}
}
