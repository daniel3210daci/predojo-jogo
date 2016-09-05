package com.jogo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

import com.jogo.common.AbstractEntityJogo;

@Entity(name="partida")
public class Partida extends AbstractEntityJogo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "partida_id", sequenceName = "partidar_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "partida_id")
	private Long id;
	
	@NotEmpty(message="Código da partida: campo obrigatório.")
	@Column(name="codPartida", length=9, unique=true)
	private long codPartida;
	
	@NotEmpty(message="Data de inicio da partida: campo obrigatório.")
	@Temporal(TemporalType.DATE)
	@Column(name="dataInicio")
	private Date dataInicio;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dataFinal")
	private Date dataFinal;
	
	@Column(name="descricao", length=200)
	private String descricao;
	
	@ManyToMany
	@JoinColumn(name="id_jogador")
	private List<Jogador> jogadores;
	
	@Column(name="qtdMortes")
	private long qtdMortes;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getCodPartida() {
		return codPartida;
	}

	public void setCodPartida(long codPartida) {
		this.codPartida = codPartida;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Jogador> getJogadores() {
		return jogadores;
	}

	public void setJogadores(List<Jogador> jogadores) {
		this.jogadores = jogadores;
	}

	public long getQtdMortes() {
		return qtdMortes;
	}

	public void setQtdMortes(long qtdMortes) {
		this.qtdMortes = qtdMortes;
	}
}
