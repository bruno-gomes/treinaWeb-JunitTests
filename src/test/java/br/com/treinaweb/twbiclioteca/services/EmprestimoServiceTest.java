package br.com.treinaweb.twbiclioteca.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.treinaweb.twbiblioteca.enums.Reputacao;
import br.com.treinaweb.twbiblioteca.enums.Tipo;
import br.com.treinaweb.twbiblioteca.models.Autor;
import br.com.treinaweb.twbiblioteca.models.Cliente;
import br.com.treinaweb.twbiblioteca.models.Obra;
import br.com.treinaweb.twbiblioteca.services.EmprestimoService;

public class EmprestimoServiceTest {
	
	private EmprestimoService service =  null;
	
	@BeforeEach
	private void setup() {
		//System.out.println("Antes do metodo");
		service = new EmprestimoService();
	}
	
//	@AfterEach
//	private void depoisDeCadaMetodo() {
//		System.out.println("Depois do metodo");
//	}
//	
//	@BeforeAll
//	private static void antesDaClasse() {
//		System.out.println("Antes da classe ser instanciada");
//	}
//	
//	@AfterAll
//	private static void depoisDaClasse() {
//		System.out.println("Depois da classe ser destruida");
//	}
	
	@Test
	void quandoMetodoNovoChamadoDeveRetornarUmEmprestimo() {
		//cenario
		//var service = new EmprestimoService();
		var cliente = new Cliente(1L, "Cliente Teste", LocalDate.now(), "123.456.789-11", Reputacao.REGULAR);
		var autor = new Autor(1L,  "Autor Teste", LocalDate.now(), null);
		var obra = new Obra(1L, "Obra teste", 100, Tipo.LIVRO, autor);
		
		//execucao
		var emprestimo = service.novo(cliente, List.of(obra));
		
		//verificação
		assertNotNull(emprestimo);
		assertEquals(cliente, emprestimo.getCliente());
		assertEquals(List.of(obra), emprestimo.getLivros());
		assertEquals( LocalDate.now(), emprestimo.getDataEmprestimo());
		assertEquals( LocalDate.now().plusDays(3), emprestimo.getDataDevolucao());
		
	}
	
	@Test
	void quandoMetodoNovoChamadoComClienteReputacaoRuimDeveRetornarEmprestimoComDevolucaoDeUmDia() {
		//cenario
		//var service = new EmprestimoService();
		var cliente = new Cliente(1L, "Cliente Teste", LocalDate.now(), "123.456.789-11", Reputacao.RUIM);
		var autor = new Autor(1L,  "Autor Teste", LocalDate.now(), null);
		var obra = new Obra(1L, "Obra teste", 100, Tipo.LIVRO, autor);
		
		//execucao
		var emprestimo = service.novo(cliente, List.of(obra));
		
		//verificacao
		assertEquals(LocalDate.now().plusDays(1), emprestimo.getDataDevolucao());
	}
	
	@Test
	void quandoMetodoNovoChamadoComClienteReputacaoRegularDeveRetornarEmprestimoComDevolucaoDeTresDia() {
		//cenario
		//var service = new EmprestimoService();
		var cliente = new Cliente(1L, "Cliente Teste", LocalDate.now(), "123.456.789-11", Reputacao.REGULAR);
		var autor = new Autor(1L,  "Autor Teste", LocalDate.now(), null);
		var obra = new Obra(1L, "Obra teste", 100, Tipo.LIVRO, autor);
		
		//execucao
		var emprestimo = service.novo(cliente, List.of(obra));
		
		//verificacao
		assertEquals(LocalDate.now().plusDays(3), emprestimo.getDataDevolucao());
	}
	
	@Test
	void quandoMetodoNovoChamadoComClienteReputacaoBoaDeveRetornarEmprestimoComDevolucaoDeCincoDia() {
		//cenario
		//var service = new EmprestimoService();
		var cliente = new Cliente(1L, "Cliente Teste", LocalDate.now(), "123.456.789-11", Reputacao.BOA);
		var autor = new Autor(1L,  "Autor Teste", LocalDate.now(), null);
		var obra = new Obra(1L, "Obra teste", 100, Tipo.LIVRO, autor);
		
		//execucao
		var emprestimo = service.novo(cliente, List.of(obra));
		
		//verificacao
		assertEquals(LocalDate.now().plusDays(5), emprestimo.getDataDevolucao());
	}

	@Test
	void quandoMetodoNovoForChamadoComObraNullDeveLancarExcecaoDoTipoIllegaArgumentException() {

		//cenario
		//var service = new EmprestimoService();
		var cliente = new Cliente(1L, "Cliente Teste", LocalDate.now(), "123.456.789-11", Reputacao.BOA);
		var mensagemEsperada = "Obra não pode ser nulo e nem vazio";
		
		//execucao
		
		var exception = assertThrows(IllegalArgumentException.class, () ->  service.novo(cliente, null));
		assertEquals(mensagemEsperada, exception.getMessage());

	}
	
	@Test
	void quandoMetodoNovoForChamadoComObraVaziaDeveLancarExcecaoDoTipoIllegaArgumentException() {

		//cenario
		//var service = new EmprestimoService();
		var cliente = new Cliente(1L, "Cliente Teste", LocalDate.now(), "123.456.789-11", Reputacao.BOA);
		var obras = new ArrayList<Obra>();
		var mensagemEsperada = "Obra não pode ser nulo e nem vazio";
		
		//execucao
		
		var exception = assertThrows(IllegalArgumentException.class, () ->  service.novo(cliente, obras));
		assertEquals(mensagemEsperada, exception.getMessage());

	}
	
	@Test
	void quandoMetodoNovoForChamadoComClienteNullDeveLancarExcecaoDoTipoIllegaArgumentException() {

		//cenario
		//var service = new EmprestimoService();
		var autor = new Autor(1L,  "Autor Teste", LocalDate.now(), null);
		var obra = new Obra(1L, "Obra teste", 100, Tipo.LIVRO, autor);
		var mensagemEsperada = "Cliente não pode ser nulo";
		
		//execucao
		
		var exception = assertThrows(IllegalArgumentException.class, () ->  service.novo(null, List.of(obra)));
		assertEquals(mensagemEsperada, exception.getMessage());

	}


}
