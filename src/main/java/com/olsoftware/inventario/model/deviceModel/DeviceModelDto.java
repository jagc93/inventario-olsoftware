package com.olsoftware.inventario.model.deviceModel;

import com.olsoftware.inventario.model.manufacturer.ManufacturerDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceModelDto {
	private Long deviceModelID;
	private String modelName;
	private ManufacturerDto manufacturer;
}
