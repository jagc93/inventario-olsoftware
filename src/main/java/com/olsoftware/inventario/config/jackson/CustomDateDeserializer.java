package com.olsoftware.inventario.config.jackson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

@Configuration
@DependsOn("simpleDateFormatStandard")
public class CustomDateDeserializer  extends KeyDeserializer {

	private final SimpleDateFormat formatter;

	public CustomDateDeserializer(@Qualifier("simpleDateFormatStandard") SimpleDateFormat _formatter) {
        this.formatter = _formatter;
    }

	@Override
	public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
		try {
            return formatter.parse(key);
        } catch (ParseException e) {
            throw new IOException("Error parsing date key", e);
        }
	}
}
