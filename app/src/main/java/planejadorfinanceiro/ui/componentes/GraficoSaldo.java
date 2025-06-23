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
import planejadorfinanceiro.model.TipoTransacao;
import planejadorfinanceiro.model.Transacao;

/**
 * Gráfico que exibe a evolução saldo mensal de um cliente ao longo de um ano.
 * Extende LineChart com meses no eixo X e valores financeiro no eixo Y.
 */
public class GraficoSaldo extends LineChart<String, Number> {

    /** Cliente que o saldo será usado no gráfico. */
    private Cliente cliente;
    /** Constante para o locale exibir em pt-BR. */
    private static final Locale LOCALE_PT_BR = Locale.of("pt", "BR");

    /** Construtor padrão que configura o gráfico e seus eixos com as informações adequadas. */
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

    /**
     * Configura o eixo X com todos os meses do ano em ordem e com abreviações.
     * 
     * @param xAxis Eixo X a ser configurado
     */
    private void configurarEixoXComTodosMeses(CategoryAxis xAxis){
        // Obtém os nomes abreviados dos meses em português
        List<String> meses = Arrays.stream(Month.values())
                .map(m -> m.getDisplayName(TextStyle.SHORT, LOCALE_PT_BR))
                .toList();
        // Define as categorias do eixo X com a lista de meses
        xAxis.setCategories(FXCollections.observableArrayList(meses));
    }

    /**
     * Atualiza o gráfico com os dados de saldo acumulado ao longo dos meses do ano específico.
     * Remove os dados anteriores e adiciona uma nova série ao gráfico.
     *
     * @param ano O ano representado no gráfico
     */
    public void atualizarAnoGrafico(int ano){
        Series<String, Number> serieSaldoAno = criarSerieSaldoMensal(ano);
        // Remove os dados anteriores do gráfico
        getData().clear();
        // Adiciona a nova série ao gráfico
        getData().add(serieSaldoAno);
    }

    /**
     * Cria uma série de dados contendo o saldo acumulado mês a mês do ano específico.
     *
     * @param ano O ano a ser visualizado
     * @return Uma série contendo os dados mensais de saldo
     */
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

    /**
     * Calcula o saldo mensal(entrada - saída) para cada mês do ano específico.
     *
     * @param ano O ano para o qual os saldos mensais serão calculados
     * @return Mapa com saldo por mẽs
     */
    private Map<Month, Double> calcularSaldoMensal(int ano){
        Map<Month, Double> saldoMes = new EnumMap<>(Month.class);
        // Filtra as transações do ano especificado
        List<Transacao> transacoesAno = cliente.getTransacoes().stream()
                .filter(t -> t.getData().getYear() == ano)
                .toList();

        // Soma os valores das transações por mês
        for (Transacao transacao : transacoesAno){
            Month mes = transacao.getData().getMonth();
            if (transacao.getTipo() == TipoTransacao.ENTRADA){
                saldoMes.put(mes, saldoMes.getOrDefault(mes, 0.0) + transacao.getValor());
            }
            else {
                saldoMes.put(mes, saldoMes.getOrDefault(mes, 0.0) - transacao.getValor());
            }
        }
        return saldoMes;
    }

    /**
     * Calcula o saldo acumulado de todos os anos anteriores ao ano específico.
     *
     * @param ano Ano de referência
     * @return Saldo acumulado dos últimos anos
     */
    private double calcularSaldoTotalAnosAnteriores(int ano){
        // Calcula o saldo total acumulado de todos os anos anteriores ao ano especificado.
        return cliente.getTransacoes().stream()
                .filter(t -> t.getData().getYear() < ano)
                .mapToDouble(t -> {
                        if (t.getTipo() == TipoTransacao.ENTRADA){
                            return t.getValor();
                        }
                        else {
                            return - t.getValor();
                        }
                })
                .sum();
    }

    /**
     * Define o cliente de onde se obtem as transações que serão usadas para gerar o gráfico.
     *
     * @param cliente Cliente a ser associado ao gráfico
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
