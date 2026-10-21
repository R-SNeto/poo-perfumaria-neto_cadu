package br.edu.unifsa.perfumaria.produtos;

public class Difusor extends Perfume {

    private int qtdVaretas;

    public Difusor() {
    }

    public Difusor(String name, double custoProducao, double concentracaoEssencia,
                   CategoriaPerfume categoriaPerfume, Formula formula, int qtdVaretas) {

        super(name, custoProducao, concentracaoEssencia, categoriaPerfume, formula);
        this.qtdVaretas = qtdVaretas;
    }

    public void setQtdVaretas(int qtdVaretas) {
        if (validarQtdVaretas(qtdVaretas)) {
            this.qtdVaretas = qtdVaretas;
        }
    }

    public boolean validarQtdVaretas(int qtd) {
        if (qtd >= 4 && qtd <= 10) return true;
        else return false;
    }
}
