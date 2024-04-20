package com.olsoftware.inventario.controller;

import static com.olsoftware.inventario.constant.MessagesConstant.*;
import static com.olsoftware.inventario.constant.UserConstant.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.github.javafaker.Faker;
import com.olsoftware.inventario.factory.UserFactory;
import com.olsoftware.inventario.mock.JwtServiceMock;
import com.olsoftware.inventario.model.user.UserDto;
import com.olsoftware.inventario.model.user.UserRequest;
import com.olsoftware.inventario.util.ObjectMapperUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserFactory factory;

	@Autowired
	private ObjectMapperUtil objectMapper;

	@Autowired
	private JwtServiceMock jwtServiceMock;

	private static final Faker faker = new Faker();
	private String uri = "/api/inventario/user";

	@Test
	@Transactional
	public void createUser() throws Exception {
		UserRequest request = factory.request();
		mockMvc.perform(
				MockMvcRequestBuilders.post(String.format("%s/createUser", uri))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(request))
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isCreated(),
				MockMvcResultMatchers.jsonPath("$.userID", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.username", Matchers.equalTo(request.getUsername())),
				MockMvcResultMatchers.jsonPath("$.role", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.status", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.notNullValue())
		);
	}

	@Test
	@Transactional
	public void updateUser() throws Exception {
		UserDto userDto = factory.create();
		UserRequest request = new UserRequest();
		request.setUsername("Other");

		String result = mockMvc.perform(
				MockMvcRequestBuilders.patch(String.format("%s/updateUser/%s", uri, userDto.getUserID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(request))
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn()
		.getResponse()
		.getContentAsString();

		UserDto user = objectMapper.readValue(result, UserDto.class);

		Assertions.assertEquals(userDto.getUserID(), user.getUserID());
		Assertions.assertEquals(request.getUsername(), user.getUsername());
		Assertions.assertNotNull(user.getRole());
		Assertions.assertNotNull(user.getStatus());
	}

	@Test
	public void updateUserNotExist() throws Exception {
		Long userID = 1L;
		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.patch(String.format("%s/updateUser/%s", uri, userID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
				.content(objectMapper.writeValueAsString(new UserRequest()))
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("user by ID %s not found", userID), errorMessage);
	}

	@Test
	@Transactional
	public void show() throws Exception {
		UserDto userDto = factory.create();

		mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getUser/%s", uri, userDto.getUserID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.jsonPath("$.userID", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.username", Matchers.equalTo(userDto.getUsername())),
				MockMvcResultMatchers.jsonPath("$.emailAddress", Matchers.equalTo(userDto.getEmailAddress())),
				MockMvcResultMatchers.jsonPath("$.role", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.status", Matchers.notNullValue()),
				MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.notNullValue())
		);
	}

	@Test
	public void showUserNotExist() throws Exception {
		Long userID = 1L;

		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getUser/%s", uri, userID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("User by ID %s not found", userID), errorMessage);
	}

	@Test
	@Transactional
	public void index() throws Exception {
		for(int i = 0; i < 2; i++) {
			factory.create();
		}

		mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getUsers", uri))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.jsonPath("$.content.length()", Matchers.equalTo(2)),
				MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.equalTo(2)),
				MockMvcResultMatchers.jsonPath("$.last", Matchers.equalTo(true)),
				MockMvcResultMatchers.jsonPath("$.empty", Matchers.equalTo(false))
		);
	}

	@Test
	@Transactional
	public void indexPaginatorSortDescAndSearch() throws Exception {
		UserDto user = factory.create();

		mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("%s/getUsers?page=0&size=5&sort=userID,DESC&search=userID==%s", uri, user.getUserID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpectAll(
				MockMvcResultMatchers.status().isOk(),
				MockMvcResultMatchers.jsonPath("$.content.length()", Matchers.equalTo(1)),
				MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.equalTo(1)),
				MockMvcResultMatchers.jsonPath("$.last", Matchers.equalTo(true)),
				MockMvcResultMatchers.jsonPath("$.empty", Matchers.equalTo(false))
		);
	}

	@Test
	@Transactional
	public void delete() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete(String.format("%s/deleteUser/%s", uri, factory.create().getUserID()))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void deleteUserNotExist() throws Exception {
		Long userID = 1L;
		String errorMessage = mockMvc.perform(
				MockMvcRequestBuilders.delete(String.format("%s/deleteUser/%s", uri, userID))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtServiceMock.getToken())
		)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn()
		.getResponse()
		.getContentAsString();

		Assertions.assertEquals(String.format("User by ID %s not found", userID), errorMessage);
	}

	@Test
	@Transactional
	public void failedValidationsInCreateUser() throws Exception {
		UserRequest request;
		String errorMessage;
		String expected = null;

		Map<String, List<Object>> validations = new HashMap<>();
		validations.put("identificationType", Arrays.asList(null, "", faker.lorem().characters(MAX_ID_TYPE_LENGTH + 1)));
		validations.put("identificationNumber", Arrays.asList(null, "", faker.lorem().characters(MAX_ID_NUMBER_LENGTH + 1)));
		validations.put("firstName", Arrays.asList(null, "", faker.lorem().characters(MAX_FIRST_NAME_LENGTH + 1)));
		validations.put("middleName", Arrays.asList(faker.lorem().characters(MAX_MIDDLE_NAME_LENGTH + 1)));
		validations.put("lastName", Arrays.asList(null, "", faker.lorem().characters(MAX_LAST_NAME_LENGTH + 1)));
		validations.put("secondLastName", Arrays.asList(faker.lorem().characters(MAX_SECOND_LAST_NAME_LENGTH + 1)));
		validations.put("emailAddress", Arrays.asList(null, "", faker.lorem().characters(MAX_EMAIL_ADDRESS_LENGTH + 1)));
		validations.put("phoneNumber", Arrays.asList(faker.lorem().characters(MAX_PHONE_NUMBER_LENGTH + 1)));
		validations.put("username", Arrays.asList(null, "", faker.lorem().characters(MAX_USERNAME_LENGTH + 1)));
		validations.put("password", Arrays.asList(null, "", faker.lorem().characters(MAX_PASSWORD_LENGTH + 1)));
		validations.put("roleID", Arrays.asList((String) null));
		validations.put("statusID", Arrays.asList((String) null));

		for (Map.Entry<String, List<Object>> entry : validations.entrySet()) {
			String key = entry.getKey();
		    List<Object> values = entry.getValue();

		    for (Object value : values) {
		    	request = factory.request();
		    	switch (key) {
			    	case "identificationType": {
						request.setIdentificationType((String) value);
						break;
					}
			    	case "identificationNumber": {
						request.setIdentificationNumber((String) value);
						break;
					}
			    	case "firstName": {
						request.setFirstName((String) value);
						break;
					}
			    	case "middleName": {
						request.setMiddleName((String) value);
						break;
					}
			    	case "lastName": {
						request.setLastName((String) value);
						break;
					}
			    	case "secondLastName": {
						request.setSecondLastName((String) value);
						break;
					}
			    	case "emailAddress": {
						request.setEmailAddress((String) value);
						break;
					}
			    	case "phoneNumber": {
						request.setPhoneNumber((String) value);
						break;
					}
			    	case "username": {
						request.setUsername((String) value);
						break;
					}
			    	case "password": {
						request.setPassword((String) value);
						break;
					}
			    	case "roleID": {
						request.setRoleID(null);
						break;
					}
			    	case "statusID": {
						request.setStatusID(null);
						break;
					}
		    	}

		    	errorMessage = mockMvc.perform(
		    			MockMvcRequestBuilders.post(String.format("%s/createUser", uri))
		                .contentType(MediaType.APPLICATION_JSON)
		                .header("Authorization", jwtServiceMock.getToken())
		                .content(objectMapper.writeValueAsString(request))
		        )
		    			.andExpect(MockMvcResultMatchers.status().isBadRequest())
		                .andReturn()
		                .getResponse()
		                .getContentAsString();

		    	if (value == null || ((String) value).isBlank()) {
		    		expected = String.format(NOT_NULL_OR_EMPTY_MESSAGE, key);
		    	} else {
		    		switch (key) {
			    		case "identificationType": {
			    			expected = String.format(LENGTH_EXCEED_MESSAGE, key, MAX_ID_TYPE_LENGTH);
							break;
						}
				    	case "identificationNumber": {
				    		expected = String.format(LENGTH_EXCEED_MESSAGE, key, MAX_ID_NUMBER_LENGTH);
							break;
						}
				    	case "firstName": {
				    		expected = String.format(LENGTH_EXCEED_MESSAGE, key, MAX_FIRST_NAME_LENGTH);
							break;
						}
				    	case "middleName": {
				    		expected = String.format(LENGTH_EXCEED_MESSAGE, key, MAX_MIDDLE_NAME_LENGTH);
							break;
						}
				    	case "lastName": {
				    		expected = String.format(LENGTH_EXCEED_MESSAGE, key, MAX_LAST_NAME_LENGTH);
							break;
						}
				    	case "secondLastName": {
				    		expected = String.format(LENGTH_EXCEED_MESSAGE, key, MAX_SECOND_LAST_NAME_LENGTH);
							break;
						}
				    	case "emailAddress": {
				    		expected = String.format(LENGTH_EXCEED_MESSAGE, key, MAX_EMAIL_ADDRESS_LENGTH);
							break;
						}
				    	case "phoneNumber": {
				    		expected = String.format(LENGTH_EXCEED_MESSAGE, key, MAX_PHONE_NUMBER_LENGTH);
							break;
						}
				    	case "username": {
				    		expected = String.format(LENGTH_EXCEED_MESSAGE, key, MAX_USERNAME_LENGTH);
							break;
						}
				    	default:
				    		expected = String.format(LENGTH_EXCEED_MESSAGE, key, MAX_PASSWORD_LENGTH);
					}
		    	}

		    	Assertions.assertEquals(expected, errorMessage);
		    }
		}
	}

	@Test
	@Transactional
	public void failedUniquesValidationsInCreateUser() throws Exception {
		UserRequest request;
		String expected;
		String errorMessage;
		UserDto dto;
		String[] validations = { "emailAddress", "username" };

		for (String value : validations) {
			dto = factory.create();
			request = factory.request();

			switch (value) {
				case "emailAddress": {
					request.setEmailAddress(dto.getEmailAddress());
					break;
				}
				default:
					request.setUsername(dto.getUsername());
			}

			errorMessage = mockMvc.perform(
	    			MockMvcRequestBuilders.post(String.format("%s/createUser", uri))
	                .contentType(MediaType.APPLICATION_JSON)
	                .header("Authorization", jwtServiceMock.getToken())
	                .content(objectMapper.writeValueAsString(request))
	        )
	    			.andExpect(MockMvcResultMatchers.status().isBadRequest())
	                .andReturn()
	                .getResponse()
	                .getContentAsString();

			switch (value) {
				case "emailAddress": {
					expected = String.format(ALREADY_EXIST, "user", value, request.getEmailAddress());
					break;
				}
				default:
					expected = String.format(ALREADY_EXIST, "user", value, request.getUsername());
			}

			Assertions.assertEquals(expected, errorMessage);
		}
	}

	@Test
	@Transactional
	public void failedUniquesValidationsInUpdateUser() throws Exception {
		UserRequest request;
		String expected;
		String errorMessage;
		UserDto firstUserDto = factory.create();
		UserDto secondUserDto;
		String[] validations = { "emailAddress", "username" };

		for (String value : validations) {
			secondUserDto = factory.create();
			request = new UserRequest();

			switch (value) {
				case "emailAddress": {
					request.setEmailAddress(firstUserDto.getEmailAddress());
					break;
				}
				default:
					request.setUsername(firstUserDto.getUsername());
			}

			errorMessage = mockMvc.perform(
	    			MockMvcRequestBuilders.patch(String.format("%s/updateUser/%s", uri, secondUserDto.getUserID()))
	                .contentType(MediaType.APPLICATION_JSON)
	                .header("Authorization", jwtServiceMock.getToken())
	                .content(objectMapper.writeValueAsString(request))
	        )
	    			.andExpect(MockMvcResultMatchers.status().isBadRequest())
	                .andReturn()
	                .getResponse()
	                .getContentAsString();

			switch (value) {
				case "emailAddress": {
					expected = String.format(ALREADY_EXIST, "user", value, request.getEmailAddress());
					break;
				}
				default:
					expected = String.format(ALREADY_EXIST, "user", value, request.getUsername());
			}

			Assertions.assertEquals(expected, errorMessage);
		}
	}
}
