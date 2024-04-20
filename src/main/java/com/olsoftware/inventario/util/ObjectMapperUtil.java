package com.olsoftware.inventario.util;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ObjectMapperUtil {

	private final ObjectMapper objectMapper;

	public ObjectMapperUtil(ObjectMapper _objectMapper) {
		this.objectMapper = _objectMapper;
	}

	public <T> String writeValueAsString(T object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException jpe) {
			throw new IllegalArgumentException(jpe);
		}
	}

	public <T> T readValue(String str, TypeReference<T> tr) {
		try {
			return objectMapper.readValue(str, tr);
		} catch (JsonProcessingException jpe) {
			throw new IllegalArgumentException(jpe);
		}
	}
	
	public <T> T readValue(InputStream is, TypeReference<T> tr) {
		try {
			return objectMapper.readValue(is, tr);
		} catch (JsonProcessingException jpe) {
			throw new IllegalArgumentException(jpe);
		} catch (IOException ioe) {
			throw new IllegalArgumentException(ioe);
		}
	}

	public <T> T readValue(String str, Class<T> clazz) {
		try {
			return objectMapper.readValue(str, clazz);
		} catch (JsonProcessingException jpe) {
			throw new IllegalArgumentException(jpe);
		}
	}
}
