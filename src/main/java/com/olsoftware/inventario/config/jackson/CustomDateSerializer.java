package com.olsoftware.inventario.config.jackson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Configuration
@DependsOn("simpleDateFormatStandard")
public class CustomDateSerializer extends JsonSerializer<Object> {

	private final SimpleDateFormat formatter;

	public CustomDateSerializer(@Qualifier("simpleDateFormatStandard") SimpleDateFormat _formatter) {
        this.formatter = _formatter;
    }

	@Override
	public void serialize(Object t, JsonGenerator jg, SerializerProvider sp) throws IOException {
		if (t instanceof Date) {
			String formattedDate = formatter.format((Date) t);

			jg.writeString(formattedDate);
		} else {
			throw new IllegalArgumentException(String.format("Unsupported type for serialization: %s", t.getClass()));
		}
	}
}