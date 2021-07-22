package com.ighorbrito.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.ighorbrito.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
