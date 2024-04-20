package com.olsoftware.inventario.model.area;

import org.mapstruct.Mapper;

import com.olsoftware.inventario.config.MyMapperConfig;
import com.olsoftware.inventario.entity.Area;
import com.olsoftware.inventario.model.GenericMapper;

@Mapper(config = MyMapperConfig.class)
public interface AreaMapper
	extends GenericMapper<AreaRequest, Area, AreaDto> {

}
