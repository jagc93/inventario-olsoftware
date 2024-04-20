package com.olsoftware.inventario.model.status;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatusDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String statusID;
	private String statusName;
}
