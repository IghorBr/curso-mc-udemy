package com.ighorbrito.cursomc.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ighorbrito.cursomc.dto.CidadeDTO;
import com.ighorbrito.cursomc.dto.EstadoDTO;
import com.ighorbrito.cursomc.services.CidadeService;
import com.ighorbrito.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> searchAllByNome() {
		List<EstadoDTO> list = estadoService.searchAllByNome().stream()
							.map(x -> new EstadoDTO(x)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value="/{id}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> searchAllCidadesByEstado(@PathVariable Integer id) {
		List<CidadeDTO> list = cidadeService.searchCidadesByEstado(id).stream()
							.map(x -> new CidadeDTO(x)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(list);
	}
}
