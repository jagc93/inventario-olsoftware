package com.olsoftware.inventario.model.deviceInventory;

import com.olsoftware.inventario.model.area.AreaDto;
import com.olsoftware.inventario.model.deviceModel.DeviceModelDto;
import com.olsoftware.inventario.model.deviceStatus.DeviceStatusDto;
import com.olsoftware.inventario.model.deviceType.DeviceTypeDto;
import com.olsoftware.inventario.model.user.UserDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceInventoryDto {
	private Long deviceInventoryID;
	private String deviceName;
	private String serialNumber;
	private String comments;
	private UserDto user;
	private AreaDto area;
	private DeviceStatusDto deviceStatus;
	private DeviceTypeDto deviceType;
	private DeviceModelDto deviceModel;
}
