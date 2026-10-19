package br.edu.unifsa.perfumaria.pessoas;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Vendedor extends Funcionario {

    private int numeroVendas;

    public Vendedor() {
    }

    public Vendedor(String cpf, String nome, String email, String telefone, LocalDate dataContrato, BigDecimal salario) {
        super(cpf, nome, email, telefone, dataContrato, salario);
        this.numeroVendas = 0;
    }

    public int getNumeroVendas() {
        return numeroVendas;
    }

    public void registrarVenda(int numeroVendas) {
        this.numeroVendas += numeroVendas;
    }
}
