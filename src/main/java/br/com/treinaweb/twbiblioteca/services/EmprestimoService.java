package br.com.treinaweb.twbiblioteca.services;


import java.time.LocalDate;
import java.util.List;

import br.com.treinaweb.twbiblioteca.models.Cliente;
import br.com.treinaweb.twbiblioteca.models.Emprestimo;
import br.com.treinaweb.twbiblioteca.models.Obra;
import br.com.treinaweb.twbiclioteca.dao.EmprestimoDAO;

public class EmprestimoService {
	
	private EmprestimoDAO emprestimoDAO;
	
	private NotificacaoService notificacaoService;
	
	public EmprestimoService(EmprestimoDAO emprestimoDAO, NotificacaoService notificacaoService) {
		this.emprestimoDAO = emprestimoDAO;
		this.notificacaoService = notificacaoService;
	}
	
	public Emprestimo novo(Cliente cliente, List<Obra> obras) {
		if(cliente == null) {
			throw new IllegalArgumentException("Cliente não pode ser nulo");
		}
		
		if(obras == null || obras.isEmpty()) {
			throw new IllegalArgumentException("Obra não pode ser nulo e nem vazio");
		}
		
		
		var emprestimo = new Emprestimo();
		var dataEmprestimo = LocalDate.now();
		
		var diasParaDevolucao = cliente.getReputacao().obterDiasParaDevolucao();		
		
		//var dataDevolucao = dataEmprestimo.plusDays(DIAS_PARA_DEVOLUCAO);
		var dataDevolucao = dataEmprestimo.plusDays(diasParaDevolucao);
		
		emprestimo.setCliente(cliente);
		emprestimo.setLivros(obras);
		emprestimo.setDataEmprestimo(dataEmprestimo);
		emprestimo.setDataDevolucao(dataDevolucao);
		
		return emprestimo;
	}
	
	public void notificarAtrasos() {
		var hoje = LocalDate.now();
		var emprestimos =  emprestimoDAO.buscarTodos();
		
		for (Emprestimo emprestimo : emprestimos) {
			var estaAtrasado = emprestimo.getDataDevolucao().isBefore(hoje);
			if(estaAtrasado) {
				notificacaoService.notificar(emprestimo);
			}
		}
		
	}
}
