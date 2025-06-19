package planejadorfinanceiro.ui.componentes;

import javafx.collections.FXCollections;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import planejadorfinanceiro.model.TipoTransacao;
import planejadorfinanceiro.model.Transacao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TabelaTransacao extends TableView<Transacao> {
    private final static int ALTURA_LINHA = 42;
    private final static int ALTURA_CABECALHO = 50;

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

        // Formata a data para dd/MM/yyyy
        colunaData.setCellFactory(coluna -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });

        // Formata o valor para verde se entrada, vermelho caso seja saída
        colunaValor.setCellFactory(coluna -> new TableCell<>(){
                @Override
                protected void updateItem(Double valor, boolean empty){
                    super.updateItem(valor, empty);
                    if (empty || valor == null){
                        setText(null);
                        setStyle("");
                    }
                    else {
                        Transacao transacaoFormatar = getTableView().getItems().get(getIndex());
                        setText(String.format("R$ %.2f", valor));
                        if (transacaoFormatar.getTipo() == TipoTransacao.ENTRADA) {
                            setStyle("-fx-text-fill: green;");
                        } else if (transacaoFormatar.getTipo() == TipoTransacao.SAIDA) {
                            setStyle("-fx-text-fill: red;");
                        } else {
                            setStyle("");
                        }
                    }
                }
        });

        // Adicionar as colunas à tabela
        getColumns().setAll(colunaNome, colunaValor, colunaTipo, colunaData);

        setFixedCellSize(ALTURA_LINHA);
        getStylesheets().add(getClass().getResource("/tabelaTransacaoStyle.css").toExternalForm());
    }

    public void mostrarTransacoes(List<Transacao> transacoes){
        setItems(FXCollections.observableList(transacoes));
        refresh();
        // Aplica o tamanho correto da tabela
        setMinHeight(transacoes.size() * ALTURA_LINHA + ALTURA_CABECALHO);
        setMaxHeight(getMinHeight());
    }
}
