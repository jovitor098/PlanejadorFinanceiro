package planejadorfinanceiro;

import java.time.LocalDate;

public class GerenciadorFinanceiro {
    private Cliente cliente;

    public void criarTransacaoEntrada(String nome, double valor, LocalDate data){
        Transacao novaTransacao = TransacaoFactory.criarEntrada(nome, valor, data);
        cliente.adicionarTransacao(novaTransacao);
    }

    public void criarTransacaoSaida(String nome, double valor, LocalDate data){
        Transacao novaTransacao = TransacaoFactory.criarSaida(nome, valor, data);
        cliente.adicionarTransacao(novaTransacao);
    }

    public void removerTransacao(Transacao transacao){
        cliente.removerTransacao(transacao);
    }

    public void atualizarTransacao(String nome, double valor, TipoTransacao tipoTransacao, LocalDate data){
        Transacao transacaoAtualizada = TransacaoFactory.criarTransacao(nome, valor, tipoTransacao, data);
        cliente.atualizarTransacao(transacaoAtualizada);
    }
}
