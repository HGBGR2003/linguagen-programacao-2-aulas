package br.edu.ifgoiano.aulas.henrique.gabriel;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe principal de teste demonstrando o uso avançado de Programação Funcional e Stream API no Java.
 * Apresenta aplicações práticas de:
 * <ul>
 *   <li><b>Interfaces Funcionais:</b> Criação e uso de método genérico com {@link Predicate}.</li>
 *   <li><b>Exceções de Concorrência:</b> Demonstração e tratamento de {@link ConcurrentModificationException}.</li>
 *   <li><b>Redução com Streams:</b> Cálculo do total de verba financeira necessária via {@code mapToDouble} e {@code sum}.</li>
 *   <li><b>Ordenação e Limitação:</b> Comparação personalizada ponderada por múltiplos atributos com {@code sorted} e {@code limit}.</li>
 *   <li><b>Agrupamento de Dados:</b> Classificação categórica de alunos por nível de risco usando {@link Collectors#groupingBy}.</li>
 * </ul>
 *
 * @author Henrique Gabriel Barbosa Guida Rodrigues
 * @version 1.0
 * @see Estudante
 * @see AlunoRegular
 */
public class Main {

    /**
     * Construtor padrão da classe Main.
     */
    public Main() {
    }

    /**
     * Filtra uma lista de estudantes aplicando uma condição dinâmica fornecida via interface funcional {@link Predicate}.
     *
     * @param lista     Lista original de objetos {@link Estudante} a ser filtrada.
     * @param predicado Condição lógica a ser avaliada para cada elemento da lista.
     * @return Uma nova {@link List} contendo apenas os estudantes que atenderam ao predicado.
     */
    public static List<Estudante> filtrar(List<Estudante> lista, Predicate<Estudante> predicado) {
        List<Estudante> resultado = new ArrayList<>();
        for (Estudante e : lista) {
            if (predicado.test(e)) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    /**
     * Instancia e retorna uma lista pré-populada de estudantes para realização dos testes práticos.
     *
     * @return Uma {@link List} contendo instâncias de {@link Estudante}.
     */
    public static List<Estudante> carregarTurma() {
        List<Estudante> turma = new ArrayList<>();
        turma.add(new AlunoRegular("20261EC001", "Lucas Santos", 50.0, 15.0));
        turma.add(new AlunoRegular("20261EC002", "Ana Vilela", 80.0, 120.0));
        turma.add(new AlunoRegular("20261EC003", "Bruno Carlos", 60.0, 10.0));
        turma.add(new AlunoRegular("20261EC004", "Carla Ferreira", 90.0, 200.0));
        turma.add(new AlunoRegular("20261EC004", "Dario Jubilado", 0.0, 10.0));
        turma.add(new AlunoRegular("20261EC001", "Lucas Silva", 0.0, 12.50));
        turma.add(new AlunoRegular("20261EC002", "Ana Souza", 65.0, 8.00));
        turma.add(new AlunoRegular("20261EC003", "Bruno Lima", 45.0, 22.00));
        turma.add(new AlunoRegular("20261EC004", "Carla Dias", 15.0, 85.00));
        turma.add(new AlunoRegular("20261EC005", "Diego Sato", 85.0, 180.00));
        turma.add(new AlunoRegular("20261EC006", "Fernanda Cruz", 90.0, 250.00));
        turma.add(new AlunoRegular("20261EC007", "Gabriel Mendes", 25.0, 310.00));
        turma.add(new AlunoRegular("20261EC008", "Helena Rocha", 70.0, 45.00));
        return turma;
    }

    /**
     * Ponto de entrada do programa que executa a simulação e os exercícios de processamento funcional.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        List<Estudante> turma = carregarTurma();

        // Demonstração da restrição de remoção em laço for-each
        try {
            for (Estudante e : turma) {
                if (e.getEnergia() <= 0.0) {
                    turma.remove(e); // Causa ConcurrentModificationException no próximo passo
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("[AVISO]: O laço for-each não permite modificar a lista enquanto a lê!");
        }

        System.out.println("\n==================================================");
        System.out.println("    1. TESTE DO MÉTODO CUSTOMIZADO: filtrar()     ");
        System.out.println("==================================================");
        List<Estudante> maisDeCemReais = filtrar(turma, e -> e.getDinheiro() >= 100.0);
        System.out.println("Estudantes com R$ 100,00 ou mais:");
        maisDeCemReais.forEach(e -> System.out.printf(" - %s (R$ %.2f)\n", e.getNome(), e.getDinheiro()));

        System.out.println("\n==================================================");
        System.out.println("          EXERCÍCIO 1: INJEÇÃO DE VERBA           ");
        System.out.println("==================================================");
        // Filtra quem tem menos de 30 reais e menos de 40 de energia
        double totalInjetado = turma.stream()
                .filter(e -> e.getDinheiro() < 30 && e.getEnergia() < 40)
                .mapToDouble(e -> 100 - e.getDinheiro())
                .sum();

        System.out.printf("Total a ser injetado pela universidade: R$ %.2f%n", totalInjetado);

        System.out.println("\n==================================================");
        System.out.println("      EXERCÍCIO 2: TOP 3 COM MENOR ÍNDICE         ");
        System.out.println("==================================================");
        turma.stream()
                .sorted((e1, e2) -> {
                    double indiceAluno1 = (e1.getEnergia() * 0.6) + (e1.getDinheiro() * 0.4);
                    double indiceAluno2 = (e2.getEnergia() * 0.6) + (e2.getDinheiro() * 0.4);
                    return Double.compare(indiceAluno1, indiceAluno2);
                })
                .limit(3)
                .map(e -> e.getNome().toUpperCase())
                .forEach(nome -> System.out.println(" * " + nome));

        System.out.println("\n==================================================");
        System.out.println("       EXERCÍCIO 3: AGRUPAMENTO POR RISCO         ");
        System.out.println("==================================================");
        Map<String, List<Estudante>> alunoRisco = turma.stream()
                .collect(Collectors.groupingBy(e -> {
                    if (e.getEnergia() <= 20 || e.getDinheiro() <= 10) {
                        return "Crítico";
                    } else if (e.getEnergia() <= 50) {
                        return "Alerta";
                    } else {
                        return "Estável";
                    }
                }));

        alunoRisco.forEach((categoria, lista) -> {
            System.out.println("\n--- Categoria: " + categoria + " (" + lista.size() + " alunos) ---");
            lista.forEach(e -> System.out.printf(" - %-16s | Energia: %5.1f%% | Dinheiro: R$ %6.2f%n",
                    e.getNome(), e.getEnergia(), e.getDinheiro()));
        });

        System.out.println("==================================================");
    }
}