package planejadorfinanceiro.ui.componentes;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import planejadorfinanceiro.model.TipoTransacao;
import planejadorfinanceiro.model.Transacao;

import java.time.LocalDate;
import java.util.List;

public class TabelaTransacao extends TableView<Transacao> {
    private final static int ALTURA_LINHA = 25;
    private final static int ALTURA_CABECALHO = 28;

    public TabelaTransacao() {
        super();
        // Configurar as colunas
        TableColumn<Transacao, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Transacao, Double> colunaValor = new TableColumn<>("Valor");
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        TableColumn<Transacao, TipoTransacao> colunaTipo = new TableColumn<>("Tipo");
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Transacao, LocalDate> colunaData = new TableColumn<>("Data");
        colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));

        // Adicionar as colunas à tabela
        getColumns().setAll(colunaNome, colunaValor, colunaTipo, colunaData);
        setFixedCellSize(ALTURA_LINHA);
    }

    public void mostrarTransacoes(List<Transacao> transacoes){
        setItems(FXCollections.observableList(transacoes));
        // Aplica o tamanho correto da tabela
        setMinHeight(transacoes.size() * getFixedCellSize() + ALTURA_CABECALHO);
        setMaxHeight(getMinHeight());
    }
}
