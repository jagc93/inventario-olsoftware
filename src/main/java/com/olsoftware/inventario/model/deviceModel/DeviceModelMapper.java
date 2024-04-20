package com.olsoftware.inventario.model.deviceModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.olsoftware.inventario.config.MyMapperConfig;
import com.olsoftware.inventario.entity.DeviceModel;
import com.olsoftware.inventario.model.GenericMapper;

@Mapper(config = MyMapperConfig.class)
public interface DeviceModelMapper
	extends GenericMapper<DeviceModelRequest, DeviceModel, DeviceModelDto> {

	@Override
	@Mapping(source = "manufacturerID", target = "manufacturer.manufacturerID")
	DeviceModel toEntity(DeviceModelRequest request);
}
