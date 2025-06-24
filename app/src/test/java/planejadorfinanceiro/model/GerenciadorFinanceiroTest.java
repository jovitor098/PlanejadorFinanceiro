package planejadorfinanceiro.model;

import org.junit.jupiter.api.Test;
import planejadorfinanceiro.service.ClienteService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GerenciadorFinanceiroTest {

    @Test
    void criarTransacaoEntradaTeste() {
        // Configura o cliente service
        ClienteService.carregarCaminhoArquivo(GerenciadorFinanceiroTest.class);
        GerenciadorFinanceiro gerenciador = GerenciadorFinanceiro.getInstancia();

        // Configura o cliente
        Cliente cliente = new Cliente("Joao", "joao@email.com", "123");
        List<Cliente> clientes = List.of(cliente);
        gerenciador.setClientes(clientes);
        gerenciador.setClienteLogado(cliente);

        // Cria a transação
        gerenciador.criarTransacaoEntrada("Salário", 2000.0, LocalDate.now());
        Transacao transacao = cliente.getTransacoes().getFirst();

        // Verifica os campos
        assertEquals(1, cliente.getTransacoes().size());
        assertEquals("Salário", transacao.getNome());
        assertEquals(2000.0, transacao.getValor());
        assertEquals(TipoTransacao.ENTRADA, transacao.getTipo());
        assertEquals(LocalDate.now(), transacao.getData());
    }

    @Test
    void removerTransacaoTeste() {
        // Configura o cliente service
        ClienteService.carregarCaminhoArquivo(GerenciadorFinanceiroTest.class);
        GerenciadorFinanceiro gerenciador = GerenciadorFinanceiro.getInstancia();

        // Configura o cliente
        Cliente cliente = new Cliente("Joao", "joao@email.com", "123");
        List<Cliente> clientes = List.of(cliente);
        gerenciador.setClientes(clientes);
        gerenciador.setClienteLogado(cliente);

        gerenciador.criarTransacaoEntrada("Salário", 2000.0, LocalDate.now());
        Transacao transacao = cliente.getTransacoes().getFirst();
        // Remove a transação
        gerenciador.removerTransacao(transacao);
        // Verifica se removeu
        assertTrue(cliente.getTransacoes().isEmpty());
    }

    @Test
    void atualizarTransacaoTeste() {
        // Configura o cliente service
        ClienteService.carregarCaminhoArquivo(GerenciadorFinanceiroTest.class);
        GerenciadorFinanceiro gerenciador = GerenciadorFinanceiro.getInstancia();

        // Configura o cliente
        Cliente cliente = new Cliente("Joao", "joao@email.com", "123");
        List<Cliente> clientes = List.of(cliente);
        gerenciador.setClientes(clientes);
        gerenciador.setClienteLogado(cliente);

        gerenciador.criarTransacaoEntrada("Salário", 2000.0, LocalDate.now());
        UUID id = cliente.getTransacoes().getFirst().getId();
        // Atualiza a transação
        gerenciador.atualizarTransacao(id, "Alimentação", 1000, TipoTransacao.SAIDA, LocalDate.now().minusDays(1));
        Transacao transacaoAtualizada = cliente.getTransacoes().getFirst();
        
        // Verifica os campos
        assertEquals(id, transacaoAtualizada.getId());
        assertEquals("Alimentação", transacaoAtualizada.getNome());
        assertEquals(1000, transacaoAtualizada.getValor());
        assertEquals(TipoTransacao.SAIDA, transacaoAtualizada.getTipo());
        assertEquals(LocalDate.now().minusDays(1), transacaoAtualizada.getData());
    }

    @Test
    void criarMetaTeste() {
        // Configura o cliente service
        ClienteService.carregarCaminhoArquivo(GerenciadorFinanceiroTest.class);
        GerenciadorFinanceiro gerenciador = GerenciadorFinanceiro.getInstancia();
        
        // Configura o cliente
        Cliente cliente = new Cliente("Joao", "joao@email.com", "123");
        List<Cliente> clientes = List.of(cliente);
        gerenciador.setClientes(clientes);
        gerenciador.setClienteLogado(cliente);

        gerenciador.criarMeta("Viagem", 5000.0, LocalDate.of(2025, 12, 31));

        // Verifica os campos
        assertEquals(1, cliente.getMetas().size());
        Meta meta = cliente.getMetas().getFirst();
        assertEquals("Viagem", meta.getNome());
        assertEquals(5000.0, meta.getValorAlvo());
        assertEquals(LocalDate.of(2025, 12, 31), meta.getPrazoFinal());
    }

    @Test
    void removerMetaTeste() {
        // Configura o cliente service
        ClienteService.carregarCaminhoArquivo(GerenciadorFinanceiroTest.class);
        GerenciadorFinanceiro gerenciador = GerenciadorFinanceiro.getInstancia();

        // Configura o cliente
        Cliente cliente = new Cliente("Joao", "joao@email.com", "123");
        List<Cliente> clientes = List.of(cliente);
        gerenciador.setClientes(clientes);
        gerenciador.setClienteLogado(cliente);

        gerenciador.criarMeta("Viagem", 5000.0, LocalDate.of(2025, 12, 31));
        Meta meta = cliente.getMetas().getFirst();

        gerenciador.removerMeta(meta);
        // Verifica se removeu
        assertTrue(cliente.getMetas().isEmpty());
    }

    @Test
    void atualizarMetaTeste() {
        // Configura o cliente service
        ClienteService.carregarCaminhoArquivo(GerenciadorFinanceiroTest.class);
        GerenciadorFinanceiro gerenciador = GerenciadorFinanceiro.getInstancia();

        // Configura o cliente
        Cliente cliente = new Cliente("Joao", "joao@email.com", "123");
        List<Cliente> clientes = List.of(cliente);
        gerenciador.setClientes(clientes);
        gerenciador.setClienteLogado(cliente);

        gerenciador.criarMeta("Viagem", 5000.0, LocalDate.of(2025, 12, 31));
        Meta meta = cliente.getMetas().getFirst();
        UUID id = meta.getId();

        gerenciador.atualizarMeta(id, "Curso", 3000.0, 1000.0, LocalDate.now());
        Meta metaAtualizada = cliente.getMetas().getFirst();

        // Verifica os campos
        assertEquals(id, metaAtualizada.getId());
        assertEquals("Curso", metaAtualizada.getNome());
        assertEquals(3000.0, metaAtualizada.getValorAlvo());
        assertEquals(1000.0, metaAtualizada.getValorAtual());
        assertEquals(LocalDate.now(), metaAtualizada.getPrazoFinal());
    }
}