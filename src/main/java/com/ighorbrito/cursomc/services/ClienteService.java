package com.ighorbrito.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ighorbrito.cursomc.domain.Cidade;
import com.ighorbrito.cursomc.domain.Cliente;
import com.ighorbrito.cursomc.domain.Endereco;
import com.ighorbrito.cursomc.domain.enums.Perfil;
import com.ighorbrito.cursomc.domain.enums.TipoCliente;
import com.ighorbrito.cursomc.dto.ClienteDTO;
import com.ighorbrito.cursomc.dto.ClienteNewDTO;
import com.ighorbrito.cursomc.repositories.ClienteRepository;
import com.ighorbrito.cursomc.repositories.EnderecoRepository;
import com.ighorbrito.cursomc.security.UserSS;
import com.ighorbrito.cursomc.services.exceptions.AuthorizationException;
import com.ighorbrito.cursomc.services.exceptions.DataIntegratityException;
import com.ighorbrito.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	public Cliente searchById(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Optional<Cliente> obj = clienteRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto de ID " + id + " não encontrado " + "Tipo: " + Cliente.class.getName()));
	}

	public Cliente insertCliente(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipoCliente()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);

		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());

		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	private void updateData(Cliente newCliente, Cliente obj) {
		newCliente.setNome(obj.getNome());
		newCliente.setEmail(obj.getEmail());
	}
}
