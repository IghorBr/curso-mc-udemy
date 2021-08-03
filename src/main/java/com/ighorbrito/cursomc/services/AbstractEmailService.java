package com.ighorbrito.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.ighorbrito.cursomc.domain.Cliente;
import com.ighorbrito.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage smm = prepareSimpleMailMessageFromPedido(pedido);
		
		sendEmail(smm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage smm = new SimpleMailMessage();
		
		smm.setTo(pedido.getCliente().getEmail());
		smm.setFrom(sender);
		smm.setSubject("Pedido " + pedido.getId() + " confirmado!");
		smm.setSentDate(new Date(System.currentTimeMillis()));
		smm.setText(pedido.toString());
		
		return smm;
	}
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage smm = prepareNewPasswordEmail(cliente, newPass);
		
		sendEmail(smm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage smm = new SimpleMailMessage();
		
		smm.setTo(cliente.getEmail());
		smm.setFrom(sender);
		smm.setSubject("Solicitação de nova senha.");
		smm.setSentDate(new Date(System.currentTimeMillis()));
		smm.setText("Nova senha: " + newPass);
		
		return smm;
	}
}
