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
import com.jogo.model.Jogador;
import com.jogo.model.Partida;

@Stateless
public class JogadorDaoBean extends AbstractDaoBeanJogo<Jogador> implements JogadorDao {

	@Inject
	private Logger log;

	public void persistDaoBean(Jogador jogador) throws BusinessEntityViolationException {
		Set<ConstraintViolation<Jogador>> constraintViolations = this.validator.validate(jogador);
		
		List<String> listaMensagens = new LinkedList<String>();
		
		// popula uma lista de strings com mensagens pegas pelo validator
		for (ConstraintViolation<Jogador> constraintViolation : constraintViolations) {
			
			listaMensagens.add(constraintViolation.getMessage());
		}
		
		if(!listaMensagens.isEmpty()){
			
			throw new BusinessEntityViolationException(listaMensagens);
		}
		
		if (jogador == null) {
			
			throw new BusinessEntityViolationException("O objeto jogador está nulo.");
		}
		
		persist(jogador);	
	}

	public void mergeDaoBean(Jogador jogador) throws BusinessEntityViolationException {
		Set<ConstraintViolation<Jogador>> constraintViolations = this.validator.validate(jogador);
		
		List<String> listaMensagens = new LinkedList<String>();
		
		// popula uma lista de strings com mensagens pegas pelo validator
		for (ConstraintViolation<Jogador> constraintViolation : constraintViolations) {
			
			listaMensagens.add(constraintViolation.getMessage());
		}
		
		if(!listaMensagens.isEmpty()){
			
			throw new BusinessEntityViolationException(listaMensagens);
		}
		
		if (jogador == null) {
			
			throw new BusinessEntityViolationException("O objeto jogador está nulo.");
		}
		
		merge(jogador);	
	}

	@Override
	public Jogador findByNome(String nome) {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<Jogador> query = builder.createQuery(Jogador.class);

		Root<Jogador> from = query.from(Jogador.class);
		query.select(from).where(builder.equal(from.get("nome"), nome));
		
		return getSingleResult(query);
	}

	@Override
	public List<Jogador> listJogadorVencedorSemMorrer() {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<Jogador> query = builder.createQuery(Jogador.class);

		Root<Jogador> from = query.from(Jogador.class);
		query.select(from).where(builder.equal(from.get("ativo"), 1));
		
		return getResultList(query);
	}
}
