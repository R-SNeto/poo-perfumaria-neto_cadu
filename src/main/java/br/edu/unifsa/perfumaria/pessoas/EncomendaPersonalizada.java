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
        this.gravacao = gravacao;
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

    private String gerarId() {
        return UUID.randomUUID().toString();
    }
}
