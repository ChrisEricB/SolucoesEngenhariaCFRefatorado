package service;

public class CalculadoraProgressoProjeto {

    public double calcularPercentualConclusao(
            int etapasConcluidas,
            int totalEtapas) {

        if (totalEtapas <= 0) {
            throw new IllegalArgumentException(
                    "O total de etapas deve ser maior que zero."
            );
        }

        if (etapasConcluidas < 0) {
            throw new IllegalArgumentException(
                    "A quantidade de etapas concluídas não pode ser negativa."
            );
        }

        if (etapasConcluidas > totalEtapas) {
            throw new IllegalArgumentException(
                    "As etapas concluídas não podem superar o total de etapas."
            );
        }

        return etapasConcluidas * 100.0 / totalEtapas;
    }
}