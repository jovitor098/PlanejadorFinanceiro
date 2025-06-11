package planejadorfinanceiro;

import java.util.List;

public class Cliente {
    private String nome;
    private String email;
    private String senha;
    private double saldo;
    private double entradaTotal;
    private double saidaTotal;
    
    private List<Transacao> transacoes;
    private List<Meta> metas;

    public void adicionarTransacao(Transacao transacao){
        transacoes.add(transacao);
    }

    public void removerTransacao(Transacao transacao){
        transacoes.remove(transacao);
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
}
