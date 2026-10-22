package br.edu.unifsa.perfumaria.pessoas;

import br.edu.unifsa.perfumaria.produtos.CategoriaPerfume;
import br.edu.unifsa.perfumaria.produtos.Perfume;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Perfumista extends Funcionario {

    private NivelPerfumista nivelPerfumista;

    public Perfumista() {
    }

    public Perfumista(String cpf, String nome, String email,
                      String telefone, LocalDate dataContrato, BigDecimal salario,
                      NivelPerfumista nivelPerfumista) {
        super(cpf, nome, email, telefone, dataContrato, salario);
        this.nivelPerfumista = nivelPerfumista;
    }

    public boolean assinar(Perfume perfume) {
        if (perfume.getCategoriaPerfume() == CategoriaPerfume.PARFUM) {
            if (nivelPerfumista == NivelPerfumista.PERFUMISTA_SENIOR) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
