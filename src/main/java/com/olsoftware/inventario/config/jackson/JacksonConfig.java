package com.olsoftware.inventario.config.jackson;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@DependsOn({"customDateSerializer", "customDateDeserializer"})
public class JacksonConfig {

	private final CustomDateSerializer customDateSerializer;
	private final CustomDateDeserializer customDateDeserializer;
	private final SimpleDateFormat sdf;

	public JacksonConfig(
		CustomDateSerializer _customDateSerializer,
		CustomDateDeserializer _customDateDeserializer,
		@Qualifier("simpleDateFormatStandard") SimpleDateFormat _sdf
	) {
		this.customDateSerializer = _customDateSerializer; 
		this.customDateDeserializer = _customDateDeserializer;
		this.sdf = _sdf;
	}

	@Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
        	.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
        	.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
	        .registerModule(new JavaTimeModule()
	        		.addSerializer(java.util.Date.class, customDateSerializer)
	        		.addSerializer(java.sql.Date.class, customDateSerializer)
	        		.addSerializer(java.sql.Timestamp.class, customDateSerializer)
	        		.addKeyDeserializer(java.util.Date.class, customDateDeserializer)
	        		.addKeyDeserializer(java.sql.Date.class, customDateDeserializer)
					.addKeyDeserializer(java.sql.Timestamp.class, customDateDeserializer)
	        )
	        .setDateFormat(sdf);
    }
}
