package com.olsoftware.inventario.model.deviceInventory;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.olsoftware.inventario.config.MyMapperConfig;
import com.olsoftware.inventario.entity.DeviceInventory;
import com.olsoftware.inventario.model.GenericMapper;

@Mapper(config = MyMapperConfig.class)
public interface DeviceInventoryMapper
	extends GenericMapper<DeviceInventoryRequest, DeviceInventory, DeviceInventoryDto> {

	@Override
	@Mapping(source = "userID", target = "user.userID")
	@Mapping(source = "areaID", target = "area.areaID")
	@Mapping(source = "deviceTypeID", target = "deviceType.deviceTypeID")
	@Mapping(source = "deviceModelID", target = "deviceModel.deviceModelID")
	DeviceInventory toEntity(DeviceInventoryRequest request);
}
