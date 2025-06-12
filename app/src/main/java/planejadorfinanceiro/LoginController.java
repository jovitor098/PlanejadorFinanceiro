package planejadorfinanceiro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import planejadorfinanceiro.model.Cliente;
import planejadorfinanceiro.service.ClienteService;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink cadastroLink;
    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin() {
        String email = usernameField.getText().trim();
        String senha = passwordField.getText();
        
        System.out.println("Tentando login com email: " + email);
        
        if (email.isEmpty() || senha.isEmpty()) {
            messageLabel.setText("Email e senha são obrigatórios");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        
        // Para debug: mostrar clientes cadastrados
        List<Cliente> clientes = ClienteService.carregarClientes();
        System.out.println("Total de clientes cadastrados: " + clientes.size());
        for (Cliente c : clientes) {
            System.out.println("Cliente disponível: " + c.getNome() + ", Email: " + c.getEmail());
        }
        
        // Verificar se existe um cliente com o email e senha fornecidos
        Cliente cliente = ClienteService.buscarCliente(email, senha);
        
        // Se encontrou um cliente com as credenciais informadas
        if (cliente != null) {
            System.out.println("Login bem-sucedido para: " + cliente.getNome());
            messageLabel.setText("Login realizado com sucesso!");
            messageLabel.setStyle("-fx-text-fill: green;");
            abrirTelaPerfil(cliente);
        } else {
            // Manter compatibilidade com o login hardcoded existente
            if (email.equals("admin") && senha.equals("admin")) {
                System.out.println("Login de administrador");
                // Criar um cliente para o admin
                Cliente adminCliente = new Cliente("Administrador", "admin", "admin");
                adminCliente.setSaldo(1000.0); // Saldo inicial para demonstração
                
                messageLabel.setText("Login de administrador realizado com sucesso!");
                messageLabel.setStyle("-fx-text-fill: green;");
                
                abrirTelaPerfil(adminCliente);
            } else {
                System.out.println("Falha no login: credenciais inválidas");
                messageLabel.setText("Email ou senha incorretos");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }
    
    private void abrirTelaPerfil(Cliente cliente) {
        try {
            // Carrega a tela de perfil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/perfil_cliente.fxml"));
            Parent root = loader.load();
            
            // Obtém o controller e passa os dados do cliente
            PerfilClienteController controller = loader.getController();
            controller.inicializarDados(cliente);
            
            // Configura a nova cena
            Scene scene = new Scene(root);
            
            // Obtém o palco (stage) atual e muda a cena
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Perfil do Cliente - " + cliente.getNome());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erro ao abrir a tela de perfil");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
    
    @FXML
    private void handleCadastro() {
        try {
            // Carrega a tela de cadastro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cadastro.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            // Obtém o palco (stage) atual e muda a cena
            Stage stage = (Stage) cadastroLink.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Cadastro de Usuário");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erro ao abrir a tela de cadastro");
        }
    }
} 