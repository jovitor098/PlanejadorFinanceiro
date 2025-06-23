package planejadorfinanceiro.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Classe que representa uma meta financeira definida por um cliente.
 * Cada meta possui um valor alvo, um valor atual, uma data final e um identificador único.
 */
public class Meta {

    private UUID id;
    private String nome;
    private double valorAlvo;
    private double valorAtual;
    private LocalDate prazoFinal;

    /**
     * Construtor padrão necessário para deserialização JSON.
     * Gera automaticamente um ID único e inicia o valor atual como 0.
     */
    public Meta() {
        this.id = UUID.randomUUID();
        valorAtual = 0;
    }

    /**
     * Construtor para a criação de uma nova meta com valor inicial zero.
     * @param nome Nome da meta
     * @param valorAlvo Valor alvo/final
     * @param prazoFinal Data final para o cumprimento da meta
     */
    public Meta(String nome, double valorAlvo, LocalDate prazoFinal) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.prazoFinal = prazoFinal;
        valorAtual = 0;
    }

    /**
     * Construtor completo para a criação de uma meta com valor atual definido 
     * além dos demais parâmetros.
     * 
     * @param nome Nome da meta
     * @param valorAlvo Valor alvo/final
     * @param valorAtual Valor já acumulado
     * @param prazoFinal Data final para o cumprimento da meta
     */
    public Meta(String nome, double valorAlvo, double valorAtual, LocalDate prazoFinal) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.valorAtual = valorAtual;
        this.prazoFinal = prazoFinal;
    }

    /**
     * Adiciona um valor ao já acumulado(valorAtual).
     * 
     * @param valor Valor a ser adicionado 
     */
    public void adicionarValorAtual(double valor){
        valorAtual += valor;
    }

    /**
     * Remove um valor do já acumulado(valorAtual).
     * 
     * @param valor Valor a ser recebido
     */
    public void removerValorAtual(double valor){
        valorAtual -= valor;
    }

    /**
     * Calcula o progresso da meta em porcentagem.
     * 
     * @return Percentual de progresso em relação ao alvo
     */
    public double calcularProgresso(){
        return (valorAtual / valorAlvo) * 100;
    }

    // Getters e Setters

    /**
     * @return Identificador único da meta
     */
    public UUID getId() {
        return id;
    }

    /**
     * Define o identificador único da meta.
     *
     * @param id UUID da meta
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return Nome da meta
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da meta.
     *
     * @param nome Nome descritivo
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return Valor-alvo da meta
     */
    public double getValorAlvo() {
        return valorAlvo;
    }

    /**
     * Define o valor-alvo da meta.
     *
     * @param valorAlvo Valor a ser atingido
     */
    public void setValorAlvo(double valorAlvo) {
        this.valorAlvo = valorAlvo;
    }

    /**
     * @return Valor atual acumulado na meta
     */
    public double getValorAtual() {
        return valorAtual;
    }

    /**
     * Define o valor atual acumulado na meta.
     *
     * @param valorAtual Valor acumulado até o momento
     */
    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    /**
     * @return Data limite para atingir a meta
     */
    public LocalDate getPrazoFinal() {
        return prazoFinal;
    }

    /**
     * Define a data limite para atingir a meta.
     *
     * @param prazoFinal Data final desejada
     */
    public void setPrazoFinal(LocalDate prazoFinal) {
        this.prazoFinal = prazoFinal;
    }
}
