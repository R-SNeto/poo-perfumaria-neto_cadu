package br.edu.unifsa.perfumaria.pessoas;

import java.time.LocalDate;
import java.util.UUID;

public class EncomendaPersonalizada {

    private String id;
    private LocalDate dataEncomenda;
    private String gravacao;

    public EncomendaPersonalizada() {
    }

    public EncomendaPersonalizada(String gravacao) {
        this.id = gerarId();
        this.dataEncomenda = LocalDate.now();
        validarGravacao(gravacao);
    }

    public String getId() {
        return id;
    }

    public LocalDate getDataEncomenda() {
        return dataEncomenda;
    }

    public String getGravacao() {
        return gravacao;
    }

    public void setGravacao(String gravacao) {
        this.gravacao = gravacao;
    }

    private void validarGravacao(String gravacao) {
        if (gravacao.length() > 20) throw new IllegalArgumentException("O tamanho deve ser de, no máximo, 20 caracteres");
        else this.gravacao = gravacao;
    }

    private String gerarId() {
        return UUID.randomUUID().toString();
    }
}
