package com.olsoftware.inventario.service.status;

/**
 * 
 * @param <E> Entity
 * @param <ID> Id type in entity
 */
public interface InternalService<E, ID> {
	public E findEntityById(ID id);
}
