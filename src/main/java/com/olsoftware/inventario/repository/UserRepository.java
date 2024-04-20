package com.olsoftware.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.olsoftware.inventario.entity.User;

@Repository
public interface UserRepository
	extends JpaRepository<User, Long>,
			JpaSpecificationExecutor<User> {

	boolean existsByRoleRoleID(Long roleID);

	boolean existsByStatusStatusID(String statusID);

	boolean existsByEmailAddressIgnoreCase(String emailAddress);

	boolean existsByEmailAddressIgnoreCaseAndUserIDNot(String emailAddress, Long userID);

	boolean existsByUsernameIgnoreCase(String username);

	boolean existsByUsernameIgnoreCaseAndUserIDNot(String username, Long userID);

	User findByUsername(String username);
}
