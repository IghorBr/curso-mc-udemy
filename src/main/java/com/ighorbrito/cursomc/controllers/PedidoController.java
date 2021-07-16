package com.ighorbrito.cursomc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
}
