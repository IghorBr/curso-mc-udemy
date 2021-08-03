package com.ighorbrito.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ighorbrito.cursomc.domain.Cidade;
import com.ighorbrito.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> searchCidadesByEstado(Integer estadoId) {
		return cidadeRepository.findCidades(estadoId);
	}
}
