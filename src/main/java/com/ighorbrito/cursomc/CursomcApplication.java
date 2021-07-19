package com.ighorbrito.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ighorbrito.cursomc.domain.Categoria;
import com.ighorbrito.cursomc.domain.Cidade;
import com.ighorbrito.cursomc.domain.Cliente;
import com.ighorbrito.cursomc.domain.Endereco;
import com.ighorbrito.cursomc.domain.Estado;
import com.ighorbrito.cursomc.domain.ItemPedido;
import com.ighorbrito.cursomc.domain.Pagamento;
import com.ighorbrito.cursomc.domain.PagamentoComBoleto;
import com.ighorbrito.cursomc.domain.PagamentoComCartao;
import com.ighorbrito.cursomc.domain.Pedido;
import com.ighorbrito.cursomc.domain.Produto;
import com.ighorbrito.cursomc.domain.enums.EstadoPagamento;
import com.ighorbrito.cursomc.domain.enums.TipoCliente;
import com.ighorbrito.cursomc.repositories.CategoriaRepository;
import com.ighorbrito.cursomc.repositories.CidadeRepository;
import com.ighorbrito.cursomc.repositories.ClienteRepository;
import com.ighorbrito.cursomc.repositories.EnderecoRepository;
import com.ighorbrito.cursomc.repositories.EstadoRepository;
import com.ighorbrito.cursomc.repositories.ItemPedidoRepository;
import com.ighorbrito.cursomc.repositories.PagamentoRepository;
import com.ighorbrito.cursomc.repositories.PedidoRepository;
import com.ighorbrito.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria ctg1 = new Categoria(null, "Informática");
		Categoria ctg2 = new Categoria(null, "Escritório");
		Categoria ctg3 = new Categoria(null, "Cama, mesa e banho");
		Categoria ctg4 = new Categoria(null, "Eletrônicos");
		Categoria ctg5 = new Categoria(null, "Jardinagem");
		Categoria ctg6 = new Categoria(null, "Decoração");
		Categoria ctg7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de Escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV True Color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajur", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		
		
		ctg1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		ctg2.getProdutos().addAll(Arrays.asList(p2, p4));
		ctg3.getProdutos().addAll(Arrays.asList(p5, p6));
		ctg4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		ctg5.getProdutos().addAll(Arrays.asList(p8));
		ctg6.getProdutos().addAll(Arrays.asList(p9, p10));
		ctg7.getProdutos().addAll(Arrays.asList(p11));
		
		
		p1.getCategorias().addAll(Arrays.asList(ctg1, ctg4));
		p2.getCategorias().addAll(Arrays.asList(ctg1, ctg2, ctg4));
		p3.getCategorias().addAll(Arrays.asList(ctg1, ctg4));
		p4.getCategorias().addAll(Arrays.asList(ctg2));
		p5.getCategorias().addAll(Arrays.asList(ctg3));
		p6.getCategorias().addAll(Arrays.asList(ctg3));
		p7.getCategorias().addAll(Arrays.asList(ctg4));
		p8.getCategorias().addAll(Arrays.asList(ctg5));
		p9.getCategorias().addAll(Arrays.asList(ctg6));
		p10.getCategorias().addAll(Arrays.asList(ctg6));
		p11.getCategorias().add(ctg7);
		
		categoriaRepository.saveAll(Arrays.asList(ctg1, ctg2, ctg3, ctg4, ctg5, ctg6, ctg7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "Campinas", est2);
		Cidade c3 = new Cidade(null, "São Paulo", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cl1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA);
		
		cl1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cl1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cl1, c2);
		
		cl1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		
		
		clienteRepository.save(cl1);
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cl1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cl1, e2);
		
		Pagamento pgt1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgt1);
		
		Pagamento pgt2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pgt2);
		
		cl1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgt1, pgt2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}