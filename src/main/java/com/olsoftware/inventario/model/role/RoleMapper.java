package com.olsoftware.inventario.model.role;

import org.mapstruct.Mapper;

import com.olsoftware.inventario.config.MyMapperConfig;
import com.olsoftware.inventario.entity.Role;
import com.olsoftware.inventario.model.GenericMapper;

@Mapper(config = MyMapperConfig.class)
public interface RoleMapper
	extends GenericMapper<RoleRequest, Role, RoleDto> {

}
