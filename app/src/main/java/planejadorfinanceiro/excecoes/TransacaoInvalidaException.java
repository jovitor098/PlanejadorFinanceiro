package planejadorfinanceiro.excecoes;

/** Exceção lançada quando uma transação financeira é considerada inválida. */
public class TransacaoInvalidaException extends RuntimeException{
    /**
     * Cria uma exceção com a mensagem especificada.
     * 
     * @param mensagem Mensagem descritiva do motivo da exceção
     */
    public TransacaoInvalidaException(String mensagem){
        super(mensagem);
    }
}
