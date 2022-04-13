package com.santosystem.domain.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Regra de Debito de Conta")
class DebitoContaTest {
    // armazena o saldo para teste ficar dinamico
    BigDecimal cem = new BigDecimal(1000);
    Conta contaValida;

    @BeforeEach
    void prepara() {
        contaValida = new Conta(1, cem, "Fernando");
    }

    // negativos

    @Test
    @DisplayName("valor debito nulo como obrigatório")
    void teste1() {
        try {
            contaValida.debitar(null);
            fail("valor débito é obrigatório");
        } catch (NegocioException e) {
            assertEquals("Valor débito é obrigatório.", e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("valor debito negatico como obrigatório")
    void teste2() {
        try {
            contaValida.debitar(new BigDecimal(-10));
            fail("valor débito obrigatório");
        } catch (NegocioException e) {
            assertEquals("Valor débito é obrigatório.", e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("valor debito zero como obrigatório")
    void teste3() {
        try {
            contaValida.debitar(BigDecimal.ZERO);
            fail("valor debito obrigatório");
        } catch (NegocioException e) {
            assertEquals("Valor débito é obrigatório.", e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("valor debito acima do saldo")
    void teste4() {
        try {
            contaValida.debitar(cem.add(BigDecimal.ONE));
            fail("valor débito acima do saldo");
        } catch (NegocioException e) {
            assertEquals("Saldo insuficiente.", e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    // positivos

    @Test
    @DisplayName("valor debito igual ao saldo")
    void teste5() {
        try {
            contaValida.debitar(cem);
            assertEquals(BigDecimal.ZERO, contaValida.getSaldo(), "Saldo deve zerar");
        } catch (NegocioException e) {
            fail("Deve debitar com sucesso - " + e.getMessage());
        }
    }

    @Test
    @DisplayName("valor debito menor que saldo")
    void teste6() {
        try {
            contaValida.debitar(BigDecimal.TEN);
            var saldoFinal = cem.subtract(BigDecimal.TEN);
            assertEquals(contaValida.getSaldo(), saldoFinal, "Saldo deve bater");
        } catch (NegocioException e) {
            fail("Deve debitar com sucesso - " + e.getMessage());
        }
    }
}