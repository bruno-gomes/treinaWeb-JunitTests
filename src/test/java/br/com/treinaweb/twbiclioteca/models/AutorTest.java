package br.com.treinaweb.twbiclioteca.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.treinaweb.twbiblioteca.models.Autor;

public class AutorTest {

	@Test
     void quandoMetodoEstaVivoForChamadoComDataFalecimentoNuloDeveRetornarTrue(){
		//cenario
		var autor = new Autor();        
		//execução
        var estarVivo = autor.estaVivo();
		//verificação        
        assertTrue( estarVivo);
    }
	
	@Test
	 void quandoMetodoEstaVivoComDataFacelimentoPreenchidaDeveRetornarFalse() {
		//cenario
		var autor = new Autor();
		autor.setDataFalecimento(LocalDate.now());
		//execução
		var estaVivo = autor.estaVivo();
		//verificação
		assertFalse( estaVivo);
	}
}
