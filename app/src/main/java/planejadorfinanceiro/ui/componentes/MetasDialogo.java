package planejadorfinanceiro.ui.componentes;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import planejadorfinanceiro.model.Meta;

import java.time.LocalDate;

/** Caixa de diálogo personalizada para criar uma nova {@link Meta}. */
public class MetasDialogo extends Dialog<Meta> {

    private TextField campoNome;
    private TextField campoValorAlvo;
    private TextField campoValorAtual;
    private DatePicker campoPrazoFinal;
    private Label erroLabel;

    private final ButtonType salvarButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);

    public MetasDialogo() {
        campoNome = new TextField();
        campoValorAlvo = new TextField();
        campoValorAtual = new TextField();
        campoPrazoFinal = new DatePicker();

        erroLabel = new Label();
        erroLabel.setStyle("-fx-text-fill: red;");

        montarDialogo();

        setResultConverter(dialogButton -> {
            if (dialogButton == salvarButtonType) {
                return criarMetaDoDialogo();
            }
            return null;
        });
    }

    private void montarDialogo() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(campoNome, 1, 0);

        grid.add(new Label("Valor Alvo (R$):"), 0, 1);
        grid.add(campoValorAlvo, 1, 1);

        grid.add(new Label("Valor Atual (R$):"), 0, 2);
        grid.add(campoValorAtual, 1, 2);

        grid.add(new Label("Prazo Final:"), 0, 3);
        grid.add(campoPrazoFinal, 1, 3);

        grid.add(erroLabel, 0, 4, 2, 1);

        getDialogPane().setContent(grid);

        getDialogPane().getButtonTypes().addAll(salvarButtonType, ButtonType.CLOSE);

        Node salvarButton = getDialogPane().lookupButton(salvarButtonType);
        salvarButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
        getDialogPane().lookupButton(ButtonType.CLOSE).setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");

        salvarButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (criarMetaDoDialogo() == null) {
                event.consume();
            }
        });

        setTitle("Criar Nova Meta");
    }

    private Meta criarMetaDoDialogo() {
        try {
            String nome = campoNome.getText().trim();
            double valorAlvo = Double.parseDouble(campoValorAlvo.getText());
            double valorAtual = Double.parseDouble(campoValorAtual.getText());
            LocalDate prazo = campoPrazoFinal.getValue();

            if (nome.isEmpty()) {
                erroLabel.setText("O nome não pode estar vazio.");
                return null;
            }
            if (prazo == null) {
                erroLabel.setText("Escolha uma data válida.");
                return null;
            }

            return new Meta(nome, valorAlvo, valorAtual, prazo);
        } catch (NumberFormatException e) {
            erroLabel.setText("Digite valores numéricos válidos.");
        }
        return null;
    }
}
