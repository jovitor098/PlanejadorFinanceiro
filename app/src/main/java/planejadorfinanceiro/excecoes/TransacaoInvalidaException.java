package planejadorfinanceiro.excecoes;

public class TransacaoInvalidaException extends RuntimeException{
    public TransacaoInvalidaException(String mensagem){
        super(mensagem);
    }
}
