package planejadorfinanceiro.model;

import java.time.LocalDate;
import java.util.UUID;

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

    public void atualizarTransacao(UUID transacaoId, String nome, double valor, TipoTransacao tipoTransacao, LocalDate data){
        Transacao transacaoAntiga = encontrarTransacaoPorId(transacaoId);
        if (transacaoAntiga != null) {
            Transacao transacaoAtualizada = new Transacao(valor, nome, tipoTransacao, data);
            transacaoAtualizada.setId(transacaoId); // Mantém o ID original
            cliente.atualizarTransacao(transacaoAtualizada);
        }
    }

    public void criarMeta(String nome, double valorAlvo, LocalDate data){
        Meta novaMeta = new Meta(nome, valorAlvo, data);
        cliente.adicionarMeta(novaMeta);
    }

    public void removerMeta(Meta meta){
        cliente.removerMeta(meta);
    }

    public void atualizarMeta(UUID metaId, String nome, double valorAlvo, double valorAtual, LocalDate data){
        Meta metaAntiga = encontrarMetaPorId(metaId);
        if (metaAntiga != null) {
            Meta metaAtualizada = new Meta(nome, valorAlvo, valorAtual, data);
            metaAtualizada.setId(metaId); // Mantém o ID original
            cliente.atualizarMeta(metaAtualizada);
        }
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Métodos auxiliares
    private Transacao encontrarTransacaoPorId(UUID id) {
        return cliente.getTransacoes()
                      .stream()
                      .filter(t -> t.getId().equals(id))
                      .findFirst()
                      .orElse(null);
    }

    private Meta encontrarMetaPorId(UUID id) {
        return cliente.getMetas()
                      .stream()
                      .filter(m -> m.getId().equals(id))
                      .findFirst()
                      .orElse(null);
    }
}
