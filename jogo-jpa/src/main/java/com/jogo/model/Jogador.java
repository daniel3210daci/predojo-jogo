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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.NotEmpty;

import com.jogo.common.AbstractEntityJogo;
import com.jogo.model.Arma;

@Entity(name="jogadores")
public class Jogador extends AbstractEntityJogo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "jogador_id", sequenceName = "jogador_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "jogador_id")
	private Long id;
	
	@NotEmpty(message="Nome do jogador: campo obrigatório.")
	@Column(name="nome", length=50)
	private String nome;

	public String getNome() {
		return nome;
	}

	@OneToMany
	@JoinColumn(name="id_arma")
	private List<Arma> armas;
	
	@Column(name="ativo")
	private long ativo;
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Arma> getArmas() {
		return armas;
	}

	public void setArmas(List<Arma> armas) {
		this.armas = armas;
	}

	public long getAtivo() {
		return ativo;
	}

	public void setAtivo(long ativo) {
		this.ativo = ativo;
	}
}
