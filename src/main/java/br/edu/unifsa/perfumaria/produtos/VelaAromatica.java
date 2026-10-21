package br.edu.unifsa.perfumaria.produtos;

public class VelaAromatica extends Perfume {

    private double pesoCera;
    private double fragrancia;

    public VelaAromatica() {
    }

    public VelaAromatica(String name, double custoProducao, double concentracaoEssencia,
                         CategoriaPerfume categoriaPerfume, Formula formula, double pesoCera,
                         double fragrancia) {

        super(name, custoProducao, concentracaoEssencia, categoriaPerfume, formula);
        this.pesoCera = pesoCera;
        setFragrancia(fragrancia);
    }

    public double getPesoCera() {
        return pesoCera;
    }

    public void setPesoCera(double pesoCera) {
        this.pesoCera = pesoCera;
    }

    public double getFragrancia() {
        return fragrancia;
    }

    public void setFragrancia(double fragrancia) {
        if (validarFragrancia(pesoCera, fragrancia))
            this.fragrancia = fragrancia;
    }

    public boolean validarFragrancia(double pesoCera, double fragrancia) {

        double max = pesoCera * 0.1;

        if (fragrancia >= 0 && fragrancia <= max) return true;
        else return false;
    }
}
