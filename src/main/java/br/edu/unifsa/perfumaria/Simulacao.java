package br.edu.unifsa.perfumaria;

import br.edu.unifsa.perfumaria.pessoas.Cliente;
import br.edu.unifsa.perfumaria.pessoas.EncomendaPersonalizada;
import br.edu.unifsa.perfumaria.pessoas.NivelPerfumista;
import br.edu.unifsa.perfumaria.pessoas.Perfumista;
import br.edu.unifsa.perfumaria.pessoas.Vendedor;
import br.edu.unifsa.perfumaria.producao.Estoque;
import br.edu.unifsa.perfumaria.producao.Frasco;
import br.edu.unifsa.perfumaria.producao.Lote;
import br.edu.unifsa.perfumaria.producao.MateriaPrima;
import br.edu.unifsa.perfumaria.producao.Venda;
import br.edu.unifsa.perfumaria.produtos.CamadaNota;
import br.edu.unifsa.perfumaria.produtos.CategoriaPerfume;
import br.edu.unifsa.perfumaria.produtos.Difusor;
import br.edu.unifsa.perfumaria.produtos.Formula;
import br.edu.unifsa.perfumaria.produtos.Nota;
import br.edu.unifsa.perfumaria.produtos.Perfume;
import br.edu.unifsa.perfumaria.produtos.VelaAromatica;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Simulacao {

    private static final LocalDate HOJE = LocalDate.of(2026, 10, 23);

    static void main() {
        System.out.println("===== UM DIA NA ESSÊNCIA DO POTI - " + HOJE + " =====");

        simularProducao();
        simularProdutosEPessoas();
    }

    // ---------------- módulos produtos e pessoas ----------------

    private static void simularProdutosEPessoas() {
        titulo("Pessoas: cliente, vendedor e perfumistas");
        Cliente ana = new Cliente("44444444444", "Ana", "ana@exemplo.com", "86900000004");
        Cliente anaDeNovo = new Cliente("44444444444", "Ana Maria", "outro@exemplo.com", "86900000009");
        ana.setTelefone("86988887777");
        ana.setEmail("ana.nova@exemplo.com");
        System.out.println("Cliente: " + ana.getNome() + " | " + ana.getEmail() + " | " + ana.getTelefone()
                + " | CPF " + ana.getCpf());
        System.out.println("Mesmo CPF = mesma pessoa? " + ana.equals(anaDeNovo)
                + " (hash iguais: " + (ana.hashCode() == anaDeNovo.hashCode()) + ")");

        Vendedor caio = new Vendedor("33333333333", "Caio", "caio@exemplo.com", "86900000003",
                HOJE.minusMonths(8), new BigDecimal("2000.00"));
        caio.setSalario(new BigDecimal("2300.00"));
        caio.setDataContrato(HOJE.minusMonths(9));
        System.out.println("Vendedor: " + caio.getNome() + " | contrato " + caio.getDataContrato()
                + " | salário R$ " + caio.getSalario() + " | vendas: " + caio.getNumeroVendas());

        Perfumista lia = new Perfumista("11111111111", "Lia", "lia@exemplo.com", "86900000001",
                HOJE.minusYears(1), new BigDecimal("2200.00"), NivelPerfumista.PERFUMISTA_JUNIOR);
        Perfumista rosa = new Perfumista("22222222222", "Rosa", "rosa@exemplo.com", "86900000002",
                HOJE.minusYears(5), new BigDecimal("4200.00"), NivelPerfumista.PERFUMISTA_SENIOR);
        System.out.println("Perfumistas: " + lia.getNome() + " (júnior) e " + rosa.getNome() + " (sênior)");

        titulo("Categorias: faixa de concentração de essência");
        for (CategoriaPerfume c : CategoriaPerfume.values()) {
            System.out.println(c + ": de " + c.getConcentracaoMin(c) + " a " + c.getConcentracaoMax(c));
        }

        titulo("Perfumes");
        Perfume parfum = new Perfume("Poti Intenso", 100, 0.25, CategoriaPerfume.PARFUM, null) { };
        Perfume colonia = new Perfume("Poti Fresco", 40, 0.03, CategoriaPerfume.COLONIA, null) { };
        System.out.println(parfum.getName() + " | " + parfum.getCategoriaPerfume() + " | custo R$ " + parfum.getCustoProducao()
                + " | essência " + parfum.getConcentracaoEssencia() + " | preço R$ " + parfum.getPrecoVenda()
                + " | id " + parfum.getId());
        parfum.setName("Poti Intenso Reserva");
        parfum.setCustoProducao(110);
        parfum.setConcentracaoEssencia(0.28);
        parfum.setPrecoVenda(parfum.calcularPrecoVenda());
        System.out.println("Depois de editar: " + parfum.getName() + " | custo R$ " + parfum.getCustoProducao()
                + " | essência " + parfum.getConcentracaoEssencia() + " | preço R$ " + parfum.getPrecoVenda());

        titulo("Assinatura dos perfumistas");
        System.out.println(lia.getNome() + " (júnior) assina Parfum? " + lia.assinar(parfum));
        System.out.println(rosa.getNome() + " (sênior) assina Parfum? " + rosa.assinar(parfum));
        System.out.println(lia.getNome() + " (júnior) assina Colônia? " + lia.assinar(colonia));

        titulo("Fórmula (notas de topo, coração e fundo)");
        Formula formula = criarFormula(CategoriaPerfume.PARFUM, 30, 40, 30);
        formula.validarCamada();
        System.out.println("Soma das camadas fecha 100%? " + formula.validarSoma());
        parfum.setFormula(formula);
        Nota topo = new Nota("Nota topo", 1, 0.25, CategoriaPerfume.PARFUM, null, "Bergamota", CamadaNota.TOPO, 30);
        System.out.println("Nota: " + topo.getNome() + " | " + topo.getCamadaNota() + " | " + topo.getPercentual() + "%");
        System.out.println("Parfum agora tem fórmula: " + parfum.getFormula().getName());

        titulo("Vela aromática");
        VelaAromatica vela = new VelaAromatica("Vela Lavanda", 30, 0.03, CategoriaPerfume.COLONIA, null, 200, 15);
        System.out.println(vela.getName() + " | cera " + vela.getPesoCera() + " g | fragrância " + vela.getFragrancia()
                + " g | preço R$ " + vela.getPrecoVenda());
        vela.setPesoCera(300);
        vela.setFragrancia(25);
        System.out.println("Depois de editar: cera " + vela.getPesoCera() + " g | fragrância " + vela.getFragrancia() + " g");
        System.out.println("20 g em 300 g de cera é válido? " + vela.validarFragrancia(300, 20));

        titulo("Difusor");
        Difusor difusor = new Difusor("Difusor Mar", 50, 0.03, CategoriaPerfume.COLONIA, null, 6);
        difusor.setQtdVaretas(8);
        System.out.println(difusor.getName() + " | preço R$ " + difusor.getPrecoVenda() + " | 8 varetas definidas");
        System.out.println("4 varetas é válido? " + difusor.validarQtdVaretas(4) + " | 11 é válido? "
                + difusor.validarQtdVaretas(11));

        titulo("Encomenda personalizada");
        EncomendaPersonalizada encomenda = new EncomendaPersonalizada("Para a Ana");
        System.out.println("Encomenda " + encomenda.getId() + " em " + encomenda.getDataEncomenda()
                + " | gravação: " + encomenda.getGravacao());
        encomenda.setGravacao("Ana, com carinho");
        System.out.println("Gravação alterada: " + encomenda.getGravacao());

        titulo("Fluxo completo: produção, venda e encomenda da cliente");
        List<MateriaPrima> insumos = List.of(new MateriaPrima("Álcool de cereais", HOJE.minusDays(30)),
                new MateriaPrima("Óleo de bergamota", HOJE.minusDays(30)));
        Lote lote = new Lote(parfum, insumos, HOJE.minusDays(70));
        Frasco frasco = new Frasco(lote, 50, HOJE);
        Estoque estoque = new Estoque();
        estoque.adicionar(frasco, 5);
        Venda venda = new Venda(estoque, frasco, 1, HOJE);
        caio.registrarVenda(1);
        System.out.println(rosa.getNome() + " assinou o lote " + lote.getCodigo() + ": " + rosa.assinar(parfum));
        System.out.println(caio.getNome() + " vendeu para " + ana.getNome() + " (gravação '" + encomenda.getGravacao()
                + "'): " + venda);
        System.out.println(caio.getNome() + " já fez " + caio.getNumeroVendas() + " venda(s)");
        System.out.print(estoque);

        titulo("Testes das regras dos módulos produtos e pessoas");
        testar("RN01", "Criar EDP com 40% de essência",
                () -> new Perfume("Inválido", 80, 0.40, CategoriaPerfume.EAUDEPARFUM, null) { });
        testar("RN02", "Criar fórmula com notas somando 90%",
                () -> criarFormula(CategoriaPerfume.EAUDEPARFUM, 40, 30, 20));
        testar("RN02", "Criar fórmula com camada de topo em 0%",
                () -> criarFormula(CategoriaPerfume.EAUDEPARFUM, 0, 50, 50));
        testar("RN06", "Fixar preço de R$ 100 para custo R$ 110 (mínimo R$ 154)",
                () -> parfum.setPrecoVenda(100));
        testar("RN09", "Perfumista júnior assinar Parfum",
                () -> {
                    if (lia.assinar(parfum)) return;
                    throw new IllegalStateException("[RN09] só perfumista sênior assina Parfum");
                });
        testar("RN10", "Vela com 15 g de fragrância para 100 g de cera",
                () -> new VelaAromatica("Vela", 20, 0.10, CategoriaPerfume.EAUDETOILETTE, null, 100, 15));
        testar("RN10", "Difusor com 3 varetas",
                () -> new Difusor("Difusor", 20, 0.10, CategoriaPerfume.EAUDETOILETTE, null, 3));
        testar("RN11", "Gravação de 21 caracteres na encomenda",
                () -> new EncomendaPersonalizada("123456789012345678901"));
        testar("RN11", "Gravação de 21 caracteres pelo setter",
                () -> encomenda.setGravacao("123456789012345678901"));
    }

    private static Formula criarFormula(CategoriaPerfume categoria, double topo, double coracao, double fundo) {
        double concentracao = switch (categoria) {
            case PARFUM -> 0.25;
            case EAUDEPARFUM -> 0.18;
            case EAUDETOILETTE -> 0.10;
            case COLONIA -> 0.03;
        };
        List<Nota> notas = List.of(
                new Nota("Nota topo", 1, concentracao, categoria, null, "Bergamota", CamadaNota.TOPO, topo),
                new Nota("Nota coração", 1, concentracao, categoria, null, "Rosa", CamadaNota.CORACAO, coracao),
                new Nota("Nota fundo", 1, concentracao, categoria, null, "Âmbar", CamadaNota.FUNDO, fundo));
        return new Formula("Fórmula " + categoria, 1, concentracao, categoria, null, notas);
    }

    // ---------------- módulo producao ----------------

    private static void simularProducao() {
        titulo("Matérias-primas");
        MateriaPrima bergamota = new MateriaPrima("Óleo de bergamota", HOJE.minusDays(120));
        MateriaPrima rosa = new MateriaPrima("Absoluto de rosa", HOJE.minusDays(100));
        MateriaPrima baunilha = new MateriaPrima("Extrato de baunilha", HOJE.minusDays(90), 720);
        MateriaPrima alcool = new MateriaPrima("Álcool de cereais", HOJE.minusDays(60));
        MateriaPrima jasmimVencido = new MateriaPrima("Óleo de jasmim (lote antigo)", LocalDate.of(2025, 1, 10), 180);
        List<MateriaPrima> insumos = List.of(bergamota, rosa, baunilha, alcool);
        System.out.println(bergamota);
        System.out.println(rosa);
        System.out.println(baunilha);
        System.out.println(alcool);
        System.out.println(jasmimVencido + " -> vencida? " + jasmimVencido.estaVencida(HOJE));

        titulo("Perfumes (um de cada categoria)");
        Perfume noite = criarPerfume("Noite no Poti", CategoriaPerfume.PARFUM, 189.90);
        Perfume encontro = criarPerfume("Encontro das Águas", CategoriaPerfume.EAUDEPARFUM, 159.90);
        Perfume brisa = criarPerfume("Brisa do Parnaíba", CategoriaPerfume.EAUDETOILETTE, 99.90);
        Perfume cajuina = criarPerfume("Cajuína", CategoriaPerfume.COLONIA, 59.90);
        for (Perfume p : List.of(noite, encontro, brisa, cajuina)) {
            System.out.println(p.getName() + " - " + p.getCategoriaPerfume());
        }

        titulo("Produção de lotes");
        Lote loteNoite = new Lote(noite, insumos, HOJE.minusDays(65));
        Lote loteEncontro = new Lote(encontro, insumos, HOJE.minusDays(50));
        Lote loteBrisa = new Lote(brisa, insumos, HOJE.minusDays(35));
        Lote loteCajuina = new Lote(cajuina, insumos, HOJE.minusDays(20));
        Lote loteEncontroNovo = new Lote(encontro, insumos, HOJE.minusDays(20));
        for (Lote lote : List.of(loteNoite, loteEncontro, loteBrisa, loteCajuina, loteEncontroNovo)) {
            System.out.println(lote);
        }

        titulo("Envase");
        Estoque estoque = new Estoque();
        Frasco frNoite = new Frasco(loteNoite, 50, HOJE);
        Frasco frEncontro = new Frasco(loteEncontro, 100, HOJE);
        Frasco frBrisa = new Frasco(loteBrisa, 100, HOJE);
        Frasco frCajuina = new Frasco(loteCajuina, 30, HOJE);
        estoque.adicionar(frNoite, 10);
        estoque.adicionar(frEncontro, 8);
        estoque.adicionar(frBrisa, 12);
        estoque.adicionar(frCajuina, 20);
        System.out.println(estoque);

        titulo("Vendas");
        Venda venda1 = new Venda(estoque, frNoite, 2, HOJE);
        Venda venda2 = new Venda(estoque, frCajuina, 5, HOJE);
        Venda venda3 = new Venda(estoque, frBrisa, 1, HOJE.minusDays(3));
        System.out.println(venda1);
        System.out.println(venda2);
        System.out.println(venda3);
        venda3.devolver(HOJE);
        System.out.println("Devolução dentro do prazo aceita: " + venda3);

        titulo("Testes das regras do módulo producao");
        testar("RN03", "Envasar 'Brisa do Parnaíba' em frasco de 75ml",
                () -> new Frasco(loteBrisa, 75, HOJE));
        testar("RN04", "Envasar EDP com 20 dias de maceração",
                () -> new Frasco(loteEncontroNovo, 50, HOJE));
        testar("RN05", "Criar lote sem código",
                () -> new Lote("", noite, insumos, HOJE));
        System.out.println("       (Lote não tem setCodigo: o código " + loteNoite.getCodigo() + " nunca muda)");
        testar("RN07", "Vender 50 frascos de 'Noite no Poti' (há " + estoque.getQuantidade(frNoite) + ")",
                () -> new Venda(estoque, frNoite, 50, HOJE));
        testar("RN08", "Produzir lote com óleo de jasmim vencido",
                () -> new Lote(noite, List.of(jasmimVencido, alcool), HOJE));
        testar("Extra", "Devolver venda pela segunda vez",
                () -> venda3.devolver(HOJE));
        testar("Extra", "Devolver venda depois de 7 dias",
                () -> venda1.devolver(HOJE.plusDays(10)));
        testar("Extra", "Vender frasco vencido (daqui a 4 anos)",
                () -> new Venda(estoque, frEncontro, 1, HOJE.plusYears(4)));

        titulo("Estoque final");
        System.out.println(estoque);
    }

    // Perfume é abstrato: aqui criamos um perfume simples só para alimentar a produção
    private static Perfume criarPerfume(String nome, CategoriaPerfume categoria, double precoVenda) {
        Perfume perfume = new Perfume() { };
        perfume.setName(nome);
        perfume.setCategoriaPerfume(categoria);
        perfume.setPrecoVenda(precoVenda);
        return perfume;
    }

    // ---------------- auxiliares ----------------

    // roda a tentativa; se o sistema barrar, mostra RECUSADO e o motivo
    private static void testar(String regra, String descricao, Runnable tentativa) {
        try {
            tentativa.run();
            System.out.println("[" + regra + "] " + descricao + ": ACEITO (a regra falhou!)");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("[" + regra + "] " + descricao + ": RECUSADO");
            System.out.println("       Motivo: " + e.getMessage());
        }
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("--- " + texto + " ---");
    }
}