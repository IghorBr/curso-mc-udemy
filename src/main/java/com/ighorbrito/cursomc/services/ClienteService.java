package com.ighorbrito.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ighorbrito.cursomc.domain.Cliente;
import com.ighorbrito.cursomc.dto.ClienteDTO;
import com.ighorbrito.cursomc.repositories.ClienteRepository;
import com.ighorbrito.cursomc.services.exceptions.DataIntegratityException;
import com.ighorbrito.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente searchById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto de ID " + id + " não encontrado " + "Tipo: " + Cliente.class.getName()));
	}

	public Cliente insertCliente(Cliente obj) {
		obj.setId(null);
		return clienteRepository.save(obj);
	}

	public Cliente updateCliente(Cliente obj) {
		Cliente newCliente = searchById(obj.getId());
		updateData(newCliente, obj);

		return clienteRepository.save(newCliente);
	}

	public void deleteCliente(Integer id) {
		searchById(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegratityException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	public List<Cliente> searchAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> searchPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);

	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}

	private void updateData(Cliente newCliente, Cliente obj) {
		newCliente.setNome(obj.getNome());
		newCliente.setEmail(obj.getEmail());
	}
}
