package com.ighorbrito.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ighorbrito.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preencherPagamento(PagamentoComBoleto pagamento, Date instant) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instant);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagamento.setDataVencimento(cal.getTime());
	}

}
