package planejadorfinanceiro;

import java.time.LocalDate;

public class Meta {
    private String nome;
    private double valorAlvo;
    private double valorAtual;
    private LocalDate prazoFinal;

    

    public Meta(String nome, double valorAlvo, LocalDate prazoFinal) {
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.prazoFinal = prazoFinal;
        valorAtual = 0;
    }

    public Meta(String nome, double valorAlvo, double valorAtual, LocalDate prazoFinal) {
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

    public String getNome() {
        return nome;
    }
}
