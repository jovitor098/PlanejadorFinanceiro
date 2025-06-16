package planejadorfinanceiro.ui.componentes;

import javafx.scene.control.ButtonBar;
import planejadorfinanceiro.model.Transacao;

public class ResultadoTransacaoDialogo {
    private ButtonBar.ButtonData buttonType;
    private Transacao transacao;

    public ResultadoTransacaoDialogo(Transacao transacao, ButtonBar.ButtonData buttonType){
        this.transacao = transacao;
        this.buttonType = buttonType;
    }

    public ButtonBar.ButtonData getButtonType() {
        return buttonType;
    }

    public Transacao getTransacao() {
        return transacao;
    }
}
