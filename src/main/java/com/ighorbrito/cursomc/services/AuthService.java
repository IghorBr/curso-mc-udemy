package com.ighorbrito.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ighorbrito.cursomc.domain.Cliente;
import com.ighorbrito.cursomc.repositories.ClienteRepository;
import com.ighorbrito.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder enconder;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente == null)
			throw new ObjectNotFoundException("Email não encontrado");
		
		String newPass = newPassword();
		cliente.setSenha(enconder.encode(newPass));
		clienteRepository.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPass);
		
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}
		
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		
		if (opt == 1)  //DIGITO
			return (char) (rand.nextInt(10) + 48);
		else if (opt == 2) //LETRA MAIUSCULA
			return (char) (rand.nextInt(26) + 65);
		else //LETRA MINUSCULA
			return (char) (rand.nextInt(26) + 97);
	}

}
