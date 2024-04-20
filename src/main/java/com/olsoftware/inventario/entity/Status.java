package com.olsoftware.inventario.entity;

import static com.olsoftware.inventario.constant.StatusConstant.MAX_STATUS_ID_LENGTH;
import static com.olsoftware.inventario.constant.StatusConstant.MAX_STATUS_NAME_LENGTH;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ESTADOS")
@NoArgsConstructor
public class Status implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ESTADO_ID", unique = true, nullable = false, length = MAX_STATUS_ID_LENGTH)
	private String statusID;

	@Column(name = "ESTADO", unique = true, nullable = false, length = MAX_STATUS_NAME_LENGTH)
	private String statusName;
}
