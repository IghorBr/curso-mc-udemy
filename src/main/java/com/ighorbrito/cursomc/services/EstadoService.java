package com.ighorbrito.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ighorbrito.cursomc.domain.Estado;
import com.ighorbrito.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	
	public List<Estado> searchAllByNome() {
		return estadoRepository.findAllByOrderByNome();
	}
}
