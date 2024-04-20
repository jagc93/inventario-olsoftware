package com.olsoftware.inventario.exception;

import static com.olsoftware.inventario.constant.MessagesConstant.NOT_FOUND;
import static com.olsoftware.inventario.constant.MessagesConstant.NOT_NULL_OR_EMPTY_MESSAGE;
import static com.olsoftware.inventario.constant.MessagesConstant.LENGTH_EXCEED_MESSAGE;

import java.util.Objects;

import jakarta.ws.rs.NotFoundException;

public class GenericException {

    /**
     * @param value
     * @param fieldName (is required)
     */
	public static <T> void requireNotNull(T value, String fieldName) {
		String errorMessage = String.format(NOT_NULL_OR_EMPTY_MESSAGE, fieldName);
        Objects.requireNonNull(value, errorMessage);
    }

	/**
	 * @param value
	 * @param fieldName (is required)
	 */
	public static void requireNotEmpty(String value, String fieldName) {
		requireNotNull(value, fieldName);

		if (value.isBlank()) {
			String errorMessage = String.format(NOT_NULL_OR_EMPTY_MESSAGE, fieldName);
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * @param length
	 * @param maxLength
	 * @param fieldName (is required)
	 */
	public static void requiresThatNotExceedLength(int length, int maxLength, String fieldName) {
		if (fieldName.isBlank()) {
			throw new IllegalArgumentException("Field name cannot be determined.");
		}

		if (length > maxLength) {
			String errorMessage = String.format(LENGTH_EXCEED_MESSAGE, fieldName, maxLength);
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * @param fieldValue
	 * @param maxLength
	 * @param fieldName (is required)
	 */
	public static void validateFieldLength(String fieldValue, int maxLength, String fieldName) {
		if (fieldValue != null) {
			requiresThatNotExceedLength(fieldValue.length(), maxLength, fieldName);
		}
	}

	/**
	 * @param <T>
	 * @param id
	 * @param fileName
	 * @return
	 */
	public static <T> NotFoundException getNotFound(T id, String fileName) {
		return new NotFoundException(String.format(NOT_FOUND, fileName, id));
	}
}
