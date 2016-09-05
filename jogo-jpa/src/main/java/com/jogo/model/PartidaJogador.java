package com.jogo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.NotEmpty;

import com.jogo.common.AbstractEntityJogo;

@Entity(name="partida_jogador")
public class PartidaJogador extends AbstractEntityJogo implements Serializable{

	@Id
	@SequenceGenerator(name = "partida_jogador_id", sequenceName = "partida_jogador_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "partida_jogador_id")
	private Long id;
	
	@NotEmpty(message="Código da partida: campo obrigatório.")
	@Column(name="codPartida", length=9, unique=true)
	private long codPartida;
	
	@ManyToMany
	@JoinColumn(name="id_jogador")
	private List<Jogador> jogadores;
	
	@Column(name="qtdMortes")
	private long qtdMortes;
	
	@Column(name="qtdMortestotal")
	private long qtdMortestotal;

	public long getCodPartida() {
		return codPartida;
	}

	public void setCodPartida(long codPartida) {
		this.codPartida = codPartida;
	}

	public long getQtdMortes() {
		return qtdMortes;
	}

	public void setQtdMortes(long qtdMortes) {
		this.qtdMortes = qtdMortes;
	}

	public long getQtdMortestotal() {
		return qtdMortestotal;
	}

	public void setQtdMortestotal(long qtdMortestotal) {
		this.qtdMortestotal = qtdMortestotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Jogador> getJogadores() {
		return jogadores;
	}

	public void setJogadores(List<Jogador> jogadores) {
		this.jogadores = jogadores;
	}
	
}
