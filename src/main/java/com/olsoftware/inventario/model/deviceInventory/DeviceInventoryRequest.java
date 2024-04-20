package com.olsoftware.inventario.model.deviceInventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInventoryRequest {
	private String deviceName;
	private String serialNumber;
	private String comments;
	private Long userID;
	private Long areaID;
	private Long deviceStatusID;
	private Long deviceTypeID;
	private Long deviceModelID;
}
