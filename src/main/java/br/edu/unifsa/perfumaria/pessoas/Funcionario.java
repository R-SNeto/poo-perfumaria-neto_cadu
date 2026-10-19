package br.edu.unifsa.perfumaria.pessoas;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Funcionario extends Pessoa {

    private LocalDate dataContrato;
    private BigDecimal salario;

    private NivelPerfumista nivelPerfumista;

    public Funcionario() {
    }

    public Funcionario(String cpf, String nome, String email,
                       String telefone, LocalDate dataContrato,
                       BigDecimal salario) {
        super(cpf, nome, email, telefone);
        this.dataContrato = dataContrato;
        this.salario = salario;
    }

    public LocalDate getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(LocalDate dataContrato) {
        this.dataContrato = dataContrato;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }


}
