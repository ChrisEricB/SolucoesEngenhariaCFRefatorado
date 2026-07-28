package service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculadoraProgressoProjetoTest {

    @Test
    public void deveCalcularCinquentaPorCento() {
        CalculadoraProgressoProjeto calculadora =
                new CalculadoraProgressoProjeto();

        double resultado =
                calculadora.calcularPercentualConclusao(5, 10);

        assertEquals(50.0, resultado, 0.0001);
    }

    @Test
    public void deveCalcularCemPorCento() {
        CalculadoraProgressoProjeto calculadora =
                new CalculadoraProgressoProjeto();

        double resultado =
                calculadora.calcularPercentualConclusao(8, 8);

        assertEquals(100.0, resultado, 0.0001);
    }

    @Test
    public void deveCalcularZeroPorCento() {
        CalculadoraProgressoProjeto calculadora =
                new CalculadoraProgressoProjeto();

        double resultado =
                calculadora.calcularPercentualConclusao(0, 10);

        assertEquals(0.0, resultado, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveRejeitarTotalDeEtapasIgualAZero() {
        CalculadoraProgressoProjeto calculadora =
                new CalculadoraProgressoProjeto();

        calculadora.calcularPercentualConclusao(0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveRejeitarEtapasConcluidasMaiorQueTotal() {
        CalculadoraProgressoProjeto calculadora =
                new CalculadoraProgressoProjeto();

        calculadora.calcularPercentualConclusao(11, 10);
    }
}