package br.edu.unifsa.perfumaria.produtos;

import java.util.UUID;

public abstract class Perfume {

    private String id;
    private String name;
    private double custoProducao;
    private double precoVenda;
    private double concentracaoEssencia;

    private CategoriaPerfume categoriaPerfume;
    private Formula formula;

    public Perfume() {
    }

    public Perfume(String name, double custoProducao, double concentracaoEssencia,
                   CategoriaPerfume categoriaPerfume, Formula formula) {
        this.id = gerarId();
        this.name = name;
        this.custoProducao = custoProducao;
        this.categoriaPerfume = categoriaPerfume;
        setConcentracaoEssencia(concentracaoEssencia);
        this.formula = formula;

        this.precoVenda = calcularPrecoVenda();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCustoProducao() {
        return custoProducao;
    }

    public void setCustoProducao(double custoProducao) {
        this.custoProducao = custoProducao;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public double getConcentracaoEssencia() {
        return concentracaoEssencia;
    }

    public void setConcentracaoEssencia(double concentracaoEssencia) {
        if (validarConcentracao(categoriaPerfume, concentracaoEssencia)) {
            this.concentracaoEssencia = concentracaoEssencia;
        }
    }

    public CategoriaPerfume getCategoriaPerfume() {
        return categoriaPerfume;
    }

    public void setCategoriaPerfume(CategoriaPerfume categoriaPerfume) {
        this.categoriaPerfume = categoriaPerfume;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public double calcularPrecoVenda() {
        return custoProducao * 1.4;
    }

    private boolean validarConcentracao(CategoriaPerfume categoriaPerfume, double essencia) {
        if (essencia >= categoriaPerfume.getConcentracaoMin(categoriaPerfume)
                && essencia <= categoriaPerfume.getConcentracaoMax(categoriaPerfume)) {
            return true;
        }

        return false;
    }

    private String gerarId() {
        return UUID.randomUUID().toString();
    }
}
