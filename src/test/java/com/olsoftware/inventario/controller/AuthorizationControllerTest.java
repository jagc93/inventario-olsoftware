package com.olsoftware.inventario.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.olsoftware.inventario.entity.User;
import com.olsoftware.inventario.mock.JwtServiceMock;
import com.olsoftware.inventario.model.auth.AuthenticationRequest;
import com.olsoftware.inventario.model.auth.AuthenticationResponse;
import com.olsoftware.inventario.model.role.RoleDto;
import com.olsoftware.inventario.repository.UserRepository;
import com.olsoftware.inventario.service.auth.AuthenticationService;
import com.olsoftware.inventario.util.ObjectMapperUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private JwtServiceMock jwtServiceMock;

	@Autowired
	private ObjectMapperUtil objectMapper;

	private String uri = "/api/inventario";

	@Test
	@Transactional
	@Sql(scripts = "/mock/db/insert_admin_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void authorized() throws Exception {
		User user = getUser();
		String headerJwt = getHeaderJwt(user);

		String result = mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/role/getRole/%s", uri, user.getRole().getRoleID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", headerJwt)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn()
		.getResponse()
		.getContentAsString();

		RoleDto role = objectMapper.readValue(result, RoleDto.class);
		assertEquals(user.getRole().getRoleID(), role.getRoleID());
		assertEquals(user.getRole().getRoleName(), role.getRoleName());
		assertEquals(user.getRole().getRoleCode(), role.getRoleCode());
	}

	@Test
	public void jwtExpired() throws Exception {
		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/role/getRole/%s", uri, 1L))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getTokenExpired())
		)
		.andExpect(MockMvcResultMatchers.status().isUnauthorized())
		.andReturn()
		.getResponse()
		.getErrorMessage();

		assertEquals("JWT expired", errorMessage);
	}

	@Test
    void noBearerToken() throws Exception {
    	String errorMessage = mockMvc.perform(
        		MockMvcRequestBuilders.get(String.format("%s/role/getRole/%s", uri, 1L))
                .contentType(MediaType.APPLICATION_JSON)
        )
		.andExpect(MockMvcResultMatchers.status().isForbidden())
		.andReturn()
		.getResponse()
		.getErrorMessage();

		Assertions.assertEquals("Access Denied", errorMessage);
    }

	private User getUser() {
		return userRepository.findById(1L).get();
	}

	private String getHeaderJwt(User user) {
		AuthenticationResponse response = authenticationService.login(new AuthenticationRequest(user.getUsername(), user.getPassword()));
		return String.format("Bearer %s", response.getAccessToken());
	}
}
