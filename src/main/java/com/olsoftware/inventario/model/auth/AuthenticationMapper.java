package com.olsoftware.inventario.model.auth;

import org.mapstruct.Mapper;

import com.olsoftware.inventario.config.MyMapperConfig;
import com.olsoftware.inventario.entity.User;

@Mapper(config = MyMapperConfig.class)
public interface AuthenticationMapper {

	AuthenticationDto toDto(User entity);
}
