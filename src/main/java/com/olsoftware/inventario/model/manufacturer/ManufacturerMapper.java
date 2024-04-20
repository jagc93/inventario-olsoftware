package com.olsoftware.inventario.model.manufacturer;

import org.mapstruct.Mapper;

import com.olsoftware.inventario.config.MyMapperConfig;
import com.olsoftware.inventario.entity.Manufacturer;
import com.olsoftware.inventario.model.GenericMapper;

@Mapper(config = MyMapperConfig.class)
public interface ManufacturerMapper
	extends GenericMapper<ManufacturerRequest, Manufacturer, ManufacturerDto> {

}
