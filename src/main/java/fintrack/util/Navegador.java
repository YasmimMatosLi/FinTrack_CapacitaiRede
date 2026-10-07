package fintrack.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navegador {
    public static void trocarTela(ActionEvent event, String caminhoFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(Navegador.class.getResource(caminhoFXML));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela: " + caminhoFXML);
            e.printStackTrace();
        }
    }
}
