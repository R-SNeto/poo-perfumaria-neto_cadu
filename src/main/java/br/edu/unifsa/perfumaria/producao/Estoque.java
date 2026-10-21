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

    @Override
    public String toString() {
        String texto = "Estoque:\n";
        for (Frasco frasco : itens.keySet()) {
            texto += "  " + itens.get(frasco) + " x " + frasco + "\n";
        }
        return texto;
    }
}
