package com.olsoftware.inventario.model.deviceModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceModelRequest {
	private String modelName;
	private Long manufacturerID;
}
