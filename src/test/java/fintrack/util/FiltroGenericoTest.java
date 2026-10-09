package fintrack.util;

import fintrack.model.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltroGenericoTest {

    private FiltroGenerico<Transacao> filtro;
    private List<Transacao> transacoes;

    @BeforeEach
    void setUp() {
        filtro = new FiltroGenerico<>();
        transacoes = new ArrayList<>();
        transacoes.add(new Transacao("Salário", 3000.0, true, LocalDate.now()));
        transacoes.add(new Transacao("Freelance", 500.0, true, LocalDate.now()));
        transacoes.add(new Transacao("Restaurante", 85.0, false, LocalDate.now()));
        transacoes.add(new Transacao("Gasolina", 150.0, false, LocalDate.now()));
    }

    @Test
    @DisplayName("Deve filtrar apenas transações do tipo Receita")
    void testFiltrarApenasReceitas() {
        List<Transacao> resultado = filtro.aplicarFiltro(transacoes, Transacao::isEhReceita);

        assertEquals(2, resultado.size(), "Deveria retornar exatamente 2 receitas");
        assertTrue(resultado.stream().allMatch(Transacao::isEhReceita), "Todos os itens devem ser receitas");
    }

    @Test
    @DisplayName("Deve filtrar apenas transações do tipo Despesa")
    void testFiltrarApenasDespesas() {
        List<Transacao> resultado = filtro.aplicarFiltro(transacoes, t -> !t.isEhReceita());

        assertEquals(2, resultado.size(), "Deveria retornar exatamente 2 despesas");
        assertFalse(resultado.stream().anyMatch(Transacao::isEhReceita), "Nenhum item deveria ser receita");
    }

    @Test
    @DisplayName("Deve retornar lista vazia ao aplicar um filtro sem correspondências")
    void testFiltroSemResultados() {
        List<Transacao> resultado = filtro.aplicarFiltro(transacoes, t -> t.getValor() > 10000.0);

        assertTrue(resultado.isEmpty(), "A lista resultante deveria estar vazia");
    }

    @Test
    @DisplayName("Deve tratar listas nulas sem lançar exceções")
    void testFiltroComListaNula() {
        List<Transacao> resultado = filtro.aplicarFiltro(null, Transacao::isEhReceita);
        assertNull(resultado, "Com lista nula o retorno deveria ser nulo sem estourar NullPointerException");
    }
}
