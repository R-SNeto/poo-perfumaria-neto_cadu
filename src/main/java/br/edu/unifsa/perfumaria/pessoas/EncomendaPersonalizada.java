package br.edu.unifsa.perfumaria.pessoas;

import java.time.LocalDate;
import java.util.UUID;

public class EncomendaPersonalizada {

    private String numero;
    private LocalDate dataEncomenda;
    private String gravacao;

    public EncomendaPersonalizada() {
    }

    public EncomendaPersonalizada(String gravacao) {
        this.numero = UUID.randomUUID().toString();
        this.dataEncomenda = LocalDate.now();
        this.gravacao = gravacao;
    }

    public String getNumero() {
        return numero;
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
}
