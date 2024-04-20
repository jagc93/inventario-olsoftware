package com.olsoftware.inventario.service.user;

import com.olsoftware.inventario.model.user.UserDto;
import com.olsoftware.inventario.model.user.UserRequest;
import com.olsoftware.inventario.service.GenericService;

public interface UserService
	extends GenericService<UserDto, UserRequest, Long> {

}
