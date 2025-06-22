package planejadorfinanceiro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import planejadorfinanceiro.model.GerenciadorFinanceiro;
import planejadorfinanceiro.model.TipoTransacao;
import planejadorfinanceiro.model.Transacao;
import planejadorfinanceiro.ui.componentes.ResultadoTransacaoDialogo;
import planejadorfinanceiro.ui.componentes.TabelaTransacao;
import planejadorfinanceiro.ui.componentes.TransacaoDialogo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    private TabelaTransacao tabelaTransacao;
    @FXML
    public Button voltarButton;

    private GerenciadorFinanceiro gerenciador;

    public void initialize() {
        gerenciador = GerenciadorFinanceiro.getInstancia();
        tabelaTransacao.setRowFactory(tv -> {
            TableRow<Transacao> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Transacao transacaoSelecionada = row.getItem();
                    abrirInformacoesTransacao(transacaoSelecionada);
                }
            });
            return row;
        });
        tabelaTransacao.mostrarTransacoes(gerenciador.getClienteLogado().getTransacoes());
    }

    private void abrirInformacoesTransacao(Transacao transacao){
        TransacaoDialogo dialogo = new TransacaoDialogo();
        dialogo.montarDialogo(transacao);
        Optional<ResultadoTransacaoDialogo> resultado = dialogo.showAndWait();

        if (resultado.isPresent()){
            // Se o botão foi o de salvar, então atualiza a transacao
            if (resultado.get().getButtonType() == ButtonBar.ButtonData.OK_DONE){
                Transacao transacaoAtualizada = resultado.get().getTransacao();
                gerenciador.atualizarTransacao(transacao.getId(),
                        transacaoAtualizada.getNome(),
                        transacaoAtualizada.getValor(),
                        transacaoAtualizada.getTipo(),
                        transacaoAtualizada.getData());
            }
            // Se foi o de excluir, remove a transacao
            else if (resultado.get().getButtonType() == ButtonBar.ButtonData.LEFT){
                gerenciador.removerTransacao(transacao);
            }

            // Atualiza a tabela
            tabelaTransacao.mostrarTransacoes(gerenciador.getClienteLogado().getTransacoes());
        }
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
    private void handleAdicionarTransacao(){
        TransacaoDialogo dialogo = new TransacaoDialogo();
        dialogo.montarDialogo();
        Optional<ResultadoTransacaoDialogo> resultado = dialogo.showAndWait();

        if (resultado.isPresent()){
            // Se o cliente clicou para salvar
            if (resultado.get().getButtonType() == ButtonBar.ButtonData.OK_DONE){
                Transacao novaTransacao = resultado.get().getTransacao();
                if (novaTransacao.getTipo() == TipoTransacao.ENTRADA){
                    gerenciador.criarTransacaoEntrada(novaTransacao.getNome(), novaTransacao.getValor(), novaTransacao.getData());
                }
                else {
                    gerenciador.criarTransacaoSaida(novaTransacao.getNome(), novaTransacao.getValor(), novaTransacao.getData());
                }
                // Atualiza a tabela
                tabelaTransacao.mostrarTransacoes(gerenciador.getClienteLogado().getTransacoes());
            }
        }
    }

    @FXML
    private void handleFiltrar() {
        List<Transacao> listaFiltrada = gerenciador.getClienteLogado().getTransacoes().stream().filter(transacao -> {
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
