package com.jogo.dao.common;

import java.util.List;

import com.jogo.common.AbstractEntityJogo;


public interface DaoJogo<E extends AbstractEntityJogo> {

	void persist(E instance);

	E find(long id);

	void remove(E instance);

	E merge(E instance);

	List<E> findAll();

}
