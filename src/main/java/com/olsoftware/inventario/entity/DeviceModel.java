package com.olsoftware.inventario.entity;

import static com.olsoftware.inventario.constant.DeviceModelConstant.MAX_MODEL_NAME_LENGTH;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MODELOS_DISPOSITIVOS")
@SequenceGenerator(sequenceName = "sec_modelos_dispositivos", name = "deviceModelID", allocationSize = 1)
public class DeviceModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "deviceModelID")
	@Column(name = "MODELO_ID", unique = true, nullable = false)
	private Long deviceModelID;

	@Column(name = "MODELO", nullable = false, length = MAX_MODEL_NAME_LENGTH)
	private String modelName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FABRICANTE_ID", nullable = false)
	private Manufacturer manufacturer;
}
