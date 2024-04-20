package com.olsoftware.inventario.entity;

import static com.olsoftware.inventario.constant.RoleConstant.MAX_ROLE_CODE_LENGTH;
import static com.olsoftware.inventario.constant.RoleConstant.MAX_ROLE_NAME_LENGTH;

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
@Table(name = "ROLES")
@SequenceGenerator(sequenceName = "sec_roles", name = "roleID", allocationSize = 1)
@NoArgsConstructor
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "roleID")
	@Column(name = "ROL_ID", unique = true, nullable = false)
	private Long roleID;

	@Column(name = "ROL", unique = true, nullable = false, length = MAX_ROLE_NAME_LENGTH)
	private String roleName;

	@Column(name = "CODIGO", unique = true, nullable = false, length = MAX_ROLE_CODE_LENGTH)
	private String roleCode;
}
