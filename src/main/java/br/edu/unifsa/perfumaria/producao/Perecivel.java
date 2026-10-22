package br.edu.unifsa.perfumaria.producao;

import java.time.LocalDate;

// Tudo que tem prazo de validade: MateriaPrima e Frasco
public interface Perecivel {

    LocalDate getValidade();

    // já vem pronto para quem implementar
    default boolean estaVencida(LocalDate data) {
        return data.isAfter(getValidade());
    }
}
