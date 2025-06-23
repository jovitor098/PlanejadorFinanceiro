package planejadorfinanceiro.model;

/**
 * Interface que define o contrato para mecanismos de notificação.
 */
public interface Notificavel {

    /**
     * Envia uma mensagem de notificação
     * 
     * @param mensagem Texto da nostificação a ser enviada
     */
    public void notificar(String mensagem);
}
