package com.olsoftware.inventario.entity;

import static com.olsoftware.inventario.constant.ManufacturerConstant.MAX_MANUFACTURER_NAME_LENGTH;

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
@Table(name = "FABRICANTES")
@SequenceGenerator(sequenceName = "sec_fabricantes", name = "manufacturerID", allocationSize = 1)
public class Manufacturer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "manufacturerID")
	@Column(name = "FABRICANTE_ID", unique = true, nullable = false)
	private Long manufacturerID;

	@Column(name = "FABRICANTE", nullable = false, length = MAX_MANUFACTURER_NAME_LENGTH)
	private String manufacturerName;
}
