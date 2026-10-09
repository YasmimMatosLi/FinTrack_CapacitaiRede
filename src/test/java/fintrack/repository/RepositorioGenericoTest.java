package fintrack.repository;

import fintrack.model.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioGenericoTest {

    private RepositoryGeneric<Transacao> repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new RepositoryGeneric<>();
    }

    @Test
    @DisplayName("Deve instanciar corretamente o repositório genérico")
    void testInstanciacaoRepositorio() {
        assertNotNull(repositorio, "O repositório genérico não deve ser nulo ao ser instanciado");
    }

    @Test
    @DisplayName("Deve criar um objeto de transação válido para ser utilizado no repositório")
    void testObjetoParaRepositorio() {
        Transacao t = new Transacao("Teste Repositório", 200.0, true, LocalDate.now());
        assertNotNull(t);
        assertEquals("Teste Repositório", t.getDecricao());
    }
}
