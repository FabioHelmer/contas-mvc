package com.client.services;

import java.util.List;

public interface GenericService<T, I> {

	T save(T entity);

	T update(T entity);
	
	T getById(I id);

	List<T> listAll();

	void delete(T entity);

	void deleteById(I id);

}
