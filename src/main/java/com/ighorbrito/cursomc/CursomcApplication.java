package com.ighorbrito.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ighorbrito.cursomc.domain.Categoria;
import com.ighorbrito.cursomc.domain.Cidade;
import com.ighorbrito.cursomc.domain.Estado;
import com.ighorbrito.cursomc.domain.Produto;
import com.ighorbrito.cursomc.repositories.CategoriaRepository;
import com.ighorbrito.cursomc.repositories.CidadeRepository;
import com.ighorbrito.cursomc.repositories.EstadoRepository;
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
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria ctg1 = new Categoria(null, "Informática");
		Categoria ctg2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		ctg1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		ctg2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().add(ctg1);
		p2.getCategorias().addAll(Arrays.asList(ctg1, ctg2));
		p3.getCategorias().add(ctg1);
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "Campinas", est2);
		Cidade c3 = new Cidade(null, "São Paulo", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		categoriaRepository.saveAll(Arrays.asList(ctg1, ctg2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
	}

}
