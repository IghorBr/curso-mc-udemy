package com.ighorbrito.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ighorbrito.cursomc.domain.Cliente;
import com.ighorbrito.cursomc.domain.ItemPedido;
import com.ighorbrito.cursomc.domain.PagamentoComBoleto;
import com.ighorbrito.cursomc.domain.Pedido;
import com.ighorbrito.cursomc.domain.enums.EstadoPagamento;
import com.ighorbrito.cursomc.repositories.ItemPedidoRepository;
import com.ighorbrito.cursomc.repositories.PagamentoRepository;
import com.ighorbrito.cursomc.repositories.PedidoRepository;
import com.ighorbrito.cursomc.security.UserSS;
import com.ighorbrito.cursomc.services.exceptions.AuthorizationException;
import com.ighorbrito.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	
	public Pedido searchById(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto de ID "+ id + " não encontrado " +
				"Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insertPedido(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstant(new Date());
		
		pedido.setCliente(clienteService.searchById(pedido.getCliente().getId()));
		
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
			ip.setProduto(produtoService.searchById(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		
		return pedido;
		
	}
	
	public Page<Pedido> searchPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		
		if (user == null)
			throw new AuthorizationException("Acesso Negado");
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.searchById(user.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
}
