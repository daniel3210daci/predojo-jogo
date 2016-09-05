package com.jogo.dao.impl;

import com.jogo.dao.common.DaoJogo;
import com.jogo.exception.BusinessEntityViolationException;
import com.jogo.model.Arma;
import com.jogo.model.Partida;
import com.jogo.model.PartidaJogador;

import java.util.List;

import javax.ejb.Local;

@Local
public interface PartidaDao extends DaoJogo<Partida> {

	public void persistDaoBean(Partida partida) throws BusinessEntityViolationException;
	
	public void mergeDaoBean(Partida partida) throws BusinessEntityViolationException;
	
	public Partida findByCodigo(long codigo) throws BusinessEntityViolationException;
	
	public List<PartidaJogador> rankingPartidaJogador();
	
	public List<Arma> VerificaArmaPreferidaGanhador(long codiPartida); 
}
