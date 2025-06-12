package planejadorfinanceiro;

import java.util.List;
import java.util.UUID;

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
            if (transacao.getId().equals(transacaoAtualizada.getId())){
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
        metas.remove(meta);
    }

    public void atualizarMeta(Meta metaAtualizada){
        for (Meta meta : metas){
            if (meta.getNome().equals(metaAtualizada.getNome())){
                metas.remove(meta);
                break;
            }
        }
        metas.add(metaAtualizada);
    }
}
