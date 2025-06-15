package planejadorfinanceiro.ui.componentes;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import planejadorfinanceiro.model.Cliente;
import planejadorfinanceiro.model.Transacao;

public class GraficoSaldo extends LineChart<String, Number> {
    private Cliente cliente;
    // Constante para o locale pt-BR
    private static final Locale LOCALE_PT_BR = Locale.of("pt", "BR");

    public GraficoSaldo() {
        super(new CategoryAxis(), new NumberAxis());
        CategoryAxis xAxis = (CategoryAxis) getXAxis();
        // Define o rótulo do eixo X como "Meses"
        xAxis.setLabel("Meses");

        NumberAxis yAxis = ((NumberAxis) getYAxis());
        // Define o rótulo do eixo Y como "Saldo"
        yAxis.setLabel("Saldo (R$)");

        // Configura o eixo X com todos os meses
        configurarEixoXComTodosMeses(xAxis);
    }

    private void configurarEixoXComTodosMeses(CategoryAxis xAxis){
        // Obtém os nomes abreviados dos meses em português
        List<String> meses = Arrays.stream(Month.values())
                .map(m -> m.getDisplayName(TextStyle.SHORT, LOCALE_PT_BR))
                .toList();
        // Define as categorias do eixo X com a lista de meses
        xAxis.setCategories(FXCollections.observableArrayList(meses));
    }

    public void atualizarAnoGrafico(int ano){
        Series<String, Number> serieSaldoAno = criarSerieSaldoMensal(ano);
        // Remove os dados anteriores do gráfico
        getData().clear();
        // Adiciona a nova série ao gráfico
        getData().add(serieSaldoAno);
    }

    private Series<String, Number> criarSerieSaldoMensal(int ano){
        // Calcula o saldo inicial acumulado de anos anteriores
        double saldoAcumulado = calcularSaldoTotalAnosAnteriores(ano);
        // Obtém o mapa de saldos mensais para o ano especificado
        Map<Month, Double> saldoMensal = calcularSaldoMensal(ano);

        Series<String, Number> serieMensal = new Series<>();
        serieMensal.setName("Saldo do ano de " + ano);
        LocalDate dataAtual = LocalDate.now();
        int anoAtual = dataAtual.getYear();
        Month mesAtual = dataAtual.getMonth();

        // Itera sobre os meses para criar os pontos de dados
        for (Month mes : Month.values()){
            // Interrompe a inclusão de meses futuros para o ano atual
            if (ano == anoAtual && mes.getValue() > mesAtual.getValue()) {
                break; // Não incluir meses futuros
            }
            saldoAcumulado += saldoMensal.getOrDefault(mes, 0.0);
            // Adiciona o ponto de dados ao gráfico
            serieMensal.getData().add(
                    new Data<>(mes.getDisplayName(TextStyle.SHORT, LOCALE_PT_BR), saldoAcumulado)
            );
        }
        return serieMensal;
    }

    private Map<Month, Double> calcularSaldoMensal(int ano){
        Map<Month, Double> saldoMes = new EnumMap<>(Month.class);
        // Filtra as transações do ano especificado
        List<Transacao> transacoesAno = cliente.getTransacoes().stream()
                .filter(t -> t.getData().getYear() == ano)
                .toList();

        // Soma os valores das transações por mês
        for (Transacao transacao : transacoesAno){
            Month mes = transacao.getData().getMonth();
            saldoMes.put(mes, saldoMes.getOrDefault(mes, 0.0) + transacao.getValor());
        }
        return saldoMes;
    }

    private double calcularSaldoTotalAnosAnteriores(int ano){
        // Calcula o saldo total acumulado de todos os anos anteriores ao ano especificado.
        return cliente.getTransacoes().stream()
                .filter(t -> t.getData().getYear() < ano)
                .mapToDouble(Transacao::getValor)
                .sum();
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
