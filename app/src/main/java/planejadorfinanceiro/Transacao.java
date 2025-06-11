package planejadorfinanceiro;

import java.time.LocalDate;

public class Transacao {

    public Transacao(double valor, String nome, TipoTransacao tipo, LocalDate data) {
        this.valor = valor;
        this.nome = nome;
        this.tipo = tipo;
        this.data = data;
    }

    private double valor;
    private String nome;
    private TipoTransacao tipo;
    private LocalDate data;

    public String getNome() {
        return nome;
    }
    
}
