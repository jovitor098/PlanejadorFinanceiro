package planejadorfinanceiro.model;

import java.util.*;

public class Cliente {
    private String nome;
    private String email;
    private String senha;
    private double saldo = 0;
    private double entradaTotal = 0;
    private double saidaTotal = 0;
    private List<Transacao> transacoes = new ArrayList<>();
    private List<Meta> metas = new ArrayList<>();

    // Construtor padrão (sem argumentos) necessário para deserialização JSON
    public Cliente() {
        // Inicialização padrão
        this.transacoes = new ArrayList<>();
        this.metas = new ArrayList<>();
    }

    public Cliente(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void adicionarTransacao(Transacao transacao){
        transacoes.add(transacao);
        atualizarSaldos(transacao, true);
    }

    public void removerTransacao(Transacao transacao){
        if (transacoes.removeIf(t -> t.getId().equals(transacao.getId()))) {
            atualizarSaldos(transacao, false);
        }
    }

    public void atualizarTransacao(Transacao transacaoAtualizada){
        for (Transacao transacao : transacoes){
            if (transacao.getNome().equals(transacaoAtualizada.getNome())){
                transacoes.remove(transacao);
                break;
            }
        }
        transacoes.add(transacaoAtualizada);
    }

    public void adicionarMeta(Meta meta){
        metas.add(meta);
    }

    public void removerMeta(Meta meta){
        metas.removeIf(m -> m.getId().equals(meta.getId()));
    }

    public void atualizarMeta(Meta metaAtualizada){
        for (int i = 0; i < metas.size(); i++) {
            if (metas.get(i).getId().equals(metaAtualizada.getId())) {
                metas.set(i, metaAtualizada);
                return;
            }
        }
    }

    public List<Integer> getAnosTransacoes(){
        Set<Integer> anosTransacoes = new TreeSet<>();
        for (Transacao transacao : transacoes){
            anosTransacoes.add(transacao.getData().getYear());
        }
        return List.copyOf(anosTransacoes);
    }

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

    // Getters e Setters

    public double getEntradaTotal() {
        return entradaTotal;
    }
    public double getSaidaTotal() {
        return saidaTotal;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public List<Transacao> getTransacoes() {
        return transacoes;
    }
    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }
    public List<Meta> getMetas() {
        return metas;
    }
    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }
}
