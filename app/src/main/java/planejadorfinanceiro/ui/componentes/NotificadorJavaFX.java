package planejadorfinanceiro.ui.componentes;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import planejadorfinanceiro.model.Notificavel;

public class NotificadorJavaFX implements Notificavel {

    @Override
    public void notificar(String mensagem) {
        // Criar um Stage para a notificação
        Stage notificacaoStage = new Stage();
        notificacaoStage.initStyle(StageStyle.TRANSPARENT);

        // Criar o conteúdo da notificação
        Label label = new Label(mensagem);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        // Criar botão de fechar
        Button fecharBotao = new Button("Fechar");
        fecharBotao.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-background-radius: 3;");
        fecharBotao.setOnAction(e -> notificacaoStage.close());

        // Layout para alinhar o botão à direita
        HBox botaoBox = new HBox(fecharBotao);
        botaoBox.setStyle("-fx-alignment: center-right;");

        VBox notificacaoBox = new VBox(10, label, botaoBox);
        notificacaoBox.setStyle("-fx-background-color: #333333; -fx-padding: 10; -fx-background-radius: 5;");
        notificacaoBox.setPadding(new Insets(10));

        Scene scene = new Scene(notificacaoBox);
        scene.setFill(null);
        notificacaoStage.setScene(scene);

        // Ajustar o tamanho da notificação
        notificacaoStage.sizeToScene();

        // Posicionar no canto inferior direito da tela
        Rectangle2D tela = Screen.getPrimary().getVisualBounds();
        notificacaoStage.setX(tela.getMaxX() - notificacaoBox.getWidth() - 10);
        notificacaoStage.setY(tela.getMaxY() - notificacaoBox.getHeight() - 100);

        // Exibir a notificação
        notificacaoStage.show();
    }
}
