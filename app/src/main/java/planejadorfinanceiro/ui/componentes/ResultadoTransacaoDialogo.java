package planejadorfinanceiro.ui.componentes;

import javafx.scene.control.ButtonBar;
import planejadorfinanceiro.model.Transacao;

/**
 * Representa o resultado de uma interação com um diálogo relacionado a uma {@link Transacao}.
 */
public class ResultadoTransacaoDialogo {
    /** Tipo do botão pressionado no diálogo */
    private ButtonBar.ButtonData buttonType;
    /** A transação associada */
    private Transacao transacao;

    /**
     * Construtor da classe.
     * 
     * @param transacao A transação associada ao diálogo
     * @param buttonType O tipo de botão pressionado
     */
    public ResultadoTransacaoDialogo(Transacao transacao, ButtonBar.ButtonData buttonType){
        this.transacao = transacao;
        this.buttonType = buttonType;
    }

    /**
     * @return O tipo do botão pressionado
     */
    public ButtonBar.ButtonData getButtonType() {
        return buttonType;
    }

    /**
     * @return A transação manipulada
     */
    public Transacao getTransacao() {
        return transacao;
    }
}
