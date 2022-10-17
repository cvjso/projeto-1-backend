package com.example.atvd;

import com.example.atvd.entities.Cupom;
import com.example.atvd.entities.Produto;
import com.example.atvd.repository.CupomRepository;
import com.example.atvd.repository.ProdutoRepository;
import com.example.atvd.repository.VendaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class AtvdApplicationTests {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CupomRepository cupomRepository;

	@Autowired
	private VendaRepository vendaRepository;
	@Test
	void contextLoads() {
	}

	@Test
	void ObrigatoriedadeProduto(){
		Produto n = new Produto();
		boolean entrou = false;
		try {
			produtoRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

	@Test
	void ObrigatoriedadeProdutoNulosBrancos(){
		Produto n = new Produto();
		n.setNome("Teste");
		n.setPreco(1);
		n.setAliquota(0.32);
		boolean entrou = false;
		try {
			produtoRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

	@Test
	void ObrigatoriedadeCupom(){
		Cupom n = new Cupom();
		boolean entrou = false;
		try {
			cupomRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

	@Test
	void ObrigatoriedadeCupomNulosBrancos() throws ParseException {
		Cupom n = new Cupom();
		String sDate1="31/12/1998";
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		n.setDataVenda(date1);
		n.setCpf("11122233344");
		n.setStatus("ATIVO");
		boolean entrou = false;
		try {
			produtoRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

}
