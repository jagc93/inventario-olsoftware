package com.olsoftware.inventario.entity;

import static com.olsoftware.inventario.constant.DeviceStatusConstant.MAX_DEVICE_STATUS_NAME_LENGTH;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ESTADOS_DISPOSITIVOS")
@SequenceGenerator(sequenceName = "sec_estados_dispositivos", name = "deviceStatusID", allocationSize = 1)
public class DeviceStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "deviceStatusID")
	@Column(name = "ESTADO_DISPOSITIVO_ID", unique = true, nullable = false)
	private Long deviceStatusID;

	@Column(name = "ESTADO_DISPOSITIVO", nullable = false, length = MAX_DEVICE_STATUS_NAME_LENGTH)
	private String deviceStatusName;
}
