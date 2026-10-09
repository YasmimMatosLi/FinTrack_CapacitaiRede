package fintrack.repository;

import fintrack.dao.TransacoesDAO;
import fintrack.model.Transacao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryGeneric<T extends Transacao> {
    private final TransacoesDAO dao = new TransacoesDAO();

    public void adicionar(T elemento) throws SQLException {
        dao.salvarTransacao(elemento);
    }

    public void atualizar(int id, T elemento) throws SQLException {
        dao.atualizarTransacao(id, elemento);
    }

    public void deletar(int id) throws SQLException{
        dao.removerTransacao(id); //nao ta funcionando tem que ver
    }

    public List<T> listarTodos() throws SQLException {
        List<Transacao> lista = dao.listarTodas();
        return (List<T>) lista;
    }

}
