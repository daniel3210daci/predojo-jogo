package com.jogo.dao.impl;

import java.util.List;

import javax.ejb.Local;

import com.jogo.dao.common.DaoJogo;
import com.jogo.exception.BusinessEntityViolationException;
import com.jogo.model.Jogador;

@Local
public interface JogadorDao extends DaoJogo<Jogador> {
	public void persistDaoBean(Jogador jogador) throws BusinessEntityViolationException;
	
	public void mergeDaoBean(Jogador jogador) throws BusinessEntityViolationException;
	
	public Jogador findByNome(String nome); 
	
	public List<Jogador> listJogadorVencedorSemMorrer();
}
