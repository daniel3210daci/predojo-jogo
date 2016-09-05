package com.jogo.dao.impl;

import javax.ejb.Local;

import com.jogo.dao.common.DaoJogo;
import com.jogo.exception.BusinessEntityViolationException;
import com.jogo.model.Arma;

@Local
public interface ArmaDao extends DaoJogo<Arma> {
	
	public void persistDaoBean(Arma arma) throws BusinessEntityViolationException;
	
	public void mergeDaoBean(Arma arma) throws BusinessEntityViolationException;
	
	public Arma findByArma(String arma);  
}
