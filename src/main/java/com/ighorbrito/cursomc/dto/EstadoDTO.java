package com.ighorbrito.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.ighorbrito.cursomc.domain.Estado;

public class EstadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String nome;

	public EstadoDTO() {

	}
	
	public EstadoDTO(Estado estado) {
		this.setId(estado.getId());
		this.nome = estado.getNome();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
