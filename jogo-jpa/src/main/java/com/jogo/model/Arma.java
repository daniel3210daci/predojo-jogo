package com.jogo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.NotEmpty;

import com.jogo.common.AbstractEntityJogo;

@Entity(name="armas")
public class Arma extends AbstractEntityJogo implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	@Id
	@SequenceGenerator(name = "arma_id", sequenceName = "arma_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "arma_id")
	private Long id;
	
	@NotEmpty(message="Descrição da arma: campo obrigatório.")
	@Column(name="nome", length=50)
	private String nome;
	
	@NotEmpty(message="Tipo da arma: campo obrigatório.")
	@Column(name="tipo", length=50)
	private String tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
