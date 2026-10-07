package fintrack.dao;

import fintrack.model.Transacao;
import fintrack.util.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransacoesDAO {
    public void salvarTransacao(Transacao transacao) throws SQLException {
        Connection conn = ConexaoFactory.conectar();
        conn.setAutoCommit(false);

        try{
            String sql = "INSERT INTO transacoes (descricao, valor, tipo, data) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, transacao.getDecricao());
            stmt.setDouble(2, transacao.getValor());
            stmt.setString(3, transacao.isEhReceita() ? "RECEITA" : "DESPESA");
            stmt.setString(4, transacao.getData().toString());

            stmt.executeUpdate();
            conn.commit();
        }
        catch (SQLException e){
            conn.rollback();
            conn.close();
            System.out.println("Erro ao salvar informações: " + e.getMessage());
            System.out.println("Código do erro: " + e.getErrorCode());
            System.out.println("SQLState: " + e.getSQLState());

        }
    }

    public List<Transacao> listarTodas() throws SQLException {
        List<Transacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM transacoes ORDER BY data DESC";

        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("valor");
                String tipoStr = rs.getString("tipo");
                boolean ehReceita = "RECEITA".equalsIgnoreCase(tipoStr);
                LocalDate data = LocalDate.parse(rs.getString("data"));
                Transacao t = new Transacao(descricao, valor, ehReceita, data);
                t.setId(rs.getInt("id"));
                lista.add(t);
            }
        }
        return lista;
    }

    public void removerTransacao(int id) throws SQLException {
        String sql = "DELETE FROM transacoes WHERE id = ?";
        Connection conn = ConexaoFactory.conectar();
        conn.setAutoCommit(false);
        try {
             PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            conn.close();
            System.out.println("Algo deu errado ao deletar: " + e.getMessage());
        }
    }

    public void atualizarTransacao(int id, Transacao transacao) throws SQLException {
        String sql = "UPDATE transacoes SET descricao = ?, valor = ?, tipo = ?, data = ? WHERE id = ?";
        Connection conn = ConexaoFactory.conectar();
        conn.setAutoCommit(false);
        try {
             PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, transacao.getDecricao());
            stmt.setDouble(2, transacao.getValor());
            stmt.setString(3, transacao.isEhReceita() ? "RECEITA" : "DESPESA");
            stmt.setString(4, transacao.getData().toString());
            stmt.setInt(5, id);

            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            conn.close();
            System.out.println("Algo deu errado ao tentar atualizar: " + e.getMessage());
        }
    }
}
