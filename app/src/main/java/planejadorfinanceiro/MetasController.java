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

/**
 * Controlador da tela de gerenciamento de metas financeiras.
 * 
 * Permite ao usuário visualizar, criar, excluir e adicionar transações a metas.
 * As metas são exibidas em uma tabela, e é possível interagir com elas por meio
 * de cliques ou botões para atualizar valores ou excluir metas existentes.
 * 
 * As operações são realizadas com base no cliente logado, acessado via {@link GerenciadorFinanceiro}.
 * Cada meta pode ter transações associadas que atualizam seu valor atual.
 */
public class MetasController {

    @FXML private TableView<Meta> tabelaMetas;
    @FXML private TableColumn<Meta, String> colunaNome;
    @FXML private TableColumn<Meta, Double> colunaValorAlvo;
    @FXML private TableColumn<Meta, Double> colunaValorAtual;
    @FXML private TableColumn<Meta, Double> colunaProgresso;
    @FXML private TableColumn<Meta, String> colunaPrazo;
    @FXML private Button voltarButton;
    @FXML private Button novaMetaButton;
    @FXML private Label messageLabel;

    private GerenciadorFinanceiro gerenciador;
    private ObservableList<Meta> metas;

    /**
     * Inicializa os componentes da interface.
     * Configura as colunas da tabela de metas e define o evento de clique duplo
     * para adicionar transações a uma meta selecionada.
     */
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

    /**
     * Carrega as metas do cliente logado e exibe na tabela.
     */
    private void carregarMetas() {
        Cliente cliente = gerenciador.getClienteLogado();
        metas = FXCollections.observableArrayList(cliente.getMetas());
        tabelaMetas.setItems(metas);
    }

    /**
     * Ação executada ao clicar no botão "Voltar".
     * Retorna à tela de perfil do cliente.
     */
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

    /**
     * Ação executada ao clicar no botão "Nova Meta".
     * Abre um diálogo para o usuário cadastrar uma nova meta.
     */
    @FXML
    private void handleCriarMeta() {
        MetasDialogo dialogo = new MetasDialogo();
        Optional<Meta> resultado = dialogo.showAndWait();

        if (resultado.isPresent()) {
            Meta novaMeta = resultado.get();
            gerenciador.criarMeta(novaMeta.getNome(), novaMeta.getValorAlvo(), novaMeta.getPrazoFinal());
            metas.add(novaMeta);
            tabelaMetas.refresh();
        }
    }

    /**
     * Ação executada ao clicar no botão "Excluir Meta".
     * Remove a meta selecionada.
     */
    @FXML
    private void handleExcluirMeta() {
        Meta metaSelecionada = tabelaMetas.getSelectionModel().getSelectedItem();

        if (metaSelecionada == null) {
            mostrarAlerta("Nenhuma meta selecionada", "Por favor, selecione uma meta para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação de Exclusão");
        confirmacao.setHeaderText("Tem certeza que deseja excluir a meta \"" + metaSelecionada.getNome() + "\"?");
        confirmacao.setContentText("Esta ação não poderá ser desfeita.");

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            gerenciador.removerMeta(metaSelecionada);
            //carregarMetas();
            tabelaMetas.refresh();
            messageLabel.setText("Meta removida com sucesso.");
        }
    }

    /**
     * Abre o diálogo para registrar uma transação associada à meta informada.
     * Atualiza o valor atual da meta e registra a transação no sistema.
     * 
     * @param meta A meta à qual a transação será associada.
     */
    private void abrirDialogoTransacaoParaMeta(Meta meta) {
        MetasDialogo dialogo = new MetasDialogo();
        Optional<Transacao> resultado = dialogo.showTransacaoDialogo(meta.getNome());

        if (resultado.isPresent()) {
            Transacao transacao = resultado.get();

            if (transacao.getTipo() == TipoTransacao.ENTRADA) {
                meta.adicionarValorAtual(transacao.getValor());
                gerenciador.criarTransacaoEntrada(transacao.getNome(), transacao.getValor(), transacao.getData());
            } else {
                meta.removerValorAtual(transacao.getValor());
                gerenciador.criarTransacaoSaida(transacao.getNome(), transacao.getValor(), transacao.getData());
            }

            gerenciador.atualizarMeta(meta.getId(), meta.getNome(), meta.getValorAlvo(), transacao.getValor(), transacao.getData());
            tabelaMetas.refresh();
            messageLabel.setText("Transação registrada para a meta: " + meta.getNome());
        }
    }

    /**
     * Exibe um alerta com o título e mensagem fornecidos.
     * 
     * @param titulo   Título da janela de alerta.
     * @param mensagem Mensagem a ser exibida.
     */
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
