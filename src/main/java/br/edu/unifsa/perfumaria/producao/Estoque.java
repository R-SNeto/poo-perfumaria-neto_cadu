package br.edu.unifsa.perfumaria.producao;

import java.util.LinkedHashMap;
import java.util.Map;

public class Estoque {

    // frasco -> quantidade
    private final Map<Frasco, Integer> itens = new LinkedHashMap<>();

    public void adicionar(Frasco frasco, int quantidade) {
        if (frasco == null || quantidade <= 0) {
            throw new IllegalArgumentException("Informe o frasco e uma quantidade positiva");
        }
        itens.put(frasco, getQuantidade(frasco) + quantidade);
    }

    // RN07: o estoque nunca fica negativo
    public void retirar(Frasco frasco, int quantidade) {
        if (frasco == null || quantidade <= 0) {
            throw new IllegalArgumentException("Informe o frasco e uma quantidade positiva");
        }
        int disponivel = getQuantidade(frasco);
        if (quantidade > disponivel) {
            throw new IllegalStateException("[RN07] Pediu " + quantidade + " de " + frasco.getPerfume().getName()
                    + ", mas só tem " + disponivel + " em estoque");
        }
        itens.put(frasco, disponivel - quantidade);
    }

    public int getQuantidade(Frasco frasco) {
        return itens.getOrDefault(frasco, 0);
    }

    // soma de todos os frascos guardados
    public int getTotalFrascos() {
        int total = 0;
        for (int quantidade : itens.values()) {
            total += quantidade;
        }
        return total;
    }

    // quanto o estoque vale pelo preço de venda
    public double getValorTotal() {
        double total = 0;
        for (Frasco frasco : itens.keySet()) {
            total += frasco.getPreco() * itens.get(frasco);
        }
        return total;
    }

    @Override
    public String toString() {
        String texto = "Estoque:\n";
        for (Frasco frasco : itens.keySet()) {
            int quantidade = itens.get(frasco);
            texto += "  " + quantidade + " x " + frasco;
            if (quantidade == 0) {
                texto += " [ESGOTADO]";
            }
            texto += "\n";
        }
        texto += "  Total: " + getTotalFrascos() + " frascos" + String.format(" - R$ %.2f", getValorTotal());
        return texto;
    }
}
