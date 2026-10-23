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
import br.edu.unifsa.perfumaria.producao.Rastreavel;
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

    public static void main(String[] args) {
        Perfume parfum = new Difusor("Noite Dourada", 100.0, 0.25, CategoriaPerfume.PARFUM, null, 6);
        List<MateriaPrima> insumos = List.of(
                new MateriaPrima("Bergamota", LocalDate.of(2026, 1, 1)),
                new MateriaPrima("Sândalo", LocalDate.of(2026, 1, 1)));
        LocalDate inicio = LocalDate.of(2026, 6, 1);
        Lote lote = new Lote(parfum, insumos, inicio);
        System.out.println(lote);

        System.out.println("\n--- Caminho feliz ---");
        Frasco frasco = new Frasco(lote, 50, LocalDate.of(2026, 8, 15)); // 75 dias >= 60
        Estoque estoque = new Estoque();
        estoque.adicionar(frasco, 10);
        Venda venda = new Venda(estoque, frasco, 3, LocalDate.of(2026, 9, 1));
        System.out.println(frasco);
        System.out.println(venda);
        System.out.println(estoque);

        System.out.println("--- RN03: volume do frasco ---");
        tentar(() -> new Frasco(lote, 75, LocalDate.of(2026, 8, 15)));

        System.out.println("--- RN04: maceração mínima ---");
        tentar(() -> new Frasco(lote, 50, LocalDate.of(2026, 7, 1))); // 30 dias < 60

        System.out.println("--- RN07: estoque insuficiente ---");
        tentar(() -> new Venda(estoque, frasco, 20, LocalDate.of(2026, 9, 2))); // só restam 7

        System.out.println("--- RN08: matéria-prima vencida ---");
        MateriaPrima vencida = new MateriaPrima("Rosa", LocalDate.of(2025, 1, 1), 30);
        tentar(() -> new Lote(parfum, List.of(vencida), inicio));

        System.out.println("\n--- RN05: código do lote ---");
        tentar(() -> new Lote("  ", parfum, insumos, inicio));
        Rastreavel rastreavel = new Lote("LOTE-ABC", parfum, insumos, inicio);
        System.out.println("  Código dado na criação: " + rastreavel.getCodigo());
        System.out.println("  Código do frasco (mesmo do lote): " + ((Rastreavel) frasco).getCodigo());

        System.out.println("\n--- Perfumista: quem assina PARFUM ---");
        Perfumista junior = new Perfumista("111", "Ana", "ana@x.com", "9999", LocalDate.of(2024, 1, 10),
                new BigDecimal("3000"), NivelPerfumista.PERFUMISTA_JUNIOR);
        Perfumista senior = new Perfumista("222", "Beto", "beto@x.com", "8888", LocalDate.of(2020, 3, 5),
                new BigDecimal("6000"), NivelPerfumista.PERFUMISTA_SENIOR);
        Perfume colonia = new Difusor("Brisa", 40.0, 0.03, CategoriaPerfume.COLONIA, null, 6);
        System.out.println("  Júnior assina PARFUM? " + junior.assinar(parfum));
        System.out.println("  Sênior assina PARFUM? " + senior.assinar(parfum));
        System.out.println("  Júnior assina COLONIA? " + junior.assinar(colonia));

        System.out.println("\n--- Encomenda personalizada: gravação até 20 caracteres ---");
        System.out.println("  " + new EncomendaPersonalizada("Para a Maria").getGravacao());
        tentar(() -> new EncomendaPersonalizada("Esta gravação é longa demais"));

        System.out.println("\n--- Vendedor e Cliente ---");
        Vendedor vendedor = new Vendedor("333", "Carla", "carla@x.com", "7777", LocalDate.of(2023, 5, 2),
                new BigDecimal("2500"));
        Cliente cliente = new Cliente("444", "Davi", "davi@x.com", "6666");
        vendedor.registrarVenda(3);
        System.out.println("  " + vendedor.getNome() + " vendeu " + vendedor.getNumeroVendas()
                + " para " + cliente.getNome());

        System.out.println("\n--- Fórmula: camadas e soma 100% ---");
        List<Nota> notas = List.of(
                new Nota("n1", 1.0, 0.25, CategoriaPerfume.PARFUM, null, "Limão", CamadaNota.TOPO, 30),
                new Nota("n2", 1.0, 0.25, CategoriaPerfume.PARFUM, null, "Jasmim", CamadaNota.CORACAO, 50),
                new Nota("n3", 1.0, 0.25, CategoriaPerfume.PARFUM, null, "Âmbar", CamadaNota.FUNDO, 20));
        Formula formula = new Formula("Fórmula Noite", 100.0, 0.25, CategoriaPerfume.PARFUM, null, notas);
        System.out.println("  30 + 50 + 20 = 100%? " + formula.validarSoma());

        System.out.println("\n--- Vela aromática: fragrância até 10% do peso da cera ---");
        VelaAromatica vela = new VelaAromatica("Vela Lavanda", 30.0, 0.03, CategoriaPerfume.COLONIA, null, 200, 15);
        System.out.println("  Fragrância 15g com 200g de cera: " + vela.getFragrancia());
        vela.setFragrancia(30); // acima do limite: o setter ignora
        System.out.println("  Depois de tentar 30g, continua: " + vela.getFragrancia());

        System.out.println("\n--- Difusor: 4 a 10 varetas ---");
        Difusor difusor = new Difusor("Difusor Mar", 50.0, 0.03, CategoriaPerfume.COLONIA, null, 6);
        System.out.println("  3 varetas é válido? " + difusor.validarQtdVaretas(3));
        System.out.println("  6 varetas é válido? " + difusor.validarQtdVaretas(6));
    }

    // roda o trecho e imprime o erro esperado (ou avisa se a regra não barrou)
    private static void tentar(Runnable acao) {
        try {
            acao.run();
            System.out.println("  NÃO deu erro (a regra falhou!)");
        } catch (RuntimeException e) {
            System.out.println("  Barrou: " + e.getMessage());
        }
    }
}
