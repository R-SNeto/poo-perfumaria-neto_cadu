package br.edu.unifsa.perfumaria.producao;

import br.edu.unifsa.perfumaria.produtos.Perfume;

import java.time.LocalDate;

// perfume de um lote já envasado
public class Frasco implements Rastreavel {

    private final Lote lote;
    private final int volumeMl;
    private final LocalDate dataEnvase;

    public Frasco(Lote lote, int volumeMl, LocalDate dataEnvase) {
        if (lote == null || dataEnvase == null) {
            throw new IllegalArgumentException("Informe o lote e a data do envase");
        }
        validarVolume(volumeMl);
        // RN04
        long dias = lote.diasDeMaceracao(dataEnvase);
        int minimo = lote.getDiasMinimosMaceracao();
        if (dias < minimo) {
            throw new IllegalStateException("[RN04] " + lote.getPerfume().getCategoriaPerfume() + " com "
                    + dias + " dias de maceração, o mínimo é " + minimo);
        }
        this.lote = lote;
        this.volumeMl = volumeMl;
        this.dataEnvase = dataEnvase;
    }

    // RN03: só existem frascos de 30, 50 e 100 ml
    private void validarVolume(int volume) {
        if (volume != 30 && volume != 50 && volume != 100) {
            throw new IllegalArgumentException("[RN03] Não existe frasco de " + volume + "ml (só 30, 50 ou 100)");
        }
    }

    @Override
    public String getCodigo() {
        return lote.getCodigo();
    }

    public Perfume getPerfume() {
        return lote.getPerfume();
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    public double getPreco() {
        return lote.getPerfume().getPrecoVenda();
    }

    @Override
    public String toString() {
        return lote.getPerfume().getName() + " " + volumeMl + "ml (lote " + getCodigo()
                + ", envasado em " + dataEnvase + ")" + String.format(" - R$ %.2f", getPreco());
    }
}
