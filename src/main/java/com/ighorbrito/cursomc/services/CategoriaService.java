package com.ighorbrito.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ighorbrito.cursomc.domain.Categoria;
import com.ighorbrito.cursomc.dto.CategoriaDTO;
import com.ighorbrito.cursomc.repositories.CategoriaRepository;
import com.ighorbrito.cursomc.services.exceptions.DataIntegratityException;
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
	
	public Categoria insertCategoria(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}

	public Categoria updateCategoria(Categoria obj) {
		Categoria newCategoria = searchById(obj.getId());
		updateData(newCategoria, obj);
		
		return categoriaRepository.save(newCategoria);
	}

	public void deleteCategoria(Integer id) {
		searchById(id);
		try {			
			categoriaRepository.deleteById(id);		
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegratityException("Não é possível excluir a categoria que possui produtos");
		}
	}

	public List<Categoria> searchAll() {
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> searchPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);

	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	private void updateData(Categoria newCategoria, Categoria obj) {
		newCategoria.setNome(obj.getNome());
	}
	
}
