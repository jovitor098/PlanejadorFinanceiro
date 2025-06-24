package planejadorfinanceiro.ui.componentes;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import planejadorfinanceiro.model.Meta;
import planejadorfinanceiro.model.TipoTransacao;
import planejadorfinanceiro.model.Transacao;

import java.time.LocalDate;
import java.util.Optional;

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

        getDialogPane().setPrefWidth(500);
        getDialogPane().setPrefHeight(400);

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

    public Optional<Transacao> showTransacaoDialogo(String nomeMeta) {
        Dialog<Transacao> dialog = new Dialog<>();
        dialog.setTitle("Atualizar saldo da Meta: " + nomeMeta);

        TextField campoNome = new TextField();
        campoNome.setPromptText("Descrição da transação");

        TextField campoValor = new TextField();
        campoValor.setPromptText("Valor (R$)");

        ComboBox<TipoTransacao> campoTipo = new ComboBox<>();
        campoTipo.getItems().setAll(TipoTransacao.values());
        campoTipo.setValue(TipoTransacao.ENTRADA);

        DatePicker campoData = new DatePicker(LocalDate.now());

        Label erroLabel = new Label();
        erroLabel.setStyle("-fx-text-fill: red;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(campoNome, 1, 0);

        grid.add(new Label("Valor (R$):"), 0, 1);
        grid.add(campoValor, 1, 1);

        grid.add(new Label("Tipo:"), 0, 2);
        grid.add(campoTipo, 1, 2);

        grid.add(new Label("Data:"), 0, 3);
        grid.add(campoData, 1, 3);

        grid.add(erroLabel, 0, 4, 2, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().setPrefWidth(500);
        dialog.getDialogPane().setPrefHeight(400);

        ButtonType salvarBtn = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(salvarBtn, ButtonType.CANCEL);

        Node botaoSalvar = dialog.getDialogPane().lookupButton(salvarBtn);
        botaoSalvar.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                String nome = campoNome.getText().trim();
                double valor = Double.parseDouble(campoValor.getText());
                TipoTransacao tipo = campoTipo.getValue();
                LocalDate data = campoData.getValue();

                if (nome.isEmpty()) {
                    erroLabel.setText("Nome da transação não pode ser vazio.");
                    event.consume();
                } else if (data == null) {
                    erroLabel.setText("Selecione uma data.");
                    event.consume();
                } else if (valor <= 0) {
                    erroLabel.setText("Valor deve ser positivo.");
                    event.consume();
                }
            } catch (NumberFormatException e) {
                erroLabel.setText("Digite um valor numérico válido.");
                event.consume();
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == salvarBtn) {
                try {
                    String nome = campoNome.getText().trim();
                    double valor = Double.parseDouble(campoValor.getText());
                    TipoTransacao tipo = campoTipo.getValue();
                    LocalDate data = campoData.getValue();
                    return new Transacao(valor, nome, tipo, data);
                } catch (Exception ignored) {}
            }
            return null;
        });

        return dialog.showAndWait();
    }
}

