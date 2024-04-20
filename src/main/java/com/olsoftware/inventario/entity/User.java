package com.olsoftware.inventario.entity;

import static com.olsoftware.inventario.constant.UserConstant.MAX_EMAIL_ADDRESS_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_FIRST_NAME_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_ID_NUMBER_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_ID_TYPE_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_LAST_NAME_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_MIDDLE_NAME_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_PASSWORD_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_PHONE_NUMBER_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_SECOND_LAST_NAME_LENGTH;
import static com.olsoftware.inventario.constant.UserConstant.MAX_USERNAME_LENGTH;

import java.util.Date;

import com.olsoftware.inventario.entity.listener.UserListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USUARIOS")
@SequenceGenerator(sequenceName = "sec_usuarios", name = "userID", allocationSize = 1)
@EntityListeners({ UserListener.class })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "userID")
	@Column(name = "USUARIO_ID", unique = true, nullable = false)
	private Long userID;

	@Column(name = "TIPO_IDENTIFICACION", nullable = false, length = MAX_ID_TYPE_LENGTH)
    private String identificationType;

	@Column(name = "NUMERO_IDENTIFICACION", nullable = false, length = MAX_ID_NUMBER_LENGTH)
    private String identificationNumber;

	@Column(name = "PRIMER_NOMBRE", nullable = false, length = MAX_FIRST_NAME_LENGTH)
    private String firstName;

	@Column(name = "SEGUNDO_NOMBRE", length = MAX_MIDDLE_NAME_LENGTH)
    private String middleName;

	@Column(name = "PRIMER_APELLIDO", nullable = false, length = MAX_LAST_NAME_LENGTH)
    private String lastName;

	@Column(name = "SEGUNDO_APELLIDO", length = MAX_SECOND_LAST_NAME_LENGTH)
    private String secondLastName;

	@Column(name = "CORREO_ELECTRONICO", unique = true, nullable = false, length = MAX_EMAIL_ADDRESS_LENGTH)
    private String emailAddress;

	@Column(name = "TELEFONO", length = MAX_PHONE_NUMBER_LENGTH)
    private String phoneNumber;

	@Column(name = "NOMBRE_USUARIO", unique = true, nullable = false, length = MAX_USERNAME_LENGTH)
    private String username;

	@Column(name = "CONTRASENA", nullable = false, length = MAX_PASSWORD_LENGTH)
    private String password;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROL_ID", nullable = false)
    private Role role;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ESTADO_ID", nullable = false)
	private Status status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_CREACION", nullable = false)
	private Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_MODIFICACION")
	private Date modificationDate;
}
