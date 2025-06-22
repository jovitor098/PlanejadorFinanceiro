package planejadorfinanceiro.model;

import planejadorfinanceiro.excecoes.TransacaoInvalidaException;

import java.time.LocalDate;

public class TransacaoFactory {
    public static Transacao criarEntrada(String nome, double valor, LocalDate data){
        validarTransacao(nome, valor, TipoTransacao.ENTRADA, data);
        return new Transacao(valor, nome, TipoTransacao.ENTRADA, data);
    }

    public static Transacao criarSaida(String nome, double valor, LocalDate data){
        validarTransacao(nome, valor, TipoTransacao.SAIDA, data);
        return new Transacao(valor, nome, TipoTransacao.SAIDA, data);
    }

    public static Transacao criarTransacao(String nome, double valor, TipoTransacao tipoTransacao, LocalDate data){
        validarTransacao(nome, valor, tipoTransacao, data);
        return new Transacao(valor, nome, tipoTransacao, data);
    }

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
