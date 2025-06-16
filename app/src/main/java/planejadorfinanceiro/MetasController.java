package planejadorfinanceiro;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import planejadorfinanceiro.model.Meta;
import planejadorfinanceiro.model.Cliente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.format.DateTimeFormatter;

public class MetasController {

    @FXML private TableView<Meta> tabelaMetas;
    @FXML private TableColumn<Meta, String> colunaNome;
    @FXML private TableColumn<Meta, Double> colunaValorAlvo;
    @FXML private TableColumn<Meta, Double> colunaValorAtual;
    @FXML private TableColumn<Meta, Double> colunaProgresso;
    @FXML private TableColumn<Meta, String> colunaPrazo;

    private Cliente cliente;

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        carregarMetas();
    }

    /*@FXML
    private void initialize() {
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaValorAlvo.setCellValueFactory(new PropertyValueFactory<>("valorAlvo"));
        colunaValorAtual.setCellValueFactory(new PropertyValueFactory<>("valorAtual"));
        colunaProgresso.setCellValueFactory(cellData ->
            javafx.beans.property.SimpleDoubleProperty
                .doubleProperty(cellData.getValue().calcularProgressoProperty()).asObject()
        );
        colunaPrazo.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getPrazoFinal().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            )
        );
    }*/

    @FXML
    private void initialize() {
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
    }


    private void carregarMetas() {
        ObservableList<Meta> metas = FXCollections.observableArrayList(cliente.getMetas());
        tabelaMetas.setItems(metas);
    }
}

