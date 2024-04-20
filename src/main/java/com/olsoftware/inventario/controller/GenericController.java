package com.olsoftware.inventario.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.olsoftware.inventario.service.GenericService;

/**
 * 
 * @param <D>  Dto
 * @param <R>  Request
 * @param <ID> Id type in entity
 */
public class GenericController<D, R, ID> {

	private final GenericService<D, R, ID> service;

	public GenericController(GenericService<D, R, ID> _service) {
		this.service = _service;
	}

	public ResponseEntity<Page<D>> index(
        @RequestParam(defaultValue = "") String search,
        Pageable pageable
	) {
		return ResponseEntity.ok(service.index(pageable, search));
	}

    public ResponseEntity<D> show(@PathVariable ID id) {
        return ResponseEntity.ok(service.show(id));
    }

    public ResponseEntity<D> create(@RequestBody R request) {
        return ResponseEntity
        	.status(HttpStatus.CREATED)
        	.body(service.create(request));
    }

    public ResponseEntity<D> update(@PathVariable ID id, @RequestBody R request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    public ResponseEntity<Void> delete(@PathVariable ID id) {
    	service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
