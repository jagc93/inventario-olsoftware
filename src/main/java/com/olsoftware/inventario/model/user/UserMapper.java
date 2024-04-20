package com.olsoftware.inventario.model.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.olsoftware.inventario.config.MyMapperConfig;
import com.olsoftware.inventario.entity.User;
import com.olsoftware.inventario.model.GenericMapper;
import com.olsoftware.inventario.model.role.RoleMapper;

@Mapper(config = MyMapperConfig.class, uses = RoleMapper.class)
public interface UserMapper
	extends GenericMapper<UserRequest, User, UserDto> {

	@Override
	@Mapping(source = "roleID", target = "role.roleID")
	@Mapping(source = "statusID", target = "status.statusID")
	User toEntity(UserRequest request);
}
