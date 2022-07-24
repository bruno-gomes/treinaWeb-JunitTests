package br.com.treinaweb.twbiclioteca.builders;

import java.time.LocalDate;

import br.com.treinaweb.twbiblioteca.enums.Reputacao;
import br.com.treinaweb.twbiblioteca.models.Cliente;

public class ClienteBuilder {

	private Cliente cliente;
	
	public static ClienteBuilder builder() {
		var builder = new ClienteBuilder();
		
		var cliente = new Cliente(1L, "Cliente Teste", LocalDate.now(), "123.456.789-11", Reputacao.REGULAR);
		builder.cliente = cliente;
		
		return builder;		
	}
	
	public Cliente build() {
		return cliente;
	}
}
