package br.edu.unifsa.perfumaria.producao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Venda {

    private static final int PRAZO_DEVOLUCAO_DIAS = 7;

    private final Estoque estoque;
    private final Frasco frasco;
    private final int quantidade;
    private final double valorTotal;
    private final LocalDate data;
    private boolean devolvida;

    public Venda(Estoque estoque, Frasco frasco, int quantidade, LocalDate data) {
        if (estoque == null || frasco == null || data == null) {
            throw new IllegalArgumentException("Informe o estoque, o frasco e a data da venda");
        }
        if (frasco.estaVencida(data)) {
            throw new IllegalStateException("Frasco vencido em " + frasco.getValidade() + " não pode ser vendido");
        }
        estoque.retirar(frasco, quantidade); // se não tiver estoque, a RN07 barra aqui
        this.estoque = estoque;
        this.frasco = frasco;
        this.quantidade = quantidade;
        this.valorTotal = frasco.getPreco() * quantidade;
        this.data = data;
    }

    // venda com a data de hoje
    public Venda(Estoque estoque, Frasco frasco, int quantidade) {
        this(estoque, frasco, quantidade, LocalDate.now());
    }

    public boolean podeSerDevolvida(LocalDate dataPedido) {
        long dias = ChronoUnit.DAYS.between(data, dataPedido);
        return !devolvida && dias <= PRAZO_DEVOLUCAO_DIAS;
    }

    // devolve os frascos para o estoque
    public void devolver(LocalDate dataPedido) {
        if (!podeSerDevolvida(dataPedido)) {
            throw new IllegalStateException("Venda já devolvida ou fora do prazo de " + PRAZO_DEVOLUCAO_DIAS + " dias");
        }
        estoque.adicionar(frasco, quantidade);
        devolvida = true;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public boolean isDevolvida() {
        return devolvida;
    }

    @Override
    public String toString() {
        String texto = data + ": " + quantidade + " x " + frasco.getPerfume().getName() + " " + frasco.getVolumeMl()
                + "ml" + String.format(" = R$ %.2f", valorTotal);
        if (devolvida) {
            texto += " (devolvida)";
        }
        return texto;
    }
}
