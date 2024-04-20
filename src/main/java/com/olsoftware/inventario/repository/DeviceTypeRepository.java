package com.olsoftware.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.olsoftware.inventario.entity.DeviceType;

@Repository
public interface DeviceTypeRepository
	extends JpaRepository<DeviceType, Long>,
			JpaSpecificationExecutor<DeviceType> {

}
