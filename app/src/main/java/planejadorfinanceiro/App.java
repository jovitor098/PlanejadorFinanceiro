package planejadorfinanceiro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principal da aplicação JavaFX do Planejador Financeiro.
 * Inicializa a interface gráfica e exibe a janela de login carregando o aqruivo correspondente.
 */
public class App extends Application {
    /**
    * Método chamado automaticamente pelo JavaFX para iniciar a aplicação.
    *
    * @param stage O palco principal da aplicação
    * @throws IOException Se o FXML não puder ser carregado 
    */
    @Override
    public void start(Stage stage) throws IOException {
        // Carregando o FXML usando getClass().getResource
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 250);
        stage.setScene(scene);
        stage.setTitle("Login Bancário");
        stage.show();
    }

    /**
     * Método principal, inicia a aplicação
     * 
     * @param args Não usado
     */
    public static void main(String[] args) {
        launch(args);
    }
}