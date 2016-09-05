package com.jogo.dao.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;

import com.jogo.dao.common.AbstractDaoBeanJogo;
import com.jogo.exception.BusinessEntityViolationException;
import com.jogo.model.Arma;
import com.jogo.model.Partida;
import com.jogo.model.PartidaJogador;

@Stateless
public class PartidaDaoBean extends AbstractDaoBeanJogo<Partida> implements PartidaDao {

	@Inject
	private Logger log;

	public void persistDaoBean(Partida partida) throws BusinessEntityViolationException {
		Set<ConstraintViolation<Partida>> constraintViolations = this.validator.validate(partida);
		
		List<String> listaMensagens = new LinkedList<String>();
		
		// popula uma lista de strings com mensagens pegas pelo validator
		for (ConstraintViolation<Partida> constraintViolation : constraintViolations) {
			
			listaMensagens.add(constraintViolation.getMessage());
		}
		
		if(!listaMensagens.isEmpty()){
			
			throw new BusinessEntityViolationException(listaMensagens);
		}
		
		if (partida == null) {
			
			throw new BusinessEntityViolationException("O objeto partida está nulo.");
		}
		
		persist(partida);	
	}

	public void mergeDaoBean(Partida partida) throws BusinessEntityViolationException {
		Set<ConstraintViolation<Partida>> constraintViolations = this.validator.validate(partida);
		
		List<String> listaMensagens = new LinkedList<String>();
		
		// popula uma lista de strings com mensagens pegas pelo validator
		for (ConstraintViolation<Partida> constraintViolation : constraintViolations) {
			
			listaMensagens.add(constraintViolation.getMessage());
		}
		
		if(!listaMensagens.isEmpty()){
			
			throw new BusinessEntityViolationException(listaMensagens);
		}
		
		if (partida == null) {
			
			throw new BusinessEntityViolationException("O objeto partida está nulo.");
		}
		
		merge(partida);	
	}

	@Override
	public Partida findByCodigo(long codigo) throws BusinessEntityViolationException {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<Partida> query = builder.createQuery(Partida.class);

		Root<Partida> from = query.from(Partida.class);
		query.select(from).where(builder.equal(from.get("codPartida"), codigo));
		
		return getSingleResult(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartidaJogador> rankingPartidaJogador() {
		List<PartidaJogador> lstRetorno = new ArrayList<PartidaJogador>();
		
		String sql = "with rankinpartida as " +
					 "( " +
				     "   select " +
					 "	    p.codPartida partida, " +
				     "      j.nome jogador " +
					 "	    count(pj.qtdMortes) QtdMortes, " +
					 "	    count(pj.qtdMortestotal) TotalMortes " +
					 "	  from  " +
					 "	    partida_jogador pj " + 
					 "	      join jogadores j on j.id on pj.id_jogador " +
					 "	      join partida p on p.codPartida = pj.codPartida " +
					 "	  group by " +
					 "	     p.codPartida,  " +  
					 "       j.nome " +
					 "	  order by " +
					 "	     p.codPartida, " +
					 "       j.nome " +
					 ") " +
					 "	select * from rankinpartida " ;
		
		Query qry = entityManager.createNativeQuery(sql, PartidaJogador.class);
		
		lstRetorno = qry.getResultList();
		
		return lstRetorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Arma> VerificaArmaPreferidaGanhador(long codPartida) {
		List<Arma> lstRetorno = new ArrayList<Arma>();
		
		String sql = "with rankinpartida as " +
				 "( " +
			     "   select " +
				 "	    p.codPartida partida, " +
			     "      j.nome jogador " +
				 "	    count(pj.qtdMortes) QtdMortes, " +
				 "	    count(pj.qtdMortestotal) TotalMortes " +
				 "	  from  " +
				 "	    partida_jogador pj " + 
				 "	      join jogadores j on j.id on pj.id_jogador " +
				 "	      join partida p on p.codPartida = pj.codPartida " +
				 "        join armas am on am.id_jogador o j.id " +
				 "    where " +
				 "        p.codPartida = " + codPartida +
				 "	  group by " +
				 "	     p.codPartida,  " +  
				 "       j.nome " +
				 "	  order by " +
				 "	     p.codPartida, " +
				 "       j.nome " +
				 ") " +
				 "	select * from rankinpartida where max(QtdMortes) " ;
	
		Query qry = entityManager.createNativeQuery(sql, Arma.class);
		
		lstRetorno = qry.getResultList();
		
		return lstRetorno;
	}
}
