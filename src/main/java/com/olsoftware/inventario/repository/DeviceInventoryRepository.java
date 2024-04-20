package com.olsoftware.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.olsoftware.inventario.entity.DeviceInventory;

@Repository
public interface DeviceInventoryRepository
	extends JpaRepository<DeviceInventory, Long>,
			JpaSpecificationExecutor<DeviceInventory> {

	boolean existsByDeviceModelDeviceModelID(Long deviceModelID);

	boolean existsByDeviceStatusDeviceStatusID(Long deviceStatusID);

	boolean existsByDeviceTypeDeviceTypeID(Long deviceTypeID);
	
	boolean existsByUserUserID(Long userID);
}
