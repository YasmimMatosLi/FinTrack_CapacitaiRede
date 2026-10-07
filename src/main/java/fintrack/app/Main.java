package fintrack.app;

import fintrack.controller.FinTracker;
import fintrack.exceptions.EntradaInvalidaException;
import fintrack.model.Transacao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
        //Scanner scanner = new Scanner(System.in);
        //FinTracker finTracker = new FinTracker();
        //int resposta;
//        do {
//            System.out.println("""
//                    ===== FINTRACK - SEU CONTROLE FINANCEIRO =====
//                    1. Adicionar nova transação
//                    2. Listar transações
//                    3. Mostrar saldo atual
//                    4. Remover transação
//                    5. Sair
//                    """);
//            resposta = scanner.nextInt();
//
//            switch (resposta){
//                case 1:
//                    try {
//                        System.out.println("Descrição: ");
//                        String descricao = scanner.next();
//
//                        System.out.println("É receita? ");
//                        String opcao = scanner.next();
//                        boolean ehReceita;
//                        if (opcao.equalsIgnoreCase("sim")) {
//                            ehReceita = true;
//                        } else {
//                            ehReceita = false;
//                        }
//
//                        System.out.println("Valor: ");
//                        double valor = scanner.nextDouble();
//
//                        LocalDate data = LocalDate.now();
//
//                        Transacao transacao = new Transacao(descricao, valor, ehReceita, data);
//
//                        finTracker.adicionarTransacao(transacao);
//                        System.out.println("Transação bem sucedida");
//                        break;
//                    }catch (EntradaInvalidaException e){
//                        System.out.println("Erro: " + e.getMessage());
//                    }
//                case 2:
//                    System.out.println("Lista das transações: ");
//                    finTracker.listarTransacoes();
//                    break;
//                case 3:
//                    System.out.println("Esse é o seu saldo atual: ");
//                    System.out.println(finTracker.calcularSaldoTotal());
//                    break;
//                case 4:
//                    System.out.println("Insira o número da transação que quer remover: ");
//                    int indice = scanner.nextInt();
//                    finTracker.removerTransacao(indice);
//                    System.out.println("Transação removida com sucesso");
//                    break;
//            }
//        }while (resposta != 5);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);

        stage.setTitle("FinTrack");
        stage.setScene(scene);
        stage.show();
    }
}