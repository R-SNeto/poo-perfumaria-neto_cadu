# Relatório da classe `Simulacao`

Data da análise: 23/10/2026. Escopo entregue: `Simulacao.java`, este relatório e uma correção mínima em `produtos/Perfume.java` autorizada pelo responsável pelo trabalho. O ZIP fornecido é uma exportação sem diretório `.git`; nenhum commit, branch remoto, push ou alteração no GitHub foi feito.

## O que analisei

- Enunciado: a simulação deve cadastrar funcionários, matérias-primas e uma fórmula de cada categoria; produzir lotes, envasar, vender, atender encomenda personalizada, tentar violar RN01 a RN11 e exibir o estoque final.
- API existente nos pacotes `produtos`, `producao` e `pessoas` e os construtores disponíveis para montar o roteiro.
- Compilação de todos os arquivos Java com JDK 25. A versão original compila, mas não conclui o cadastro de produtos em tempo de execução.

## O que adicionei

- Roteiro com data fixa para reproduzir os resultados de validade e maceração.
- Cadastro de perfumistas júnior e sênior, vendedor, cliente, matérias-primas e tentativa de criar fórmulas das quatro categorias.
- Produção de lote, envase, entrada no estoque, venda comum e atendimento de pedido personalizado quando os objetos necessários existem.
- Tentativas identificadas no console para RN01 a RN11, inclusive soma incorreta e camada zerada da RN02, as duas partes da RN10 e os caminhos de construtor e setter da RN11.
- Resultado `RECUSADO`, `FALHOU` ou `NÃO EXECUTADA` conforme o que realmente aconteceu. Uma falha de infraestrutura ou um pré-requisito ausente não é contabilizado como regra aprovada.
- Impressão do estoque e contadores finais. A classe interna `PerfumeExemplo` serve apenas à demonstração porque `Perfume` é abstrata e o pacote `produtos` não tem um perfume concreto para venda.
- No construtor de `Perfume`, atribuição de `categoriaPerfume` antes de validar `concentracaoEssencia`. A alteração troca apenas a ordem dessas duas instruções para liberar o cadastro.

## Testes e resultados

O código original do ZIP compila, mas o construtor de `Perfume` chamava `setConcentracaoEssencia` antes de atribuir `categoriaPerfume`, causando `NullPointerException`. Antes da correção, isso impedia as quatro fórmulas, os lotes e as vendas; o estoque terminava vazio.

Após a correção mínima autorizada e incluída nesta entrega, a simulação compila e executa até o fim. Exibe as quatro fórmulas, quatro lotes, envases, duas vendas e o estoque final. Os resultados das regras foram:

| Regra | Resultado após a correção mínima | Motivo observado |
| --- | --- | --- |
| RN01 | Recusada | `Perfume` lança erro para concentração fora da faixa da categoria. |
| RN02 | Recusada | `Formula` rejeita soma diferente de 100% e camada com 0%. |
| RN03 | Recusada | `Frasco` rejeita 75 ml. |
| RN04 | Recusada | `Frasco` rejeita EDP com 20 dias de maceração. |
| RN05 | Recusada | `Lote` rejeita código vazio. |
| RN06 | Recusada | `setPrecoVenda` rejeita valor abaixo de custo + 40%. |
| RN07 | Recusada | `Venda` rejeita quantidade superior ao estoque, que permanece igual. |
| RN08 | Recusada | `Lote` rejeita matéria-prima vencida. |
| RN09 | Recusada | Perfumista júnior não assina Parfum; sênior assina. |
| RN10 | Recusada | Vela rejeita fragrância acima de 10% da cera; difusor rejeita 3 varetas. |
| RN11 | Parcial | Construtor e setter rejeitam gravação com 21 caracteres. Não há operação de devolução para testar. |

Após as validações adicionadas nas classes de domínio (ver abaixo), a saída totalizou 14 tentativas recusadas, 0 falhas e 1 não executada (devolução da RN11). Esses números contam tentativas; RN02, RN10 e RN11 têm mais de uma tentativa. Antes dessas validações, eram 7 recusadas, 7 falhas e 1 não executada.

## Alterações feitas nas classes de domínio

1. `produtos/Perfume.java`: ordem de inicialização corrigida; concentração fora da faixa lança erro (RN01); `setPrecoVenda` recusa preço abaixo de custo + 40% (RN06).
2. `produtos/Formula.java`: construtor rejeita soma diferente de 100% e camada com 0% (RN02); `validarSoma` compara com tolerância em vez de `==`.
3. `produtos/VelaAromatica.java` e `produtos/Difusor.java`: validam no construtor e no setter e lançam erro (RN10).
4. `pessoas/EncomendaPersonalizada.java`: o setter valida a gravação (RN11).

## Pendências

- Definir como a devolução será recusada (RN11): não há operação de devolução na API. A encomenda também não referencia cliente, frasco nem venda; o roteiro combina esses objetos sem vínculo de domínio.
- Se surgir uma classe concreta para o perfume vendido, substituir `PerfumeExemplo` em `Simulacao` por ela.

## Como executar

Na raiz do projeto, com JDK 25 e PowerShell:

```powershell
$fontes = rg --files src/main/java -g '*.java'
New-Item -ItemType Directory -Force target/classes | Out-Null
javac -encoding UTF-8 -d target/classes $fontes
java -cp target/classes br.edu.unifsa.perfumaria.Simulacao
```

O `README.md` coletivo, o diagrama UML e as outras classes dos pacotes dos demais integrantes não foram alterados.
