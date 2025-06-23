package planejadorfinanceiro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import planejadorfinanceiro.model.Meta;
import planejadorfinanceiro.model.GerenciadorFinanceiro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Controlador da página de visualização das metas financeiras do cliente. */
public class MetasController {

    @FXML private TableView<Meta> tabelaMetas;
    @FXML private TableColumn<Meta, String> colunaNome;
    @FXML private TableColumn<Meta, Double> colunaValorAlvo;
    @FXML private TableColumn<Meta, Double> colunaValorAtual;
    @FXML private TableColumn<Meta, Double> colunaProgresso;
    @FXML private TableColumn<Meta, String> colunaPrazo;
    @FXML
    private Button voltarButton;
    @FXML
    private Button novaMetaButton;

    //private Cliente cliente;
    private GerenciadorFinanceiro gerenciador;

    @FXML
    private void initialize() {
        gerenciador = GerenciadorFinanceiro.getInstancia();
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaValorAlvo.setCellValueFactory(new PropertyValueFactory<>("valorAlvo"));
        colunaValorAtual.setCellValueFactory(new PropertyValueFactory<>("valorAtual"));

        // Usa lambda para exibir progresso diretamente (sem usar Property)
        colunaProgresso.setCellValueFactory(cellData -> {
            double progresso = cellData.getValue().calcularProgresso();
            return new javafx.beans.property.SimpleDoubleProperty(progresso).asObject();
        });

        // Formata a data como string
        colunaPrazo.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getPrazoFinal().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            )
        );

                // Carrega e exibe as metas do cliente logado
        List<Meta> metas = gerenciador.getClienteLogado().getMetas();
        ObservableList<Meta> observableMetas = FXCollections.observableArrayList(metas);
        tabelaMetas.setItems(observableMetas);
    }

    @FXML
    private void handleVoltar() {
        try {
            // Carrega a tela de perfil cliente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/perfil_cliente.fxml"));
            Parent root = loader.load();

            PerfilClienteController controller = loader.getController();
            controller.inicializarDados(gerenciador.getClienteLogado());
            Scene scene = new Scene(root);

            // Obtém o palco (stage) atual e muda a cena
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Perfil do Cliente - " + gerenciador.getClienteLogado().getNome());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCriarMeta() {
    }
}

