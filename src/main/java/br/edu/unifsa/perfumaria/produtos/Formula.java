package br.edu.unifsa.perfumaria.produtos;

public class Formula extends Perfume {

    private double notaTopo;
    private double notaCoracao;
    private double notaFundo;

    public Formula() {
    }

    public Formula(String name, double custoProducao, double concentracaoEssencia,
                   CategoriaPerfume categoriaPerfume, Formula formula, double notaTopo,
                   double notaCoracao, double notaFundo) {

        super(name, custoProducao, concentracaoEssencia, categoriaPerfume, formula);
        this.notaTopo = notaTopo;
        this.notaCoracao = notaCoracao;
        this.notaFundo = notaFundo;
    }


}
