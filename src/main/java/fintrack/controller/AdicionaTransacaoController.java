package fintrack.controller;

import fintrack.dao.TransacoesDAO;
import fintrack.util.Navegador;
import fintrack.model.Transacao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.function.UnaryOperator;

public class AdicionaTransacaoController {
    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtValor;

    @FXML
    private ComboBox<String> comboTipo;

    @FXML
    private DatePicker dpData;

    TransacoesDAO transacoesDAO = new TransacoesDAO();

    private static Transacao transacaoEmEdicao = null;

    public static void setTransacaoParaEdicao(Transacao transacao) {
        transacaoEmEdicao = transacao;
    }

    @FXML
    public void initialize() {
        comboTipo.setItems(FXCollections.observableArrayList("Receita", "Despesa"));
        dpData.setValue(LocalDate.now());

        //sem texto no campo de valores
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String novoTexto = change.getControlNewText();
            if (novoTexto.matches("\\d*([.,]\\d*)?")) {
                return change;
            }
            return null;
        };

        txtValor.setTextFormatter(new TextFormatter<>(filtro));

        if (transacaoEmEdicao != null) {
            txtDescricao.setText(transacaoEmEdicao.getDecricao());
            txtValor.setText(String.valueOf(transacaoEmEdicao.getValor()));
            comboTipo.setValue(transacaoEmEdicao.isEhReceita() ? "Receita" : "Despesa");
            dpData.setValue(transacaoEmEdicao.getData());
        }
    }

    @FXML
    private void salvarTransacao(ActionEvent event){
        try {
            if (txtDescricao.getText().isBlank() || txtValor.getText().isBlank() || comboTipo.getValue() == null || dpData.getValue() == null) {
                exibirAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Por favor, preencha todos os campos.");
                return;
            }

            String descricao = txtDescricao.getText();
            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
            boolean ehReceita = "Receita".equalsIgnoreCase(comboTipo.getValue());
            LocalDate data = dpData.getValue();

            if (transacaoEmEdicao == null) {
                Transacao novaTransacao = new Transacao(descricao, valor, ehReceita, data);
                transacoesDAO.salvarTransacao(novaTransacao);
                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Transação cadastrada com sucesso!");
            } else {
                Transacao transacaoAtualizada = new Transacao(descricao, valor, ehReceita, data);
                transacoesDAO.atualizarTransacao(transacaoEmEdicao.getId(), transacaoAtualizada);
                transacaoEmEdicao = null;
                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Transação atualizada com sucesso!");
            }
            Navegador.trocarTela(event, "/main.fxml");

        } catch (NumberFormatException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro de Formatação", "Digite um valor numérico válido (ex: 150.50).");
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Banco", "Não foi possível salvar no banco de dados: " + e.getMessage());
        }
    }

    @FXML
    private void voltarParaMain(ActionEvent event) {
        transacaoEmEdicao = null;
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