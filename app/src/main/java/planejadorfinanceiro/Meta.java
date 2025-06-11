package planejadorfinanceiro;

import java.time.LocalDate;

public class Meta {
    private String nome;
    private double valorAlvo;
    private double valorAtual;
    private LocalDate prazoFinal;

    public void adicionarValorAtual(double valor){
        valorAtual += valor;
    }

    public void removerValorAtual(double valor){
        valorAtual -= valor;
    }

    public double calcularProgresso(){
        return (valorAtual / valorAlvo) * 100;
    }
}
