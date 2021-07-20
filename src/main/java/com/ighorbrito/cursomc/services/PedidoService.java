package com.ighorbrito.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ighorbrito.cursomc.domain.ItemPedido;
import com.ighorbrito.cursomc.domain.PagamentoComBoleto;
import com.ighorbrito.cursomc.domain.Pedido;
import com.ighorbrito.cursomc.domain.enums.EstadoPagamento;
import com.ighorbrito.cursomc.repositories.ItemPedidoRepository;
import com.ighorbrito.cursomc.repositories.PagamentoRepository;
import com.ighorbrito.cursomc.repositories.PedidoRepository;
import com.ighorbrito.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido searchById(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto de ID "+ id + " não encontrado " +
				"Tipo: " + Pedido.class.getName()));
	}

	public Pedido insertPedido(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstant(new Date());
		
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamento(pagto, pedido.getInstant());
		}
		pedido = pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.searchById(ip.getProduto().getId()).getPreco());
			ip.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		
		return pedido;
		
	}
}
