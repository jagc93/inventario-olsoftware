package com.olsoftware.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.olsoftware.inventario.entity.Manufacturer;

@Repository
public interface ManufacturerRepository
	extends JpaRepository<Manufacturer, Long>,
			JpaSpecificationExecutor<Manufacturer> {

}
