package com.example.atvd;

import com.example.atvd.entities.Cupom;
import com.example.atvd.entities.Produto;
import com.example.atvd.entities.Venda;
import com.example.atvd.repository.CupomRepository;
import com.example.atvd.repository.ProdutoRepository;
import com.example.atvd.repository.VendaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

	@BeforeAll
	public void setup(){
		produtoRepository.deleteAll();
		cupomRepository.deleteAll();
		vendaRepository.deleteAll();
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
			cupomRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

	@Test
	void ValidaCupomCPF() throws ParseException {
		Cupom n = new Cupom();
		String sDate1="31/12/1998";
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		n.setDataVenda(date1);
		n.setCpf("00000000000");
		n.setStatus("ATIVO");
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
	void ValidaCupomCPFCurto() throws ParseException {
		Cupom n = new Cupom();
		String sDate1="31/12/1998";
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		n.setDataVenda(date1);
		n.setCpf("000");
		n.setStatus("ATIVO");
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
	void ValidaCupomCPFCorreto() throws ParseException {
		Cupom n = new Cupom();
		String sDate1="31/12/1998";
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		n.setDataVenda(date1);
		n.setCpf("12136104438");
		n.setStatus("ATIVO");
		cupomRepository.save(n);
	}

	@Test
	void ObrigatoriedadeVenda(){
		Venda n = new Venda();
		boolean entrou = false;
		try {
			vendaRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

	@Test
	void BuscarCupomEItens() throws ParseException {
		Venda v = new Venda();
		Cupom c = new Cupom();
		String sDate1="31/12/1998";
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		c.setDataVenda(date1);
		c.setCpf("12136104438");
		c.setStatus("ATIVO");
		c.setId(2);
		cupomRepository.save(c);

		Produto p = new Produto();
		p.setNome("Teste");
		p.setPreco(1);
		p.setAliquota(0.32);
		p.setId(1);
		p.setCodigo(2012L);
		produtoRepository.save(p);

		v.setCupomId(2);
		v.setQuantidade(2);
		v.setPrecoFinal(2 * p.getPreco());
		v.setImpostoPago((int) (v.getPrecoFinal() * p.getAliquota()));
		vendaRepository.save(v);

		Iterable<Venda> vendas = vendaRepository.findAll();
		List<Venda> n_vendas = new ArrayList<Venda>();
		for (Venda venda:
				vendas) {
			if(venda.getCupomId() == 2){
				n_vendas.add(venda);
			}
		}
		Assertions.assertEquals(n_vendas.get(0).getId(), v.getId());
	}

}
