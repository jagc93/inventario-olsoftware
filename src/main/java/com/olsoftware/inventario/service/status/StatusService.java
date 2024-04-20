package com.olsoftware.inventario.service.status;

import com.olsoftware.inventario.entity.Status;
import com.olsoftware.inventario.model.status.StatusDto;
import com.olsoftware.inventario.model.status.StatusRequest;
import com.olsoftware.inventario.service.GenericService;

public interface StatusService
	extends GenericService<StatusDto, StatusRequest, String> {

	public Status findEntityById(String id);
}
