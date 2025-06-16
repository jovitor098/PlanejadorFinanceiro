package planejadorfinanceiro;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import planejadorfinanceiro.model.Cliente;
import planejadorfinanceiro.model.TipoTransacao;
import planejadorfinanceiro.model.Transacao;
import planejadorfinanceiro.ui.componentes.TabelaTransacao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TransacoesController {
    @FXML
    private CheckBox entradaCheckBox;
    @FXML
    private CheckBox saidaCheckBox;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField valorMinField;
    @FXML
    private TextField valorMaxField;
    @FXML
    private DatePicker dataInicioPicker;
    @FXML
    private DatePicker dataFimPicker;
    @FXML
    private Cliente clienteLogado;
    @FXML
    private TabelaTransacao tabelaTransacao;
    @FXML
    public Button voltarButton;

    public void inicializarDados(Cliente cliente) {
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

    @FXML
    private void handleFiltrar() {
        List<Transacao> listaFiltrada = clienteLogado.getTransacoes().stream().filter(transacao -> {
            // Verifica nome
            String nomeFiltro = nomeTextField.getText();
            if (nomeFiltro != null && !transacao.getNome().toLowerCase().contains(nomeFiltro.toLowerCase())) {
                return false;
            }

            // Verifica valor
            double valor = transacao.getValor();
            if (!valorMinField.getText().isEmpty() && valor < Double.parseDouble(valorMinField.getText())) {
                return false;
            }
            if (!valorMaxField.getText().isEmpty() && valor > Double.parseDouble(valorMaxField.getText()))
                return false;

            // Verifica tipo
            if (transacao.getTipo() == TipoTransacao.ENTRADA && !entradaCheckBox.isSelected()) {
                return false;
            }
            if (transacao.getTipo() == TipoTransacao.SAIDA && !saidaCheckBox.isSelected()) {
                return false;
            }

            // Verifica data
            LocalDate data = transacao.getData();
            if (dataInicioPicker.getValue() != null && data.isBefore(dataInicioPicker.getValue())) {
                return false;
            }
            if (dataFimPicker.getValue() != null && data.isAfter(dataFimPicker.getValue())) {
                return false;
            }
            return true;
        }).toList();

        tabelaTransacao.mostrarTransacoes(listaFiltrada);
    }
}
