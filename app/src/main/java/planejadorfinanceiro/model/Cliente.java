package planejadorfinanceiro.model;

import java.util.*;

import planejadorfinanceiro.ui.componentes.NotificadorJavaFX;


/**
 * Representa um cliente do sistema de planejamento financeiro.
 * Contém informações pessoais, transações financeiras, metas e uma lista de notificações.
 */
public class Cliente {
    private String nome;
    private String email;
    private String senha;
    private double saldo = 0;
    private double entradaTotal = 0;
    private double saidaTotal = 0;
    private List<Transacao> transacoes = new ArrayList<>();
    private List<Meta> metas = new ArrayList<>();
    private List<Notificavel> notificaveis = new ArrayList<>();

    /**
     * Construtor padrão (sem argumentos) necessário para deserialização JSON.
     */ 
    public Cliente() {
        this.transacoes = new ArrayList<>();
        this.metas = new ArrayList<>();
        this.notificaveis = new ArrayList<>();
        notificaveis.add(new NotificadorJavaFX());
    }
    
    /**
     * Construtor que inicializa um cliente com nome, email e senha.
     * 
     * @param nome Nome do cliente
     * @param email Endereço de email do cliente
     * @param senha Senha do cliente
     */
    public Cliente(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    /**
     * Adiciona uma nova transação ao cliente e atualiza o saldo.
     * 
     * @param transacao Transação a ser adicionada
     */
    public void adicionarTransacao(Transacao transacao){
        transacoes.add(transacao);
        atualizarSaldos(transacao, true);
    }

    /**
     * Remove uma transação já existente do cliente e atualiza o saldo.
     * 
     * @param transacao Transação a ser removida
     */
    public void removerTransacao(Transacao transacao){
        if (transacoes.removeIf(t -> t.getId().equals(transacao.getId()))) {
            atualizarSaldos(transacao, false);
        }
    }

    /**
     * Atualiza uma transação existente com novos dados (é a maneira como se realiza
     * a "edição" de uma transação).
     * 
     * @param transacaoAtualizada Transação a ser atualizada
     */
    public void atualizarTransacao(Transacao transacaoAtualizada){
        for (int i = 0; i < transacoes.size(); i++){
            if (transacoes.get(i).getId().equals(transacaoAtualizada.getId())){
                transacoes.set(i, transacaoAtualizada);
                return;
            }
        }
    }

    /**
     * Adiciona uma nova meta financeira ao cliente.
     * 
     * @param meta Meta a ser adicionada 
     */
    public void adicionarMeta(Meta meta){
        metas.add(meta);
    }

    /**
     * Remove uma meta financeira já existente do cliente.
     * 
     * @param meta Meta a ser removida
     */
    public void removerMeta(Meta meta){
        metas.removeIf(m -> m.getId().equals(meta.getId()));
    }


    /**
     * Atualiza uma meta financeira existente com novos dados (é a maneira como se realiza
     * a "edição" de uma meta).
     * 
     * @param metaAtualizada Meta a ser atualizada
     */
    public void atualizarMeta(Meta metaAtualizada){
        for (int i = 0; i < metas.size(); i++) {
            if (metas.get(i).getId().equals(metaAtualizada.getId())) {
                metas.set(i, metaAtualizada);
                return;
            }
        }
    }

    /**
     * Retorna uma lista com os anos em que o cliente possui transações.
     * 
     * @return anosTransacoes - Lista
     */
    public List<Integer> obterAnosTransacoes(){
        Set<Integer> anosTransacoes = new TreeSet<>();
        for (Transacao transacao : transacoes){
            anosTransacoes.add(transacao.getData().getYear());
        }
        return List.copyOf(anosTransacoes);
    }

    /**
     * Atualiza o saldo do cliente e a entrada ou saída total quando se realiza uma nova transação.
     * 
     * @param transacao A transação a ser processada
     * @param adicionar Se true, o valor é somado, se false é subtraído
     */
    private void atualizarSaldos(Transacao transacao, boolean adicionar){
        double valor = transacao.getValor();
        if (transacao.getTipo() == TipoTransacao.ENTRADA) {
            entradaTotal += adicionar ? valor : -valor;
            saldo += adicionar ? valor : -valor;
        } else {
            saidaTotal += adicionar ? valor : -valor;
            saldo += adicionar ? -valor : valor;
        }
    }

    /**
     * Envia uma notificação por todos os meios associados.
     * 
     * @param mensagem A mensagem a ser enviada 
     */
    public void notificar(String mensagem){
        for (Notificavel notificavel : notificaveis){
            notificavel.notificar(mensagem);
        }
    }

    // Getters e Setters

    /**
     * @return Valor de todas entradas
     */
    public double getEntradaTotal() {
        return entradaTotal;
    }
    
    /**
     * @return Valor de todas saídas
     */
    public double getSaidaTotal() {
        return saidaTotal;
    }
    
    /**
     * @return String com o nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Estabelece o nome so cliente.
     * 
     * @param nome Nome do cliente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return O nome do cliente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o endereço de email do cliente.
     * 
     * @param email Nome do endereço de email do cliente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Endereço de email do cliente
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do cliente.
     * 
     * @param senha A senha do cliente
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return Saldo do cliente
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Define o saldo do cliente.
     * 
     * @param saldo Saldo do cliente
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * @return Lista de transações do cliente
     */
    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    /**
     * Define a lista de transações realizadas pelo cliente.
     * 
     * @param transacoes Lista de transações do cliente
     */
    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    /**     
     * @return Lista das metas do cliente.
     */
    public List<Meta> getMetas() {
        return metas;
    }

    /**
     * Define a lista de metas atribuídas ao cliente.
     * 
     * @param metas Lista de metas do cliente
     */
    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }
}
