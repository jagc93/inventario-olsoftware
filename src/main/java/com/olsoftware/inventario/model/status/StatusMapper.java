package com.olsoftware.inventario.model.status;

import org.mapstruct.Mapper;

import com.olsoftware.inventario.config.MyMapperConfig;
import com.olsoftware.inventario.entity.Status;
import com.olsoftware.inventario.model.GenericMapper;

@Mapper(config = MyMapperConfig.class)
public interface StatusMapper
	extends GenericMapper<StatusRequest, Status, StatusDto> {

}
