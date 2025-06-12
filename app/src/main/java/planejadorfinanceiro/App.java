package planejadorfinanceiro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
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

    public static void main(String[] args) {
        launch(args);
    }
}