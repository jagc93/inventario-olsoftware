package com.olsoftware.inventario.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface MyMapperConfig {

}
