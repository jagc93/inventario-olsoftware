package com.olsoftware.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.olsoftware.inventario.entity.Status;

@Repository
public interface StatusRepository
	extends JpaRepository<Status, String>,
			JpaSpecificationExecutor<Status> {

	boolean existsByStatusIDIgnoreCase(String statusID);

	boolean existsByStatusNameIgnoreCase(String statusName);
}
