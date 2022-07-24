package br.com.treinaweb.twbiclioteca.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockitoSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.treinaweb.twbiblioteca.enums.Reputacao;
import br.com.treinaweb.twbiblioteca.models.Obra;
import br.com.treinaweb.twbiblioteca.services.EmprestimoService;
import br.com.treinaweb.twbiclioteca.builders.ClienteBuilder;
import br.com.treinaweb.twbiclioteca.builders.EmprestimoBuilder;
import br.com.treinaweb.twbiclioteca.builders.ObraBuilder;
import br.com.treinaweb.twbiclioteca.dao.EmprestimoDAO;

@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {
	
	@Mock
	private EmprestimoDAO emprestimoDAO;
	
	@InjectMocks
	private EmprestimoService service;
	
//	@BeforeEach
//	private void setup() {
//		//System.out.println("Antes do metodo");
//		service = new EmprestimoService(emprestimoDAO);
//	}
	
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
		var cliente = ClienteBuilder.builder().build();
		//var autor = AutorBuilder.builder().build();
		var obra = ObraBuilder.builder().build();
				
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
		//var cliente = new Cliente(1L, "Cliente Teste", LocalDate.now(), "123.456.789-11", Reputacao.RUIM);
		var cliente  = ClienteBuilder.builder().reputacao(Reputacao.RUIM).build();
		//var autor = new Autor(1L,  "Autor Teste", LocalDate.now(), null);
		var obra = ObraBuilder.builder().build();
		
		//execucao
		var emprestimo = service.novo(cliente, List.of(obra));
		
		//verificacao
		assertEquals(LocalDate.now().plusDays(1), emprestimo.getDataDevolucao());
	}
	
	@Test
	void quandoMetodoNovoChamadoComClienteReputacaoRegularDeveRetornarEmprestimoComDevolucaoDeTresDia() {
		//cenario
		//var service = new EmprestimoService();
		var cliente = ClienteBuilder.builder().build();
		//var autor = new Autor(1L,  "Autor Teste", LocalDate.now(), null);
		var obra = ObraBuilder.builder().build();
		
		//execucao
		var emprestimo = service.novo(cliente, List.of(obra));
		
		//verificacao
		assertEquals(LocalDate.now().plusDays(3), emprestimo.getDataDevolucao());
	}
	
	@Test
	void quandoMetodoNovoChamadoComClienteReputacaoBoaDeveRetornarEmprestimoComDevolucaoDeCincoDia() {
		//cenario
		//var service = new EmprestimoService();
		var cliente = ClienteBuilder.builder().reputacao(Reputacao.BOA).build();
		//var autor = new Autor(1L,  "Autor Teste", LocalDate.now(), null);
		var obra = ObraBuilder.builder().build();
		
		//execucao
		var emprestimo = service.novo(cliente, List.of(obra));
		
		//verificacao
		assertEquals(LocalDate.now().plusDays(5), emprestimo.getDataDevolucao());
	}

	@Test
	void quandoMetodoNovoForChamadoComObraNullDeveLancarExcecaoDoTipoIllegaArgumentException() {

		//cenario
		//var service = new EmprestimoService();
		var cliente = ClienteBuilder.builder().reputacao(Reputacao.BOA).build();
		var mensagemEsperada = "Obra não pode ser nulo e nem vazio";
		
		//execucao
		
		var exception = assertThrows(IllegalArgumentException.class, () ->  service.novo(cliente, null));
		assertEquals(mensagemEsperada, exception.getMessage());

	}
	
	@Test
	void quandoMetodoNovoForChamadoComObraVaziaDeveLancarExcecaoDoTipoIllegaArgumentException() {

		//cenario
		//var service = new EmprestimoService();
		var cliente = ClienteBuilder.builder().build();
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
		//var autor = new Autor(1L,  "Autor Teste", LocalDate.now(), null);
		var obra = ObraBuilder.builder().build();
		var mensagemEsperada = "Cliente não pode ser nulo";
		
		//execucao
		
		var exception = assertThrows(IllegalArgumentException.class, () ->  service.novo(null, List.of(obra)));
		assertEquals(mensagemEsperada, exception.getMessage());

	}
	
	@Test
	void quandoMetodoNotificarAtrasoForChamadoDeveRetornarNumeroNotificacoes() {
		//cenario
		var emprestimos = List.of(
				EmprestimoBuilder.builder().build(),
				EmprestimoBuilder.builder().dataDevolucao(LocalDate.now().minusDays(1)).build(),
				EmprestimoBuilder.builder().dataDevolucao(LocalDate.now().minusDays(1)).build()
				);
		
		Mockito.when(emprestimoDAO.buscarTodos()).thenReturn(emprestimos);
		
		//execucao
		var notificacoes = service.notificarAtrasos();
		
		//verificacao
		assertEquals(2 , notificacoes);
	}


}
