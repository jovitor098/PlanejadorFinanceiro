package planejadorfinanceiro;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import planejadorfinanceiro.model.Cliente;
import planejadorfinanceiro.model.GerenciadorFinanceiro;
import planejadorfinanceiro.model.Meta;
import planejadorfinanceiro.model.TipoTransacao;
import planejadorfinanceiro.model.Transacao;
import planejadorfinanceiro.ui.componentes.MetasDialogo;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MetasController {

    @FXML
    private TableView<Meta> tabelaMetas;
    @FXML
    private TableColumn<Meta, String> colunaNome;
    @FXML
    private TableColumn<Meta, Double> colunaValorAlvo;
    @FXML
    private TableColumn<Meta, Double> colunaValorAtual;
    @FXML
    private TableColumn<Meta, Double> colunaProgresso;
    @FXML
    private TableColumn<Meta, String> colunaPrazo;
    @FXML
    private Button voltarButton;
    @FXML
    private Button novaMetaButton;
    @FXML
    private Label messageLabel;

    private GerenciadorFinanceiro gerenciador;
    private ObservableList<Meta> metas;

    @FXML
    private void initialize() {
        gerenciador = GerenciadorFinanceiro.getInstancia();

        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaValorAlvo.setCellValueFactory(new PropertyValueFactory<>("valorAlvo"));
        colunaValorAtual.setCellValueFactory(new PropertyValueFactory<>("valorAtual"));
        colunaProgresso.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().calcularProgresso()).asObject()
        );
        colunaPrazo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getPrazoFinal().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                )
        );

        carregarMetas();

        // Evento de clique duplo na tabela, usado para atualizar o valor de uma meta
        tabelaMetas.setRowFactory(tv -> {
            TableRow<Meta> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Meta metaSelecionada = row.getItem();
                    abrirDialogoTransacaoParaMeta(metaSelecionada);
                }
            });
            return row;
        });
    }

    private void carregarMetas() {
        Cliente cliente = gerenciador.getClienteLogado();
        metas = FXCollections.observableArrayList(cliente.getMetas());
        tabelaMetas.setItems(metas);
    }

    @FXML
    private void handleVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/perfil_cliente.fxml"));
            Parent root = loader.load();

            PerfilClienteController controller = loader.getController();
            controller.inicializarDados(gerenciador.getClienteLogado());
            Scene scene = new Scene(root);

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
        MetasDialogo dialogo = new MetasDialogo();
        Optional<Meta> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            Meta novaMeta = resultado.get();
            gerenciador.criarMeta(novaMeta.getNome(), novaMeta.getValorAlvo(), novaMeta.getPrazoFinal());
            carregarMetas();
            //gerenciador.getClienteLogado().adicionarMeta(novaMeta);
            //Cliente cliente = gerenciador.getClienteLogado();
            //metas.add(novaMeta);
            //tabelaMetas.refresh();
        }
    }

    private void abrirDialogoTransacaoParaMeta(Meta meta) {
        MetasDialogo dialogo = new MetasDialogo();
        Optional<Transacao> resultado = dialogo.showTransacaoDialogo(meta.getNome());

        if (resultado.isPresent()) {
            Transacao transacao = resultado.get();

            if (transacao.getTipo() == TipoTransacao.ENTRADA) {
                meta.adicionarValorAtual(transacao.getValor());
            } else {
                meta.removerValorAtual(transacao.getValor());
            }

            gerenciador.atualizarMeta(meta.getId(), meta.getNome(), meta.getValorAlvo(), transacao.getValor(), transacao.getData());
            tabelaMetas.refresh();
            messageLabel.setText("Transação registrada para a meta: " + meta.getNome());
        }
    }
}


