package planejadorfinanceiro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import planejadorfinanceiro.model.Cliente;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class PerfilClienteController {
    @FXML
    private Label nomeLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label saldoLabel;
    @FXML
    private Button voltarButton;
    
    // Cliente atual que está logado
    private Cliente clienteLogado;
    
    // Método para inicializar dados do cliente na tela
    public void inicializarDados(Cliente cliente) {
        this.clienteLogado = cliente;
        
        // Atualiza as labels com os dados do cliente
        nomeLabel.setText(cliente.getNome());
        emailLabel.setText(cliente.getEmail());
        
        // Formata o saldo como valor monetário
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        saldoLabel.setText(formatoMoeda.format(cliente.getSaldo()));
    }
    
    @FXML
    private void handleVoltar() {
        try {
            // Carrega a tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            // Obtém o palco (stage) atual e muda a cena
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login Bancário");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 