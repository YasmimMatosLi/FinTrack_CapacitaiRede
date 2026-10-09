package fintrack.controller;

import fintrack.dao.TransacoesDAO;
import fintrack.repository.RepositoryGeneric;
import fintrack.util.FiltroGenerico;
import fintrack.util.Navegador;
import fintrack.model.Transacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private TableView<Transacao> tabelaTransacoes;

    @FXML
    private TableColumn<Transacao, String> colunaDescricao;

    @FXML
    private TableColumn<Transacao, Double> colunaValor;

    @FXML
    private TableColumn<Transacao, Boolean> colunaTipo;

    @FXML
    private TableColumn<Transacao, LocalDate> colunaData;

    private TransacoesDAO dao = new TransacoesDAO();
    private ObservableList<Transacao> listaTransacoes = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Transacao, Void> colunaAcoes;

    // Guarda a lista completa carregada do banco de dados
    private List<Transacao> listaCompleta;

    private FiltroGenerico<Transacao> filtroGenerico = new FiltroGenerico<>();

    private RepositoryGeneric<Transacao> repositorio = new RepositoryGeneric<>();

    @FXML
    public void initialize() throws SQLException {
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("decricao"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("ehReceita"));
        colunaTipo.setCellFactory(column -> new TableCell<Transacao, Boolean>() {
            @Override
            protected void updateItem(Boolean ehReceita, boolean empty) {
                super.updateItem(ehReceita, empty);

                if (empty || ehReceita == null) {
                    setText(null);
                    setStyle("");
                } else {
                    if (ehReceita) {
                        setText("Receita");
                        setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
                    } else {
                        setText("Despesa");
                        setStyle("-fx-text-fill: #c62828; -fx-font-weight: bold;");
                    }
                }
            }
        });
        configurarColunaAcoes();


        carregarDadosDoBanco();
    }

    private void carregarDadosDoBanco() {
        try {
            listaCompleta = repositorio.listarTodos();

            if (listaCompleta == null) {
                listaCompleta = new ArrayList<>();
            }
            listaTransacoes.clear();
            listaTransacoes.addAll(repositorio.listarTodos());
            tabelaTransacoes.setItems(FXCollections.observableArrayList(listaCompleta));
        } catch (SQLException e) {
            System.err.println("Erro ao carregar dados do banco: " + e.getMessage());
        }
    }

    private void configurarColunaAcoes() {
        colunaAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Atualizar");
            private final Button btnRemover = new Button("Remover");
            private final HBox painelBotoes = new HBox(8, btnEditar, btnRemover);

            {
                painelBotoes.setAlignment(Pos.CENTER);

                btnEditar.setStyle("-fx-background-color: #FFF3E0; -fx-cursor: hand; -fx-text-fill: #E65100;");
                btnRemover.setStyle("-fx-background-color: #FFEBEE; -fx-cursor: hand; -fx-text-fill: #C62828;");

                btnRemover.setOnAction(event -> {
                    Transacao transacao = getTableView().getItems().get(getIndex());
                    removerTransacao(transacao);
                });

                btnEditar.setOnAction(event -> {
                    Transacao transacao = getTableView().getItems().get(getIndex());
                    editarTransacao(event, transacao);
                });
            }

            @Override
            protected void updateItem(Void item, boolean vazio) {
                super.updateItem(item, vazio);
                if (vazio) {
                    setGraphic(null);
                } else {
                    setGraphic(painelBotoes);
                }
            }
        });
    }

    private void removerTransacao(Transacao transacao) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Remover Transação");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Deseja realmente excluir: " + transacao.getDecricao() + "?");

        if (confirmacao.showAndWait().get() == ButtonType.OK) {
            try {
                repositorio.deletar(transacao.getId());
                listaTransacoes.remove(transacao);
                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Transação removida com sucesso!");
            } catch (SQLException e) {
                exibirAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao excluir do banco: " + e.getMessage());
            }
        }
    }

    private void editarTransacao(ActionEvent event, Transacao transacao) {
        AdicionaTransacaoController.setTransacaoParaEdicao(transacao);
        Navegador.trocarTela(event, "/adicionaTransacao.fxml");
    }

    @FXML
    private void abrirAdicionarTransacao(ActionEvent event) {
        AdicionaTransacaoController.setTransacaoParaEdicao(null);
        Navegador.trocarTela(event, "/adicionaTransacao.fxml");
    }

    @FXML
    private void filtrarTodas(ActionEvent event) {
        if (listaCompleta != null) {
            tabelaTransacoes.setItems(FXCollections.observableArrayList(listaCompleta));
        }
    }

    @FXML
    private void filtrarReceitas(ActionEvent event) {
        if (listaCompleta != null) {
            List<Transacao> receitas = filtroGenerico.aplicarFiltro(listaCompleta, Transacao::isEhReceita);
            tabelaTransacoes.setItems(FXCollections.observableArrayList(receitas));
        }
    }

    @FXML
    private void filtrarDespesas(ActionEvent event) {
        if (listaCompleta != null) {
            List<Transacao> despesas = filtroGenerico.aplicarFiltro(listaCompleta, t -> !t.isEhReceita());
            tabelaTransacoes.setItems(FXCollections.observableArrayList(despesas));
        }
    }

    @FXML
    private void abrirMostrarSaldo(ActionEvent event) {
        Navegador.trocarTela(event, "/saldo.fxml");
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }


}