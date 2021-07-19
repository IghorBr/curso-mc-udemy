package com.ighorbrito.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.ighorbrito.cursomc.controllers.exceptions.FieldMessage;
import com.ighorbrito.cursomc.domain.Cliente;
import com.ighorbrito.cursomc.dto.ClienteDTO;
import com.ighorbrito.cursomc.repositories.ClienteRepository;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		@SuppressWarnings("unchecked")
		Map<String, String> mapAtributos = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		Integer idUri = Integer.parseInt(mapAtributos.get("id"));

		Cliente cliAux = clienteRepository.findByEmail(objDto.getEmail());

		if (cliAux != null && !cliAux.getId().equals(idUri)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		// inclua os testes aqui, inserindo erros na lista

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}