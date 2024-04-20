package com.olsoftware.inventario.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.olsoftware.inventario.factory.RoleFactory;
import com.olsoftware.inventario.mock.JwtServiceMock;
import com.olsoftware.inventario.model.role.RoleDto;
import com.olsoftware.inventario.model.role.RoleRequest;
import com.olsoftware.inventario.model.user.UserRequest;
import com.olsoftware.inventario.util.ObjectMapperUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RoleFactory factory;

	@Autowired
	private ObjectMapperUtil objectMapper;

	@Autowired
	private JwtServiceMock jwtServiceMock;

	private String uri = "/api/inventario/role";

	@Test
	@Transactional
	public void create() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post(String.format("%s/createRole", uri))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(factory.request()))
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isCreated(),
				MockMvcResultMatchers.jsonPath("$.roleID", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.roleName", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.roleCode", Matchers.notNullValue())
		);
	}

	@Test
	@Transactional
	public void update() throws Exception {
		RoleDto roleDto = factory.create();
		RoleRequest request = new RoleRequest();
		request.setRoleName("Other");

		String result = mockMvc.perform(
				MockMvcRequestBuilders.patch(String.format("%s/updateRole/%s", uri, roleDto.getRoleID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(request))
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn()
		.getResponse()
		.getContentAsString();

		RoleDto dto = objectMapper.readValue(result, RoleDto.class);

		Assertions.assertEquals(roleDto.getRoleID(), dto.getRoleID());
		Assertions.assertEquals(request.getRoleName(), dto.getRoleName());
		Assertions.assertEquals(roleDto.getRoleCode(), dto.getRoleCode());
	}

	@Test
	public void updateNotExist() throws Exception {
		Long roleID = 1L;
		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.patch(String.format("%s/updateRole/%s", uri, roleID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(new UserRequest()))
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("Role by ID %s not found", roleID), errorMessage);
	}
	
	@Test
	@Transactional
	public void show() throws Exception {
		RoleDto roleDto = factory.create();

		mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getRole/%s", uri, roleDto.getRoleID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.jsonPath("$.roleID", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.roleName", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.roleCode", Matchers.notNullValue())
		);
	}

	@Test
	public void showNotExist() throws Exception {
		Long roleID = 100L;

		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getRole/%s", uri, roleID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("Role by ID %s not found", roleID), errorMessage);
	}

	@Test
	@Transactional
	public void index() throws Exception {
		for(int i = 0; i < 2; i++) {
			factory.create();
		}

		mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getRoles", uri))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.jsonPath("$.content", Matchers.not(Matchers.hasSize(0))),
				MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.equalTo(2)),
				MockMvcResultMatchers.jsonPath("$.last", Matchers.equalTo(true)),
				MockMvcResultMatchers.jsonPath("$.empty", Matchers.equalTo(false))
		);
	}

	@Test
	@Transactional
	public void delete() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete(String.format("%s/deleteRole/%s", uri, factory.create().getRoleID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void deleteNotExist() throws Exception {
		Long roleID = 1L;
		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.delete(String.format("%s/deleteRole/%s", uri, roleID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("Role by ID %s not found", roleID), errorMessage);
	}
}
