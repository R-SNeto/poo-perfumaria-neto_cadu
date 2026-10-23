package br.edu.unifsa.perfumaria.producao;

import java.time.LocalDate;

public class MateriaPrima implements Perecivel {

    private final String nome;
    private final LocalDate fabricacao;
    private final LocalDate validade;

    public MateriaPrima(String nome, LocalDate fabricacao, int diasDeValidade) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("A matéria-prima precisa de nome");
        }
        if (fabricacao == null) {
            throw new IllegalArgumentException("Informe a data de fabricação");
        }
        if (diasDeValidade <= 0) {
            throw new IllegalArgumentException("A validade precisa ser positiva");
        }
        this.nome = nome;
        this.fabricacao = fabricacao;
        this.validade = fabricacao.plusDays(diasDeValidade);
    }

    // validade padrão de 1 ano
    public MateriaPrima(String nome, LocalDate fabricacao) {
        this(nome, fabricacao, 365);
    }

    public String getNome() {
        return nome;
    }

    // estaVencida() vem da interface Perecivel e é usado pelo Lote na RN08
    @Override
    public LocalDate getValidade() {
        return validade;
    }

    @Override
    public String toString() {
        return nome + " (fabricação " + fabricacao + ", validade " + validade + ")";
    }
}
