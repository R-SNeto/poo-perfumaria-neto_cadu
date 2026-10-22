package br.edu.unifsa.perfumaria.produtos;

public class Nota extends Perfume {

    private String nome;
    private CamadaNota camadaNota;
    private double percentual;

    public Nota() {
    }

    public Nota(String name, double custoProducao, double concentracaoEssencia,
                CategoriaPerfume categoriaPerfume, Formula formula, String nome,
                CamadaNota camadaNota, double percentual) {

        super(name, custoProducao, concentracaoEssencia, categoriaPerfume, formula);
        this.nome = nome;
        this.camadaNota = camadaNota;
        this.percentual = percentual;
    }

    public String getNome() {
        return nome;
    }

    public CamadaNota getCamadaNota() {
        return camadaNota;
    }

    public double getPercentual() {
        return percentual;
    }
}
