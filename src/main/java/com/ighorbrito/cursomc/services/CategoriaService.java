package com.ighorbrito.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ighorbrito.cursomc.domain.Categoria;
import com.ighorbrito.cursomc.repositories.CategoriaRepository;
import com.ighorbrito.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria searchById(Integer id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto de ID "+ id + " não encontrado " +
				"Tipo: " + Categoria.class.getName()));
	}
}
