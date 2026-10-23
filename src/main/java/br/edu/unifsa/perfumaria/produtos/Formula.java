package br.edu.unifsa.perfumaria.produtos;

import java.util.ArrayList;
import java.util.List;

public class Formula extends Perfume {

    private double notaTopo;
    private double notaCoracao;
    private double notaFundo;

    private List<Nota> notaList = new ArrayList<>();

    public Formula() {
    }

    public Formula(String name, double custoProducao, double concentracaoEssencia,
                   CategoriaPerfume categoriaPerfume, Formula formula, List<Nota> notaList) {

        super(name, custoProducao, concentracaoEssencia, categoriaPerfume, formula);
        this.notaList = notaList;
        validarCamada();
    }

    public void validarCamada() {
        for (int i = 0; i < 3; i++) {
            if (notaList.get(i).getCamadaNota() == CamadaNota.TOPO) {
                this.notaTopo = notaList.get(i).getPercentual();
            } else if (notaList.get(i).getCamadaNota() == CamadaNota.CORACAO) {
                this.notaCoracao = notaList.get(i).getPercentual();
            } else if (notaList.get(i).getCamadaNota() == CamadaNota.FUNDO) {
                this.notaFundo = notaList.get(i).getPercentual();
            } else {
                throw new IllegalArgumentException("Camada invalida");
            }
        }
    }

    public boolean validarSoma() {
        double nt = notaTopo / 100;
        double nc = notaCoracao / 100;
        double nf = notaFundo / 100;

        if ((nt + nc + nf) == 1) {
            return true;
        }

        return false;
    }

}
