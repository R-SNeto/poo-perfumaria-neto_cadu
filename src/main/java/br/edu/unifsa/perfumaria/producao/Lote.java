package br.edu.unifsa.perfumaria.producao;

import br.edu.unifsa.perfumaria.produtos.CategoriaPerfume;
import br.edu.unifsa.perfumaria.produtos.Perfume;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Lote implements Rastreavel {

    private static int contador = 0;

    private final String codigo;
    private final Perfume perfume;
    private final List<MateriaPrima> insumos;
    private final LocalDate inicioMaceracao;

    public Lote(String codigo, Perfume perfume, List<MateriaPrima> insumos, LocalDate inicioMaceracao) {
        // RN05: o código é dado aqui e não existe setCodigo
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("[RN05] O lote precisa receber um código na criação");
        }
        if (perfume == null || inicioMaceracao == null) {
            throw new IllegalArgumentException("Informe o perfume e a data de início da maceração");
        }
        if (insumos == null || insumos.isEmpty()) {
            throw new IllegalArgumentException("O lote precisa de pelo menos uma matéria-prima");
        }
        // RN08
        for (MateriaPrima mp : insumos) {
            if (mp.estaVencida(inicioMaceracao)) {
                throw new IllegalArgumentException("[RN08] " + mp.getNome() + " venceu em " + mp.getValidade()
                        + " e não pode ser usada");
            }
        }
        this.codigo = codigo;
        this.perfume = perfume;
        this.insumos = insumos;
        this.inicioMaceracao = inicioMaceracao;
    }

    // gera o código sozinho: LOTE-1, LOTE-2, ...
    public Lote(Perfume perfume, List<MateriaPrima> insumos, LocalDate inicioMaceracao) {
        this(gerarCodigo(), perfume, insumos, inicioMaceracao);
    }

    private static String gerarCodigo() {
        contador++;
        return "LOTE-" + contador;
    }

    public long diasDeMaceracao(LocalDate data) {
        return ChronoUnit.DAYS.between(inicioMaceracao, data);
    }

    // RN04: tempo mínimo de cada categoria
    public int getDiasMinimosMaceracao() {
        CategoriaPerfume categoria = perfume.getCategoriaPerfume();
        switch (categoria) {
            case PARFUM -> { return 60; }
            case EAUDEPARFUM -> { return 45; }
            case EAUDETOILETTE -> { return 30; }
            case COLONIA -> { return 15; }
            default -> throw new IllegalArgumentException("Categoria inválida");
        }
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    public Perfume getPerfume() {
        return perfume;
    }

    public LocalDate getInicioMaceracao() {
        return inicioMaceracao;
    }

    @Override
    public String toString() {
        return "Lote " + codigo + " - " + perfume.getName() + " - " + insumos.size()
                + " insumo(s) - maceração desde " + inicioMaceracao;
    }
}
