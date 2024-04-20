package com.olsoftware.inventario.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.olsoftware.inventario.util.ObjectMapperUtil;

import feign.FeignException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

	private final ObjectMapperUtil objectMapper;
	
	public GlobalExceptionHandler(ObjectMapperUtil _objectMapper) {
		this.objectMapper = _objectMapper;
	}
	
	@ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class })
	public ResponseEntity<String> handlePropertyReferenceException(RuntimeException re) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(re.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(e.getMessage());
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<String> handleFeignException(FeignException fe) {
		return ResponseEntity
				.status(fe.status())
				.body(getMessage(fe.getMessage()));
	}

	private String getMessage(String message) {
		Pattern pattern = Pattern.compile("\\{.*\"message\":\".*\"\\}");
		Matcher matcher = pattern.matcher(message);
		if (matcher.find()) {
		    String extractedMessage = matcher.group();
		    System.out.println("Extracted Message: " + extractedMessage);
		    ExceptionResponse er = objectMapper.readValue(extractedMessage, ExceptionResponse.class);
		    return er.getMessage();
		} else {
		    return message;
		}
	}
}
