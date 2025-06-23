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

/** Controlador da página de visualização das metas financeiras do cliente. */
public class MetasController {

    @FXML private TableView<Meta> tabelaMetas;
    @FXML private TableColumn<Meta, String> colunaNome;
    @FXML private TableColumn<Meta, Double> colunaValorAlvo;
    @FXML private TableColumn<Meta, Double> colunaValorAtual;
    @FXML private TableColumn<Meta, Double> colunaProgresso;
    @FXML private TableColumn<Meta, String> colunaPrazo;

    private Cliente cliente;

    /**
     * Define o cliente atual e carrega suas metas à tabela.
     * 
     * @param cliente O cliente que terá suas metas exibidas
     */
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
    
    /**
     * Inicializa a tabela de metas com os dados das colunas.
     * Configura a exibição dos dados.
     */
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

    /** Carrega as metas do cliente e insere na tabela da interface. */
    private void carregarMetas() {
        ObservableList<Meta> metas = FXCollections.observableArrayList(cliente.getMetas());
        tabelaMetas.setItems(metas);
    }
}

