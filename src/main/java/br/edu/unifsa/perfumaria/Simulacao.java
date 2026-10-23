package br.edu.unifsa.perfumaria;

import br.edu.unifsa.perfumaria.producao.Estoque;
import br.edu.unifsa.perfumaria.producao.Frasco;
import br.edu.unifsa.perfumaria.producao.Lote;
import br.edu.unifsa.perfumaria.producao.MateriaPrima;
import br.edu.unifsa.perfumaria.producao.Venda;
import br.edu.unifsa.perfumaria.produtos.CategoriaPerfume;
import br.edu.unifsa.perfumaria.produtos.Perfume;

import java.time.LocalDate;
import java.util.List;

public class Simulacao {

    private static final LocalDate HOJE = LocalDate.of(2026, 10, 23);

    public static void main(String[] args) {
        System.out.println("===== UM DIA NA ESSÊNCIA DO POTI - " + HOJE + " =====");

        simularProducao();

        // testes dos módulos produtos e pessoas entram aqui
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
