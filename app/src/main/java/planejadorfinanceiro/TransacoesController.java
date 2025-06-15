package planejadorfinanceiro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import planejadorfinanceiro.model.Cliente;
import planejadorfinanceiro.ui.componentes.TabelaTransacao;

import java.io.IOException;

public class TransacoesController {
    private Cliente clienteLogado;
    @FXML
    private TabelaTransacao tabelaTransacao;
    @FXML
    public Button voltarButton;

    public void inicializarDados(Cliente cliente){
        clienteLogado = cliente;
        tabelaTransacao.mostrarTransacoes(clienteLogado.getTransacoes());
    }

    @FXML
    private void handleVoltar() {
        try {
            // Carrega a tela de perfil cliente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/perfil_cliente.fxml"));
            Parent root = loader.load();

            PerfilClienteController controller = loader.getController();
            controller.inicializarDados(clienteLogado);
            Scene scene = new Scene(root);

            // Obtém o palco (stage) atual e muda a cena
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Perfil do Cliente - " + clienteLogado.getNome());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
