package com.ighorbrito.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ighorbrito.cursomc.domain.Cliente;
import com.ighorbrito.cursomc.repositories.ClienteRepository;
import com.ighorbrito.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente searchById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto de ID "+ id + " não encontrado " +
				"Tipo: " + Cliente.class.getName()));
	}
}
