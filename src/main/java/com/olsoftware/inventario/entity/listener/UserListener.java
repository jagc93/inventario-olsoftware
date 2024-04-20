package com.olsoftware.inventario.entity.listener;

import java.util.Date;

import com.olsoftware.inventario.entity.User;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserListener {

	@PrePersist
	public void beforeCreate(User entity) {
		entity.setCreationDate(new Date());
	}

	@PreUpdate
	public void beforeUpdate(User entity) {
		entity.setModificationDate(new Date());
	}
}
