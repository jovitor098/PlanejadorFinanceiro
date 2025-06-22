package planejadorfinanceiro.model;

import planejadorfinanceiro.service.ClienteService;

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
        ClienteService.salvarClientes(clientes);
        clienteLogado.notificar("Transação de entrada criada com sucesso");
    }

    public void criarTransacaoSaida(String nome, double valor, LocalDate data){
        Transacao novaTransacao = TransacaoFactory.criarSaida(nome, valor, data);
        clienteLogado.adicionarTransacao(novaTransacao);
        ClienteService.salvarClientes(clientes);
        clienteLogado.notificar("Transação de saída criada com sucesso");
    }

    public void removerTransacao(Transacao transacao){
        clienteLogado.removerTransacao(transacao);
        ClienteService.salvarClientes(clientes);
        clienteLogado.notificar("Transacação removida com sucesso");
    }

    public void atualizarTransacao(UUID transacaoId, String nome, double valor, TipoTransacao tipoTransacao, LocalDate data){
        Transacao transacaoAntiga = encontrarTransacaoPorId(transacaoId);
        if (transacaoAntiga != null) {
            Transacao transacaoAtualizada = TransacaoFactory.criarTransacao(nome, valor, tipoTransacao, data);
            transacaoAtualizada.setId(transacaoId);
            clienteLogado.atualizarTransacao(transacaoAtualizada);
            clienteLogado.notificar("Transação atualizada com sucesso");
            ClienteService.salvarClientes(clientes);
        }
    }

    public void criarMeta(String nome, double valorAlvo, LocalDate data){
        Meta novaMeta = new Meta(nome, valorAlvo, data);
        clienteLogado.adicionarMeta(novaMeta);
        ClienteService.salvarClientes(clientes);
        clienteLogado.notificar("Meta criada com sucesso");
    }

    public void removerMeta(Meta meta){
        clienteLogado.removerMeta(meta);
        ClienteService.salvarClientes(clientes);
        clienteLogado.notificar("Meta removida com sucesso");
    }

    public void atualizarMeta(UUID metaId, String nome, double valorAlvo, double valorAtual, LocalDate data){
        Meta metaAntiga = encontrarMetaPorId(metaId);
        if (metaAntiga != null) {
            Meta metaAtualizada = new Meta(nome, valorAlvo, valorAtual, data);
            metaAtualizada.setId(metaId); // Mantém o ID original
            clienteLogado.atualizarMeta(metaAtualizada);
            clienteLogado.notificar("Meta atualizada com sucesso");
            ClienteService.salvarClientes(clientes);
        }
    }

    public void setClienteLogado(Cliente cliente) {
        this.clienteLogado = cliente;
        // Verifica se o cliente esta na lista
        for (Cliente c : this.clientes){
            if (c.getEmail().equals(cliente.getEmail())){
                clienteLogado = c;
            }
        }
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
