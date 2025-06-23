package planejadorfinanceiro.model;

import planejadorfinanceiro.service.ClienteService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Classe responsável por centralizar e coordenar o gerenciador financeiro.
 * Utiliza o padrão Singleton para garantir uma única instância durante a execução.
 */
public class GerenciadorFinanceiro {
    private Cliente clienteLogado;
    private List<Cliente> clientes;
    private static GerenciadorFinanceiro instancia;

    /**
     * Construtor privado para o padrão Singleton.
     */
    private GerenciadorFinanceiro(){
    }

    /**
     * Retorna a instancia única do GerenciadorFinanceiro.
     * 
     * @return Instância única da classe
     */
    public static GerenciadorFinanceiro getInstancia(){
        if (instancia == null){
            instancia = new GerenciadorFinanceiro();
        }
        return instancia;
    }

    /**
     * Cria uma nova transação de entrada a partir de TransacaoFactory para o cliente logado.
     * 
     * @param nome nome da transação
     * @param valor valor da transação
     * @param data data da transação
     */
    public void criarTransacaoEntrada(String nome, double valor, LocalDate data){
        Transacao novaTransacao = TransacaoFactory.criarEntrada(nome, valor, data);
        clienteLogado.adicionarTransacao(novaTransacao);
        ClienteService.salvarClientes(clientes);
        clienteLogado.notificar("Transação de entrada criada com sucesso");
    }

    /**
     * Cria uma nova transação de saída a partir de TransacaoFactory para o cliente logado.
     * 
     * @param nome nome da transação
     * @param valor valor da transação
     * @param data data da transação
     */
    public void criarTransacaoSaida(String nome, double valor, LocalDate data){
        Transacao novaTransacao = TransacaoFactory.criarSaida(nome, valor, data);
        clienteLogado.adicionarTransacao(novaTransacao);
        ClienteService.salvarClientes(clientes);
        clienteLogado.notificar("Transação de saída criada com sucesso");
    }

    /**
     * Remove uma transação já existente do perfil do cliente logado.
     * 
     * @param transacao A transação a ser removida
     */
    public void removerTransacao(Transacao transacao){
        clienteLogado.removerTransacao(transacao);
        ClienteService.salvarClientes(clientes);
        clienteLogado.notificar("Transacação removida com sucesso");
    }

    /**
     * Atualiza uma transação já existente com novos dados(edição).
     * 
     * @param transacaoId O ID único da transação
     * @param nome O nome da transação
     * @param valor O valor da transaçaõ
     * @param tipoTransacao O tipo da transação
     * @param data A data da transação
     */
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

    /**
     * Cria uma nova meta financeira para o cliente logado.
     * 
     * @param nome O nome da meta
     * @param valorAlvo O valor alvo ou final da meta
     * @param data A data objetivo para atingir a meta 
     */
    public void criarMeta(String nome, double valorAlvo, LocalDate data){
        Meta novaMeta = new Meta(nome, valorAlvo, data);
        clienteLogado.adicionarMeta(novaMeta);
        ClienteService.salvarClientes(clientes);
        clienteLogado.notificar("Meta criada com sucesso");
    }

    /**
     * Remove uma meta financeir já existente do cliente logado.
     * 
     * @param meta A meta a ser removida
     */
    public void removerMeta(Meta meta){
        clienteLogado.removerMeta(meta);
        ClienteService.salvarClientes(clientes);
        clienteLogado.notificar("Meta removida com sucesso");
    }

    /**
     * Atualiza uma meta já existente do cliente logado(edição).
     * 
     * @param metaId O ID único da meta
     * @param nome O nome da meta
     * @param valorAlvo O valor alvo/final da meta
     * @param valorAtual O valor atual alcançado da meta
     * @param data A data objetivo para atingir a meta 
     */
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

    /**
     * Define o cliente atualmente logado no sistema. 
     * Garante que que o objeto(cliente) seja o mesmo da lista
     * 
     * @param cliente Cliente que realizou login
     */
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
    /**
     * Metodo auxiliar que busca uma transação pelo seu ID único.
     * 
     * @param id O ID da transação a ser buscada
     * @return Transação encontrada ou null caso não encontre
     */
    private Transacao encontrarTransacaoPorId(UUID id) {
        return clienteLogado.getTransacoes()
                      .stream()
                      .filter(t -> t.getId().equals(id))
                      .findFirst()
                      .orElse(null);
    }

    /**
     * Metodo auxiliar que busca uma meta pelo seu ID único.
     * 
     * @param id O ID da mneta a ser encontrada
     * @return A meta encotrada ou null caso não encontre
     */
    private Meta encontrarMetaPorId(UUID id) {
        return clienteLogado.getMetas()
                      .stream()
                      .filter(m -> m.getId().equals(id))
                      .findFirst()
                      .orElse(null);
    }

    /**
     * Retorna o cliente atualmente logado.
     * 
     * @return Cliente logado
     */
    public Cliente getClienteLogado(){
        return clienteLogado;
    }

    /**
     * Define a lista completa de clientes do sistema.
     * 
     * @param clientes Lista de clientes
     */
    public void setClientes(List<Cliente> clientes){
        this.clientes = clientes;
    }
}
