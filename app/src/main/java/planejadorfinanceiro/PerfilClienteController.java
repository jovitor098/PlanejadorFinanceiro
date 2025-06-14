package planejadorfinanceiro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import planejadorfinanceiro.model.Cliente;
import planejadorfinanceiro.ui.componentes.GraficoSaldo;

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
    @FXML
    private GraficoSaldo graficoSaldo;
    @FXML
    private ComboBox<Integer> boxSelecionarAno;

    // Cliente atual que está logado
    private Cliente clienteLogado;
    
    // Método para inicializar dados do cliente na tela
    public void inicializarDados(Cliente cliente) {
        this.clienteLogado = cliente;
        graficoSaldo.setCliente(cliente);
        
        // Atualiza as labels com os dados do cliente
        nomeLabel.setText(cliente.getNome());
        emailLabel.setText(cliente.getEmail());
        
        // Formata o saldo como valor monetário
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        saldoLabel.setText(formatoMoeda.format(cliente.getSaldo()));

        // Coloca as todos os anos que o cliente fez transação como opção para selecionar
        boxSelecionarAno.getItems().setAll(clienteLogado.getAnosTransacoes());
        Integer ultimoAno = boxSelecionarAno.getItems().getLast();
        // Seleciona o último ano
        if (ultimoAno != null){
            boxSelecionarAno.setValue(ultimoAno);
            graficoSaldo.atualizarAnoGrafico(2025);
        }
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

    @FXML
    private void handleSelecionarAno(){
        // Atualiza o gráfico para o ano selecionado
        int anoSelecionado = boxSelecionarAno.getValue();
        graficoSaldo.atualizarAnoGrafico(anoSelecionado);
    }
} 