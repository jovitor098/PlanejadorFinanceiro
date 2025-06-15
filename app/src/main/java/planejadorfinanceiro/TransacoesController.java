package planejadorfinanceiro;

import javafx.fxml.FXML;
import planejadorfinanceiro.model.Transacao;
import planejadorfinanceiro.ui.componentes.TabelaTransacao;

import java.util.List;

public class TransacoesController {
    private List<Transacao> transacoesCliente;
    @FXML
    private TabelaTransacao tabelaTransacao;

    public void inicializarDados(List<Transacao> transacoes){
        transacoesCliente = transacoes;
        tabelaTransacao.mostrarTransacoes(transacoesCliente);
    }
}
