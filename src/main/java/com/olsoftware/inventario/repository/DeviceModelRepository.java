package com.olsoftware.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.olsoftware.inventario.entity.DeviceModel;

@Repository
public interface DeviceModelRepository
	extends JpaRepository<DeviceModel, Long>,
			JpaSpecificationExecutor<DeviceModel> {

	boolean existsByManufacturerManufacturerID(Long manufacturerID);
}
