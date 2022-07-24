package br.com.treinaweb.twbiclioteca.builders;

import java.time.LocalDate;
import java.util.List;

import br.com.treinaweb.twbiblioteca.models.Emprestimo;

public class EmprestimoBuilder {
	
	private Emprestimo emprestimo;
	
	public static EmprestimoBuilder builder() {
		var builder = new EmprestimoBuilder();
		var cliente = ClienteBuilder.builder().build();
		var obra = ObraBuilder.builder().build();
		var dataEmprestimo = LocalDate.now();
		var dataDevolucao =  LocalDate.now().plusDays(cliente.getReputacao().obterDiasParaDevolucao());
		
		var emprestimo = new Emprestimo(1l, cliente, List.of(obra), dataEmprestimo, dataDevolucao);
		builder.emprestimo =  emprestimo;
		
		return builder;
	}
	
	public Emprestimo build() {
		return emprestimo;
	}
	
	public EmprestimoBuilder dataDevolucao(LocalDate dataDevolucao) {
		this.emprestimo.setDataDevolucao(dataDevolucao);
		return this;
	}

}
