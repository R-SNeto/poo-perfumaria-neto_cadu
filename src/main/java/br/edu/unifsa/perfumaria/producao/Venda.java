package br.edu.unifsa.perfumaria.producao;

import java.time.LocalDate;

public class Venda {

    private final Frasco frasco;
    private final int quantidade;
    private final double valorTotal;
    private final LocalDate data;

    public Venda(Estoque estoque, Frasco frasco, int quantidade, LocalDate data) {
        if (estoque == null || data == null) {
            throw new IllegalArgumentException("Informe o estoque e a data da venda");
        }
        estoque.retirar(frasco, quantidade); // se não tiver estoque, a RN07 barra aqui
        this.frasco = frasco;
        this.quantidade = quantidade;
        this.valorTotal = frasco.getPreco() * quantidade;
        this.data = data;
    }

    // venda com a data de hoje
    public Venda(Estoque estoque, Frasco frasco, int quantidade) {
        this(estoque, frasco, quantidade, LocalDate.now());
    }

    public double getValorTotal() {
        return valorTotal;
    }

    @Override
    public String toString() {
        return data + ": " + quantidade + " x " + frasco.getPerfume().getName() + " " + frasco.getVolumeMl()
                + "ml" + String.format(" = R$ %.2f", valorTotal);
    }
}
