package com.olsoftware.inventario.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olsoftware.inventario.model.user.UserDto;
import com.olsoftware.inventario.model.user.UserRequest;
import com.olsoftware.inventario.service.user.UserService;

@RestController
@RequestMapping("api/inventario/user")
public class UserController
	extends GenericController<UserDto, UserRequest, Long> {

	public UserController(UserService _service) {
		super(_service);
	}

	@Override
	@GetMapping("/getUsers")
	public ResponseEntity<Page<UserDto>> index(String search, Pageable pageable) {
		return super.index(search, pageable);
	}

	@Override
	@GetMapping("/getUser/{id}")
	public ResponseEntity<UserDto> show(Long id) {
		return super.show(id);
	}

	@Override
	@PostMapping("/createUser")
	public ResponseEntity<UserDto> create(UserRequest request) {
		return super.create(request);
	}

	@Override
	@PatchMapping("/updateUser/{id}")
	public ResponseEntity<UserDto> update(Long id, UserRequest request) {
		return super.update(id, request);
	}

	@Override
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<Void> delete(Long id) {
		return super.delete(id);
	}
}
