package com.olsoftware.inventario.entity;

import static com.olsoftware.inventario.constant.DeviceTypeConstant.MAX_DEVICE_TYPE_LENGTH;

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
@Table(name = "TIPOS_DISPOSITIVOS")
@SequenceGenerator(sequenceName = "sec_tipos_dispositivos", name = "deviceTypeID", allocationSize = 1)
public class DeviceType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "deviceTypeID")
	@Column(name = "TIPO_DISPOSITIVO_ID", unique = true, nullable = false)
	private Long deviceTypeID;

	@Column(name = "TIPO_DISPOSITIVO", nullable = false, length = MAX_DEVICE_TYPE_LENGTH)
	private String deviceType;
}
