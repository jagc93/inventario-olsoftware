package com.olsoftware.inventario.entity;

import static com.olsoftware.inventario.constant.DeviceInventoryConstant.MAX_COMMENTS_LENGTH;
import static com.olsoftware.inventario.constant.DeviceInventoryConstant.MAX_DEVICE_NAME_LENGTH;
import static com.olsoftware.inventario.constant.DeviceInventoryConstant.MAX_SERIAL_NUMBER_LENGTH;

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
@Table(name = "INVENTARIO_DISPOSITIVOS")
@SequenceGenerator(sequenceName = "sec_inventario_dispositivos", name = "deviceInventoryID", allocationSize = 1)
public class DeviceInventory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "deviceInventoryID")
	@Column(name = "INVENTARIO_ID", unique = true, nullable = false)
	private Long deviceInventoryID;

	@Column(name = "NOMBRE_DISPOSITIVO", nullable = false, length = MAX_DEVICE_NAME_LENGTH)
	private String deviceName;

	@Column(name = "NUMERO_SERIE", nullable = false, length = MAX_SERIAL_NUMBER_LENGTH)
	private String serialNumber;
	
	@Column(name = "COMENTARIOS", length = MAX_COMMENTS_LENGTH)
	private String comments;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USUARIO_ID", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AREA_ID", nullable = false)
	private Area area;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ESTADO_DISPOSITIVO_ID", nullable = false)
	private DeviceStatus deviceStatus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPO_DISPOSITIVO_ID", nullable = false)
	private DeviceType deviceType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MODELO_ID", nullable = false)
	private DeviceModel deviceModel;
}
