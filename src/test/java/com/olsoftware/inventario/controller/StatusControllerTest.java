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

import com.olsoftware.inventario.factory.StatusFactory;
import com.olsoftware.inventario.mock.JwtServiceMock;
import com.olsoftware.inventario.model.status.StatusDto;
import com.olsoftware.inventario.model.status.StatusRequest;
import com.olsoftware.inventario.model.user.UserRequest;
import com.olsoftware.inventario.util.ObjectMapperUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class StatusControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private StatusFactory factory;

	@Autowired
	private ObjectMapperUtil objectMapper;

	@Autowired
	private JwtServiceMock jwtServiceMock;

	private String uri = "/api/inventario/status";

	@Test
	@Transactional
	public void create() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post(String.format("%s/createStatus", uri))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(factory.request()))
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isCreated(),
				MockMvcResultMatchers.jsonPath("$.statusID", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.statusName", Matchers.notNullValue())
		);
	}

	@Test
	@Transactional
	public void update() throws Exception {
		StatusDto statusDto = factory.create();
		StatusRequest request = new StatusRequest();
		request.setStatusName("Other");

		String result = mockMvc.perform(
				MockMvcRequestBuilders.patch(String.format("%s/updateStatus/%s", uri, statusDto.getStatusID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(request))
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn()
		.getResponse()
		.getContentAsString();

		StatusDto dto = objectMapper.readValue(result, StatusDto.class);

		Assertions.assertEquals(statusDto.getStatusID(), dto.getStatusID());
		Assertions.assertEquals(request.getStatusName(), dto.getStatusName());
	}

	@Test
	public void updateNotExist() throws Exception {
		String statusID = "S";
		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.patch(String.format("%s/updateStatus/%s", uri, statusID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(new UserRequest()))
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("Status by ID %s not found", statusID), errorMessage);
	}
	
	@Test
	@Transactional
	public void show() throws Exception {
		StatusDto statusDto = factory.create();

		mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getStatus/%s", uri, statusDto.getStatusID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.jsonPath("$.statusID", Matchers.equalTo(statusDto.getStatusID())),
				MockMvcResultMatchers.jsonPath("$.statusName", Matchers.equalTo(statusDto.getStatusName()))
		);
	}

	@Test
	public void showNotExist() throws Exception {
		String statusID = "S";

		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getStatus/%s", uri, statusID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("Status by ID %s not found", statusID), errorMessage);
	}

	@Test
	@Transactional
	public void index() throws Exception {
		for(int i = 0; i < 2; i++) {
			factory.create();
		}

		mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getStatuses", uri))
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
				MockMvcRequestBuilders.delete(String.format("%s/deleteStatus/%s", uri, factory.create().getStatusID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void deleteNotExist() throws Exception {
		String statusID = "S";
		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.delete(String.format("%s/deleteStatus/%s", uri, statusID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("Status by ID %s not found", statusID), errorMessage);
	}
}
