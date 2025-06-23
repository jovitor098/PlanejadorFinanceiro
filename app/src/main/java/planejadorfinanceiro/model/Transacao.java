package planejadorfinanceiro.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Representa uma transação financeira, que pode ser uma entrada ou uma saída.
 * Cada transação possui um valor, nome, tipo, data e um identificador único.
 */
public class Transacao {

    private UUID id;
    private double valor;
    private String nome;
    private TipoTransacao tipo;
    private LocalDate data;
    
    /**
     * Construtor padrão necessário para deserialização JSON.
     * Gera um ID único.
     *  */ 
    public Transacao() {
        this.id = UUID.randomUUID(); // Garante que mesmo ao deserializar, haja um ID
    }

    /**
     * Construtor completo de uma transação
     * 
     * @param valor Valor da transação
     * @param nome Nome da transação
     * @param tipo Tipo da transação(entrada/saída)
     * @param data Data da realização da transação
     */
    public Transacao(double valor, String nome, TipoTransacao tipo, LocalDate data) {
        this.id = UUID.randomUUID();
        this.valor = valor;
        this.nome = nome;
        this.tipo = tipo;
        this.data = data;
    }

    // Getters e Setters

    /**
     * @return Identificador único da transação
     */
    public UUID getId() {
        return id;
    }

    /**
     * Define manualmente o identificador da transação.
     * 
     * @param id UUID da transação
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return Nome da transação
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * @return Valor da transação
     */
    public double getValor() {
        return valor;
    }
    
    /**
     * @return Tipo da transação(entrada/saida)
     */
    public TipoTransacao getTipo() {
        return tipo;
    }
    
    /**
     * @return Data em que a transação ocorreu
     */
    public LocalDate getData() {
        return data;
    }
    
    /**
     * Define o valor da transação.
     * 
     * @param valor Valor da transação
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    
    /**
     * Define o nome da transação.
     * 
     * @param nome Nome da transação
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Define o tipo da transação.
     * 
     * @param tipo Tipo (ENTRADA ou SAIDA)
     */
    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Define a data da transação.
     * 
     * @param data Data da transação
     */
    public void setData(LocalDate data) {
        this.data = data;
    }
}
