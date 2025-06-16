package planejadorfinanceiro.model;

import java.time.LocalDate;
import java.util.UUID;

public class Transacao {

    private UUID id;
    private double valor;
    private String nome;
    private TipoTransacao tipo;
    private LocalDate data;
    
    // Construtor padrão necessário para deserialização JSON
    public Transacao() {
        this.id = UUID.randomUUID(); // Garante que mesmo ao deserializar, haja um ID
    }

    public Transacao(double valor, String nome, TipoTransacao tipo, LocalDate data) {
        this.id = UUID.randomUUID();
        this.valor = valor;
        this.nome = nome;
        this.tipo = tipo;
        this.data = data;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    
    public double getValor() {
        return valor;
    }
    
    public TipoTransacao getTipo() {
        return tipo;
    }
    
    public LocalDate getData() {
        return data;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }
    
    public void setData(LocalDate data) {
        this.data = data;
    }
}
