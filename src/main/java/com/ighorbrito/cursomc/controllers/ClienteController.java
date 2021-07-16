package com.ighorbrito.cursomc.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ighorbrito.cursomc.domain.Cliente;
import com.ighorbrito.cursomc.dto.ClienteDTO;
import com.ighorbrito.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> findCliente(@PathVariable Integer id) {
		Cliente obj = clienteService.searchById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insertCliente(@RequestBody @Valid ClienteDTO objDto) {
		Cliente objCliente = clienteService.fromDTO(objDto); 
		objCliente = clienteService.insertCliente(objCliente);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(objCliente.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateCliente(@PathVariable Integer id, @RequestBody @Valid ClienteDTO objDto) {
		Cliente objCliente = clienteService.fromDTO(objDto);
		
		objCliente.setId(id);
		objCliente = clienteService.updateCliente(objCliente);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
		clienteService.deleteCliente(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAllCliente() {
		List<ClienteDTO> list = clienteService.searchAll()
									.stream().map(c -> new ClienteDTO(c)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPageCliente(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value="direction", defaultValue = "ASC") String direction) {
		
		Page<ClienteDTO> list = clienteService.searchPage(page, linesPerPage, orderBy, direction)
								.map(c -> new ClienteDTO(c));
		return ResponseEntity.ok().body(list);
	}
	
}
