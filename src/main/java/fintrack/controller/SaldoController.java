package fintrack.controller;

import fintrack.dao.TransacoesDAO;
import fintrack.model.Transacao;
import fintrack.util.Navegador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.List;

public class SaldoController {

    @FXML
    private Label lblSaldoTotal;

    @FXML
    private Label lblTotalReceitas;

    @FXML
    private Label lblTotalDespesas;

    private TransacoesDAO dao = new TransacoesDAO();

    @FXML
    public void initialize() {
        carregarDadosDoBanco();
    }

    private void carregarDadosDoBanco() {
        try {
            List<Transacao> transacoes = dao.listarTodas();

            double totalReceitas = 0.0;
            double totalDespesas = 0.0;

            for (Transacao t : transacoes) {
                if (t.isEhReceita()) {
                    totalReceitas += t.getValor();
                } else {
                    totalDespesas += t.getValor();
                }
            }

            double saldoTotal = totalReceitas - totalDespesas;

            lblTotalReceitas.setText(String.format("R$ %.2f", totalReceitas));
            lblTotalDespesas.setText(String.format("R$ %.2f", totalDespesas));
            lblSaldoTotal.setText(String.format("R$ %.2f", saldoTotal));

            if (saldoTotal < 0) {
                lblSaldoTotal.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #C62828;");
            } else {
                lblSaldoTotal.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2E7D32;");
            }

        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Banco", "Não foi possível carregar os dados do saldo: " + e.getMessage());
        }
    }

    @FXML
    private void voltarParaMain(ActionEvent event) {
        Navegador.trocarTela(event, "/main.fxml");
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}