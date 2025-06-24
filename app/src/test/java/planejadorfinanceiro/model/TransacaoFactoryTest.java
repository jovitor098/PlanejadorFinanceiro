package planejadorfinanceiro.model;

import org.junit.jupiter.api.Test;
import planejadorfinanceiro.excecoes.TransacaoInvalidaException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransacaoFactoryTest {
    @Test
    public void criarEntradaTeste(){
        Transacao transacao = TransacaoFactory.criarEntrada("Salário", 2000, LocalDate.now());
        assertEquals(TipoTransacao.ENTRADA, transacao.getTipo());
        assertEquals("Salário", transacao.getNome());
        assertEquals(2000, transacao.getValor());
        assertEquals(LocalDate.now(), transacao.getData());
    }

    @Test
    public void criarSaidaTeste(){
        Transacao transacao = TransacaoFactory.criarSaida("Compras", 100, LocalDate.now());
        assertEquals(TipoTransacao.SAIDA, transacao.getTipo());
        assertEquals("Compras", transacao.getNome());
        assertEquals(100, transacao.getValor());
        assertEquals(LocalDate.now(), transacao.getData());
    }

    @Test
    public void criarTransacaoTeste(){
        Transacao transacao = TransacaoFactory.criarTransacao("Aposta", 1500, TipoTransacao.SAIDA, LocalDate.now());
        assertEquals(TipoTransacao.SAIDA, transacao.getTipo());
        assertEquals("Aposta", transacao.getNome());
        assertEquals(1500, transacao.getValor());
        assertEquals(LocalDate.now(), transacao.getData());
    }

    @Test
    public void validacaoNomeTeste(){
        TransacaoInvalidaException exception = assertThrows(TransacaoInvalidaException.class,
                () -> TransacaoFactory.criarTransacao("", 1000, TipoTransacao.ENTRADA, LocalDate.now()));
        assertEquals("Nome não pode ser nulo", exception.getMessage());
    }

    @Test
    public void validacaoValorTeste(){
        TransacaoInvalidaException exception = assertThrows(TransacaoInvalidaException.class,
                () -> TransacaoFactory.criarTransacao("Bingo", -500, TipoTransacao.ENTRADA, LocalDate.now()));
        assertEquals("O valor precisa ser positivo", exception.getMessage());
    }

    @Test
    public void validacaoTipoTeste(){
        TransacaoInvalidaException exception = assertThrows(TransacaoInvalidaException.class,
                () -> TransacaoFactory.criarTransacao("Viagem", 500, null, LocalDate.now()));
        assertEquals("O tipo da transação não pode ser nulo", exception.getMessage());
    }

    @Test
    public void validacaoDataTeste(){
        TransacaoInvalidaException exception = assertThrows(TransacaoInvalidaException.class,
                () -> TransacaoFactory.criarTransacao("Mecânico", 500, TipoTransacao.SAIDA, LocalDate.of(2026, 1, 10)));
        assertEquals("A data não pode ser do futuro", exception.getMessage());
    }
}