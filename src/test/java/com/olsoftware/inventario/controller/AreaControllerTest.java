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

import com.olsoftware.inventario.factory.AreaFactory;
import com.olsoftware.inventario.mock.JwtServiceMock;
import com.olsoftware.inventario.model.area.AreaDto;
import com.olsoftware.inventario.model.area.AreaRequest;
import com.olsoftware.inventario.model.user.UserRequest;
import com.olsoftware.inventario.util.ObjectMapperUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class AreaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AreaFactory factory;

	@Autowired
	private ObjectMapperUtil objectMapper;

	@Autowired
	private JwtServiceMock jwtServiceMock;

	private String uri = "/api/inventario/area";

	@Test
	@Transactional
	public void createArea() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post(String.format("%s/createArea", uri))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(factory.request()))
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isCreated(),
				MockMvcResultMatchers.jsonPath("$.areaID", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.areaName", Matchers.notNullValue())
		);
	}

	@Test
	@Transactional
	public void updateArea() throws Exception {
		AreaDto areaDto = factory.create();
		AreaRequest request = new AreaRequest();
		request.setAreaName("Other");

		String result = mockMvc.perform(
				MockMvcRequestBuilders.patch(String.format("%s/updateArea/%s", uri, areaDto.getAreaID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(request))
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn()
		.getResponse()
		.getContentAsString();

		AreaDto dto = objectMapper.readValue(result, AreaDto.class);

		Assertions.assertEquals(areaDto.getAreaID(), dto.getAreaID());
		Assertions.assertEquals(request.getAreaName(), dto.getAreaName());
	}

	@Test
	public void updateAreaNotExist() throws Exception {
		Long areaID = 1L;
		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.patch(String.format("%s/updateArea/%s", uri, areaID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(new UserRequest()))
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("Area by ID %s not found", areaID), errorMessage);
	}

	@Test
	@Transactional
	public void show() throws Exception {
		AreaDto areaDto = factory.create();

		mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getArea/%s", uri, areaDto.getAreaID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.jsonPath("$.areaID", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.areaName", Matchers.notNullValue())
		);
	}

	@Test
	public void showNotExist() throws Exception {
		Long areaID = 1L;

		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getArea/%s", uri, areaID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("Area by ID %s not found", areaID), errorMessage);
	}

	@Test
	@Transactional
	public void index() throws Exception {
		for(int i = 0; i < 2; i++) {
			factory.create();
		}

		mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getAreas", uri))
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
				MockMvcRequestBuilders.delete(String.format("%s/deleteArea/%s", uri, factory.create().getAreaID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void deleteNotExist() throws Exception {
		Long areaID = 1L;
		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.delete(String.format("%s/deleteArea/%s", uri, areaID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("Area by ID %s not found", areaID), errorMessage);
	}
}
