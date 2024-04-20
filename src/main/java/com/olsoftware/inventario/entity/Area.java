package com.olsoftware.inventario.entity;

import static com.olsoftware.inventario.constant.AreaConstant.MAX_AREA_NAME_LENGTH;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "AREAS")
@SequenceGenerator(sequenceName = "sec_areas", name = "areaID", allocationSize = 1)
@NoArgsConstructor
public class Area implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "areaID")
	@Column(name = "AREA_ID", unique = true, nullable = false)
	private Long areaID;

	@Column(name = "AREA", nullable = false, length = MAX_AREA_NAME_LENGTH)
	private String areaName;
}
