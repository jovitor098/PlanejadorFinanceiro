package planejadorfinanceiro.model;

import planejadorfinanceiro.excecoes.TransacaoInvalidaException;

import java.time.LocalDate;

/**
 * Fábrica responsável pela criação de objetos {@link Transacao} com validação dos dados de entrada. 
 */
public class TransacaoFactory {

    /**
     * Cria uma transação do tipo ENTRADA.
     * 
     * @param nome Nome da transação
     * @param valor Valor da transação(positivo)
     * @param data Data da transação(não futura)
     * @return Uma nova instância de {@link Transacao}
     * @throws TransacaoInvalidaException Se os dados forem inválidos
     */
    public static Transacao criarEntrada(String nome, double valor, LocalDate data){
        validarTransacao(nome, valor, TipoTransacao.ENTRADA, data);
        return new Transacao(valor, nome, TipoTransacao.ENTRADA, data);
    }

    /**
     * Cria uma transação do tipo SAIDA.
     * 
     * @param nome Nome da transação
     * @param valor Valor da transação(positivo)
     * @param data Data da transação(não futura)
     * @return Uma nova instância de {@link Transacao}
     * @throws TransacaoInvalidaException Se os dados forem inválidos
     */
    public static Transacao criarSaida(String nome, double valor, LocalDate data){
        validarTransacao(nome, valor, TipoTransacao.SAIDA, data);
        return new Transacao(valor, nome, TipoTransacao.SAIDA, data);
    }
    /**
     * Cria uma transação genérica com tipo a ser específicado.
     * 
     * @param nome Nome da transação
     * @param valor Valor da transação(positivo)
     * @param tipoTransacao Tipo da transacao(entrada/saida)
     * @param data Data da transação(não futura)
     * @return Uma nova instância de {@link Transacao}
     * @throws TransacaoInvalidaException Se os dados forem inválidos
     */
    public static Transacao criarTransacao(String nome, double valor, TipoTransacao tipoTransacao, LocalDate data){
        validarTransacao(nome, valor, tipoTransacao, data);
        return new Transacao(valor, nome, tipoTransacao, data);
    }

     /**
      * Valida os campos da transação e lança as exceções adequadas caso seja necessário.
      * 
      * @param nome Nome da transação(não vazio)
      * @param valor Valor da transação(não negativo)
      * @param tipoTransacao Tipo da transação(não nulo)
      * @param data Data da transaçção(não nula nem futura)
      */
    private static void validarTransacao(String nome, double valor, TipoTransacao tipoTransacao, LocalDate data){
        if (nome == null || nome.isEmpty()){
            throw new TransacaoInvalidaException("Nome não pode ser nulo");
        }
        if (valor <= 0){
            throw new TransacaoInvalidaException("O valor precisa ser positivo");
        }
        if (data == null){
            throw new TransacaoInvalidaException("A data não pode ser nulo");
        }
        if (data.isAfter(LocalDate.now())){
            throw new TransacaoInvalidaException("A data não pode ser do futuro");
        }
        if (tipoTransacao == null){
            throw new TransacaoInvalidaException("O tipo da transação não pode ser nulo");
        }
    }
}
