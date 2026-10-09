package fintrack.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransacaoTest {

    private Transacao receita;
    private Transacao despesa;

    @BeforeEach
    void setUp() {
        receita = new Transacao("Salário", 3000.0, true, LocalDate.now());
        despesa = new Transacao("Aluguel", 1200.0, false, LocalDate.now());
    }

    @Test
    @DisplayName("Deve verificar os atributos básicos de uma transação")
    void testCriacaoTransacao() {
        assertEquals("Salário", receita.getDecricao());
        assertEquals(3000.0, receita.getValor());
        assertTrue(receita.isEhReceita());
        assertNotNull(receita.getData());
    }

    @Test
    @DisplayName("Deve identificar corretamente se a transação é receita ou despesa")
    void testTipoTransacao() {
        assertTrue(receita.isEhReceita(), "A transação de Salário deve ser uma receita");
        assertFalse(despesa.isEhReceita(), "A transação de Aluguel deve ser uma despesa");
    }

    @Test
    @DisplayName("Deve calcular o saldo total corretamente a partir de uma lista")
    void testCalculoSaldoTotal() {
        List<Transacao> lista = List.of(
                new Transacao("Salário", 2500.0, true, LocalDate.now()),
                new Transacao("Mercado", 400.0, false, LocalDate.now()),
                new Transacao("Conta de Luz", 100.0, false, LocalDate.now())
        );

        double totalReceitas = lista.stream()
                .filter(Transacao::isEhReceita)
                .mapToDouble(Transacao::getValor)
                .sum();

        double totalDespesas = lista.stream()
                .filter(t -> !t.isEhReceita())
                .mapToDouble(Transacao::getValor)
                .sum();

        double saldoCalculado = totalReceitas - totalDespesas;

        assertEquals(2500.0, totalReceitas, "Total de receitas incorreto");
        assertEquals(500.0, totalDespesas, "Total de despesas incorreto");
        assertEquals(2000.0, saldoCalculado, "Cálculo do saldo total incorreto");
    }
}
