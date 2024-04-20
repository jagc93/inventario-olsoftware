package com.olsoftware.inventario.model.deviceStatus;

import org.mapstruct.Mapper;

import com.olsoftware.inventario.config.MyMapperConfig;
import com.olsoftware.inventario.entity.DeviceStatus;
import com.olsoftware.inventario.model.GenericMapper;

@Mapper(config = MyMapperConfig.class)
public interface DeviceStatusMapper
	extends GenericMapper<DeviceStatusRequest, DeviceStatus, DeviceStatusDto> {

}
