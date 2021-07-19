package com.ighorbrito.cursomc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ighorbrito.cursomc.controllers.utils.URL;
import com.ighorbrito.cursomc.domain.Produto;
import com.ighorbrito.cursomc.dto.ProdutoDTO;
import com.ighorbrito.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> findProduto(@PathVariable Integer id) {
		Produto obj = produtoService.searchById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPageCategoria(
			@RequestParam(value="nome", defaultValue = "") String nome,
			@RequestParam(value="categorias", defaultValue = "") String categorias,
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value="direction", defaultValue = "ASC") String direction) {
		
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		
		Page<ProdutoDTO> list = produtoService.searchByNameCategoria(nomeDecoded, ids, page, linesPerPage, orderBy, direction)
								.map(c -> new ProdutoDTO(c));
		return ResponseEntity.ok().body(list);
	}
	
}
