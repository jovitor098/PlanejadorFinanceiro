package planejadorfinanceiro;

import java.time.LocalDate;
import java.util.UUID;

public class Transacao {

    private UUID id;
    private double valor;
    private String nome;
    private TipoTransacao tipo;
    private LocalDate data;

    public Transacao(double valor, String nome, TipoTransacao tipo, LocalDate data) {
        this.id = UUID.randomUUID();
        this.valor = valor;
        this.nome = nome;
        this.tipo = tipo;
        this.data = data;
    }
    public UUID getId(){
        return id;
    }
    public String getNome() {
        return nome;
    }
    
}
