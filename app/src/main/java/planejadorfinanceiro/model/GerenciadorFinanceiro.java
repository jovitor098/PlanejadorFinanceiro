package planejadorfinanceiro.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class GerenciadorFinanceiro {
    private Cliente clienteLogado;
    private List<Cliente> clientes;
    private static GerenciadorFinanceiro instancia;

    private GerenciadorFinanceiro(){

    }

    public static GerenciadorFinanceiro getInstancia(){
        if (instancia == null){
            instancia = new GerenciadorFinanceiro();
        }
        return instancia;
    }

    public void criarTransacaoEntrada(String nome, double valor, LocalDate data){
        Transacao novaTransacao = TransacaoFactory.criarEntrada(nome, valor, data);
        clienteLogado.adicionarTransacao(novaTransacao);
    }

    public void criarTransacaoSaida(String nome, double valor, LocalDate data){
        Transacao novaTransacao = TransacaoFactory.criarSaida(nome, valor, data);
        clienteLogado.adicionarTransacao(novaTransacao);
    }

    public void removerTransacao(Transacao transacao){
        clienteLogado.removerTransacao(transacao);
    }

    public void atualizarTransacao(UUID transacaoId, String nome, double valor, TipoTransacao tipoTransacao, LocalDate data){
        Transacao transacaoAntiga = encontrarTransacaoPorId(transacaoId);
        if (transacaoAntiga != null) {
            transacaoAntiga.setNome(nome);
            transacaoAntiga.setValor(valor);
            transacaoAntiga.setTipo(tipoTransacao);
            transacaoAntiga.setData(data);
        }
    }

    public void criarMeta(String nome, double valorAlvo, LocalDate data){
        Meta novaMeta = new Meta(nome, valorAlvo, data);
        clienteLogado.adicionarMeta(novaMeta);
    }

    public void removerMeta(Meta meta){
        clienteLogado.removerMeta(meta);
    }

    public void atualizarMeta(UUID metaId, String nome, double valorAlvo, double valorAtual, LocalDate data){
        Meta metaAntiga = encontrarMetaPorId(metaId);
        if (metaAntiga != null) {
            Meta metaAtualizada = new Meta(nome, valorAlvo, valorAtual, data);
            metaAtualizada.setId(metaId); // Mantém o ID original
            clienteLogado.atualizarMeta(metaAtualizada);
        }
    }

    public void setClienteLogado(Cliente cliente) {
        this.clienteLogado = cliente;
    }

    // Métodos auxiliares
    private Transacao encontrarTransacaoPorId(UUID id) {
        return clienteLogado.getTransacoes()
                      .stream()
                      .filter(t -> t.getId().equals(id))
                      .findFirst()
                      .orElse(null);
    }

    private Meta encontrarMetaPorId(UUID id) {
        return clienteLogado.getMetas()
                      .stream()
                      .filter(m -> m.getId().equals(id))
                      .findFirst()
                      .orElse(null);
    }

    public Cliente getClienteLogado(){
        return clienteLogado;
    }

    public void setClientes(List<Cliente> clientes){
        this.clientes = clientes;
    }
}
