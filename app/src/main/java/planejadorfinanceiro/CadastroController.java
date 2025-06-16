package planejadorfinanceiro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import planejadorfinanceiro.model.Cliente;
import planejadorfinanceiro.service.ClienteService;

import java.io.IOException;
import java.util.List;

public class CadastroController {
    @FXML
    private TextField nomeField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField senhaField;
    @FXML
    private PasswordField confirmarSenhaField;
    @FXML
    private Button cadastrarButton;
    @FXML
    private Button voltarButton;
    @FXML
    private Label mensagemLabel;

    @FXML
    private void handleCadastrar() {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        String senha = senhaField.getText();
        String confirmarSenha = confirmarSenhaField.getText();
        
        System.out.println("Tentando cadastrar usuário: " + nome + ", Email: " + email);

        // Validações básicas
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            mensagemLabel.setText("Todos os campos são obrigatórios!");
            mensagemLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            mensagemLabel.setText("As senhas não coincidem!");
            mensagemLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Para debug: mostrar clientes já cadastrados
        List<Cliente> clientesAtuais = ClienteService.carregarClientes();
        System.out.println("Clientes cadastrados antes: " + clientesAtuais.size());
        
        // Verifica se o email já está cadastrado
        if (ClienteService.clienteExistente(email)) {
            mensagemLabel.setText("Este email já está cadastrado!");
            mensagemLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Criar e salvar o novo cliente
        Cliente novoCliente = new Cliente(nome, email, senha);
        ClienteService.adicionarCliente(novoCliente);
        
        // Verificar se o cliente foi realmente adicionado
        List<Cliente> clientesDepois = ClienteService.carregarClientes();
        System.out.println("Clientes cadastrados depois: " + clientesDepois.size());

        // Exibir mensagem de sucesso
        mensagemLabel.setStyle("-fx-text-fill: green;");
        mensagemLabel.setText("Cadastro realizado com sucesso!");

        // Limpar campos
        nomeField.clear();
        emailField.clear();
        senhaField.clear();
        confirmarSenhaField.clear();
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
            mensagemLabel.setText("Erro ao retornar para a tela de login");
            mensagemLabel.setStyle("-fx-text-fill: red;");
        }
    }
} 