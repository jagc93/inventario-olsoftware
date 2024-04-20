package com.olsoftware.inventario.factory.base;

/**
 * @param <R> Request
 * @param <D> Dto
 */
public interface BaseFactory<R, D> {
	public D create();
	public R request();
}
