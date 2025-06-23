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
import planejadorfinanceiro.model.Transacao;
import planejadorfinanceiro.ui.componentes.GraficoSaldo;
import planejadorfinanceiro.ui.componentes.TabelaTransacao;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/** Controlador da tela perfil do cliente/tela principal.
 * Exibe as informações do cadastro, saldo atual, gráfico de evolução do saldo por ano,
 * uma prévia das últimas trasações realizadas, um botão para a pagina de transações,
 * -- um botão para a página de metas.
 */
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
    @FXML
    private TabelaTransacao tabelaTransacao;
    @FXML
    private Button todasTransacaoesButton;
    @FXML
    private Button verMetasButton;

    // Cliente atual que está logado
    private Cliente clienteLogado;
    
    /**
     * Método para inicializar dados do cliente na tela.
     * 
     * @param cliente O cliente autenticado cujas informações serão exibidas
     */
    public void inicializarDados(Cliente cliente) {
        this.clienteLogado = cliente;
        graficoSaldo.setCliente(cliente);
        
        // Atualiza as labels com os dados do cliente
        nomeLabel.setText(cliente.getNome());
        emailLabel.setText(cliente.getEmail());
        
        // Formata o saldo como valor monetário
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        saldoLabel.setText(formatoMoeda.format(cliente.getSaldo()));
        // Configura a cor da label saldo
        if (cliente.getSaldo() >= 0) {
            saldoLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 13px;"); // verde
        } else {
            saldoLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 13px;"); // vermelho
        }

        // Coloca as todos os anos que o cliente fez transação como opção para selecionar
        boxSelecionarAno.getItems().setAll(clienteLogado.obterAnosTransacoes());

        if (boxSelecionarAno.getItems().size() > 0){
            Integer ultimoAno = boxSelecionarAno.getItems().getLast();
            // Seleciona o último ano
            if (ultimoAno != null){
                boxSelecionarAno.setValue(ultimoAno);
                graficoSaldo.atualizarAnoGrafico(2025);
        }
        }
       

        List<Transacao> transacoesCliente = cliente.getTransacoes();
        // Mostra na tabela as ultimas 5 transacoes
        List<Transacao> ultimasTransacoes = transacoesCliente
                .stream()
                .sorted((t1, t2) -> t2.getData().compareTo(t1.getData()))
                .limit(5)
                .toList();
        tabelaTransacao.mostrarTransacoes(ultimasTransacoes);
    }
    
    /**
     * Ação excecutada ao clicar em 'voltar', retorna à tela de login.
     */
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

    /**
     * Ação excecutada ao clicar em 'Ver todas as transações', leva à tela com o 
     * histórico completo de transações do cliente.
     */
    @FXML
    private void handleTodasTransacoes(){
        try {
            // Carrega a tela de transacoes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transacoes.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Obtém o palco (stage) atual e muda a cena
            Stage stage = (Stage) todasTransacaoesButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Transações");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /** 
     * Ação excecutada ao clicar no seletor, atualiza o gráfico com os dados do ano selecionado.
     */
    @FXML
    private void handleVerMetas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/metas.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) verMetasButton.getScene().getWindow();
            stage.setTitle("Metas do Cliente");
            stage.setScene(new Scene(root));
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