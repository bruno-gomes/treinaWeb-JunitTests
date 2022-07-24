package br.com.treinaweb.twbiclioteca.dao;

import java.util.List;

import br.com.treinaweb.twbiblioteca.models.Emprestimo;

public interface EmprestimoDAO {
	
	List<Emprestimo> buscarTodos();

}
