package planejadorfinanceiro;

import java.time.LocalDate;

public class TransacaoFactory {
    public static Transacao criarEntrada(String nome, double valor, LocalDate data){
        return new Transacao(valor, nome, TipoTransacao.ENTRADA, data);
    }

    public static Transacao criarSaida(String nome, double valor, LocalDate data){
        return new Transacao(valor, nome, TipoTransacao.SAIDA, data);
    }

    public static Transacao criarTransacao(String nome, double valor, TipoTransacao tipoTransacao, LocalDate data){
        return new Transacao(valor, nome, tipoTransacao, data);
    }
}
