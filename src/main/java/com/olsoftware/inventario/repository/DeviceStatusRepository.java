package com.olsoftware.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.olsoftware.inventario.entity.DeviceStatus;

@Repository
public interface DeviceStatusRepository
	extends JpaRepository<DeviceStatus, Long>,
			JpaSpecificationExecutor<DeviceStatus> {

}
