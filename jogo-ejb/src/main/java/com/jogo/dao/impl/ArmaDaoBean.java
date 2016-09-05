package com.jogo.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;

import com.jogo.dao.common.AbstractDaoBeanJogo;
import com.jogo.exception.BusinessEntityViolationException;
import com.jogo.model.Arma;
import com.jogo.model.Jogador;

@Stateless
public class ArmaDaoBean extends AbstractDaoBeanJogo<Arma> implements ArmaDao {
	
	@Inject
	private Logger log;

	public void persistDaoBean(Arma arma) throws BusinessEntityViolationException {
		Set<ConstraintViolation<Arma>> constraintViolations = this.validator.validate(arma);
		
		List<String> listaMensagens = new LinkedList<String>();
		
		// popula uma lista de strings com mensagens pegas pelo validator
		for (ConstraintViolation<Arma> constraintViolation : constraintViolations) {
			
			listaMensagens.add(constraintViolation.getMessage());
		}
		
		if(!listaMensagens.isEmpty()){
			
			throw new BusinessEntityViolationException(listaMensagens);
		}
		
		if (arma == null) {
			
			throw new BusinessEntityViolationException("O objeto arma está nulo.");
		}
		
		persist(arma);	
	}

	public void mergeDaoBean(Arma arma) throws BusinessEntityViolationException {
		Set<ConstraintViolation<Arma>> constraintViolations = this.validator.validate(arma);
		
		List<String> listaMensagens = new LinkedList<String>();
		
		// popula uma lista de strings com mensagens pegas pelo validator
		for (ConstraintViolation<Arma> constraintViolation : constraintViolations) {
			
			listaMensagens.add(constraintViolation.getMessage());
		}
		
		if(!listaMensagens.isEmpty()){
			
			throw new BusinessEntityViolationException(listaMensagens);
		}
		
		if (arma == null) {
			
			throw new BusinessEntityViolationException("O objeto arma está nulo.");
		}
		
		merge(arma);	
	}

	@Override
	public Arma findByArma(String arma) {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<Arma> query = builder.createQuery(Arma.class);

		Root<Arma> from = query.from(Arma.class);
		query.select(from).where(builder.equal(from.get("nome"), arma));
		
		return getSingleResult(query);
	}
}
