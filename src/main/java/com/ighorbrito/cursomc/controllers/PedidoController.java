package com.ighorbrito.cursomc.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ighorbrito.cursomc.domain.Pedido;
import com.ighorbrito.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> findPedido(@PathVariable Integer id) {
		Pedido obj = pedidoService.searchById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insertPedido(@RequestBody @Valid Pedido pedido) {
		pedido = pedidoService.insertPedido(pedido);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(pedido.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
}
