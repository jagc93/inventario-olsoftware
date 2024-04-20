package com.olsoftware.inventario.controller;

import org.hamcrest.Matchers;
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
import com.olsoftware.inventario.model.auth.AuthenticationRequest;
import com.olsoftware.inventario.repository.UserRepository;
import com.olsoftware.inventario.util.ObjectMapperUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapperUtil objectMapper;

	private String uri = "/api/inventario/login";

	@Test
	@Transactional
	@Sql(scripts = "/mock/db/insert_admin_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void login() throws Exception {

		User user = userRepository.findById(1L).get();
		AuthenticationRequest request = new AuthenticationRequest(user.getUsername(), user.getPassword());

		mockMvc.perform(
				MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isOk(),
        		MockMvcResultMatchers.jsonPath("$.accessToken", Matchers.notNullValue())
		);
	}

	@Test
	@Transactional
	@Sql(scripts = "/mock/db/insert_inactive_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void loginInactiveUser() throws Exception {

		User user = userRepository.findById(1L).get();
		AuthenticationRequest request = new AuthenticationRequest(user.getUsername(), user.getPassword());

		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(errorMessage, "User is disabled");
	}
}
