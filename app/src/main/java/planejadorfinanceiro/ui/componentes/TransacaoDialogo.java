package planejadorfinanceiro.ui.componentes;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import planejadorfinanceiro.model.TipoTransacao;
import planejadorfinanceiro.model.Transacao;
import planejadorfinanceiro.model.TransacaoFactory;

public class TransacaoDialogo extends Dialog<ResultadoTransacaoDialogo> {
    private TextField campoNome;
    private TextField campoValor;
    private DatePicker campoData;
    private ComboBox<TipoTransacao> campoTipo;

    private ButtonType salvarButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
    private ButtonType excluirButtonType = new ButtonType("Excluir", ButtonBar.ButtonData.LEFT);

    public TransacaoDialogo() {
        campoNome = new TextField();
        campoValor = new TextField();
        campoData = new DatePicker();
        campoTipo = new ComboBox<>();
        campoTipo.getItems().addAll(TipoTransacao.values());

        salvarButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        excluirButtonType = new ButtonType("Excluir", ButtonBar.ButtonData.LEFT);

        // Retorna o resultado de acordo com o botão clicado
        setResultConverter(dialogButton -> {
            if (dialogButton == salvarButtonType){
                return new ResultadoTransacaoDialogo(criarTransacaoDoDialogo(), dialogButton.getButtonData());
            }
            if (dialogButton == excluirButtonType){
                return new ResultadoTransacaoDialogo(null, dialogButton.getButtonData());
            }
            return null;
        });
    }

    public void montarDialogo() {
        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(campoNome, 1, 0);

        grid.add(new Label("Valor:"), 0, 1);
        grid.add(campoValor, 1, 1);

        grid.add(new Label("Data:"), 0, 2);
        grid.add(campoData, 1, 2);

        grid.add(new Label("Tipo:"), 0, 3);
        grid.add(campoTipo, 1, 3);

        getDialogPane().setContent(grid);

        // Botões
        getDialogPane().getButtonTypes().addAll(salvarButtonType,ButtonType.CLOSE);

        setTitle("Criar transação");
    }

    public void montarDialogo(Transacao transacaoEditar) {
        montarDialogo();
        getDialogPane().getButtonTypes().add(excluirButtonType);

        // Inicializa campos
        campoNome.setText(transacaoEditar.getNome());
        campoValor.setText(String.valueOf(transacaoEditar.getValor()));
        campoData.setValue(transacaoEditar.getData());
        campoTipo.setValue(transacaoEditar.getTipo());

        setTitle("Editar transação");
    }

    private Transacao criarTransacaoDoDialogo() {
        return TransacaoFactory.criarTransacao(
                campoNome.getText().trim(),
                Double.parseDouble(campoValor.getText()),
                campoTipo.getValue(),
                campoData.getValue());
    }
}
