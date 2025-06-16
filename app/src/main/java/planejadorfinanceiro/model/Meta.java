package planejadorfinanceiro.model;

import java.time.LocalDate;
import java.util.UUID;

public class Meta {

    private UUID id;
    private String nome;
    private double valorAlvo;
    private double valorAtual;
    private LocalDate prazoFinal;

    // Construtor padrão necessário para deserialização JSON
    public Meta() {
        this.id = UUID.randomUUID();
        valorAtual = 0;
    }

    public Meta(String nome, double valorAlvo, LocalDate prazoFinal) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.prazoFinal = prazoFinal;
        valorAtual = 0;
    }

    public Meta(String nome, double valorAlvo, double valorAtual, LocalDate prazoFinal) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.valorAtual = valorAtual;
        this.prazoFinal = prazoFinal;
    }

    public void adicionarValorAtual(double valor){
        valorAtual += valor;
    }

    public void removerValorAtual(double valor){
        valorAtual -= valor;
    }

    public double calcularProgresso(){
        return (valorAtual / valorAlvo) * 100;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorAlvo() {
        return valorAlvo;
    }

    public void setValorAlvo(double valorAlvo) {
        this.valorAlvo = valorAlvo;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public LocalDate getPrazoFinal() {
        return prazoFinal;
    }

    public void setPrazoFinal(LocalDate prazoFinal) {
        this.prazoFinal = prazoFinal;
    }
}
