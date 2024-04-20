package com.olsoftware.inventario.model.deviceType;

import org.mapstruct.Mapper;

import com.olsoftware.inventario.config.MyMapperConfig;
import com.olsoftware.inventario.entity.DeviceType;
import com.olsoftware.inventario.model.GenericMapper;

@Mapper(config = MyMapperConfig.class)
public interface DeviceTypeMapper
	extends GenericMapper<DeviceTypeRequest, DeviceType, DeviceTypeDto> {

}
