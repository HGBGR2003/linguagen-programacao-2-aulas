package br.edu.ifgoiano.aulas.henrique.gabriel;

import java.util.*;
import java.util.function.Consumer;

/**
 * Classe principal de demonstração prática sobre o Java Collections Framework e Stream API.
 * Apresenta operações fundamentais de manipulação de coleções com objetos do tipo {@link Aluno}, tais como:
 * <ul>
 *   <li><b>Iteração de Listas:</b> Laços {@code for} indexados, {@code for-each} aprimorado, Lambdas e Method References.</li>
 *   <li><b>Cálculos Estatísticos:</b> Somatório e cálculo de médias.</li>
 *   <li><b>Remoção Segura:</b> Uso de {@link Iterator} para evitar {@link ConcurrentModificationException}.</li>
 *   <li><b>Java Streams:</b> Filtragem ({@code filter}), mapeamento ({@code map}), ordenação customizada ({@code sorted}) e redução ({@code sum}).</li>
 * </ul>
 *
 * @author Henrique Gabriel Barbosa Guida Rodrigues
 * @version 1.0
 * @see Aluno
 * @see AlunoRegular
 * @see AlunoEstagiario
 */
public class Main {

    /**
     * Construtor padrão da classe Main.
     */
    public Main() {
    }

    /**
     * Instancia e carrega uma lista inicial simulando uma turma de alunos regulares para os testes.
     *
     * @return Uma {@link List} contendo instâncias pré-cadastradas de {@link Aluno}.
     */
    public static List<Aluno> carregarTurma() {
        List<Aluno> turma = new ArrayList<>();
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
     * Ponto de entrada do programa que executa as etapas de listagem, processamento,
     * filtragem e resolução dos exercícios práticos com Streams.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        List<Aluno> turma = carregarTurma();

        System.out.println("==================================================");
        System.out.println("          1. LISTAGEM INICIAL DE ALUNOS           ");
        System.out.println("==================================================");
        System.out.println("[Iteração com 'for' tradicional]:");
        for (int i = 0; i < turma.size(); i++) {
            System.out.println(" -> " + turma.get(i));
        }

        System.out.println("\n[Iteração com 'for-each' tradicional]:");
        for (Aluno aluno : turma) {
            System.out.println(" -> " + aluno);
        }

        System.out.println("\n==================================================");
        System.out.println("             2. CÁLCULO DE NOTAS                  ");
        System.out.println("==================================================");
        List<Double> notas = Arrays.asList(7.3, 5.8, 9.2, 6.4, 8.0, 10.0);
        double soma = 0;
        for (Double n : notas) {
            soma += n;
        }
        double media = soma / notas.size();

        System.out.println("Notas avaliadas: " + notas);
        System.out.println("Somatório das notas : " + Math.floor(soma));
        System.out.println("Média das notas     : " + Math.floor(media));

        System.out.println("\n==================================================");
        System.out.println("         3. REMOÇÃO DE ALUNOS SEM ENERGIA         ");
        System.out.println("==================================================");
        System.out.println("Total antes da remoção: " + turma.size());

        // Remoção segura utilizando Iterator
        Iterator<Aluno> al = turma.iterator();
        while (al.hasNext()) {
            Aluno aluno = al.next();
            if (aluno.getEnergia() <= 0.0) {
                al.remove();
            }
        }
        System.out.println("Total após Iterator (limpeza final): " + turma.size());

        System.out.println("\n==================================================");
        System.out.println("     4. DEMONSTRAÇÃO DE FOR-EACH / LAMBDAS        ");
        System.out.println("==================================================");
        System.out.println("[Com classe anônima (Consumer)]:");
        turma.forEach(new Consumer<Aluno>() {
            @Override
            public void accept(Aluno aluno) {
                System.out.println(" - " + aluno.getNome());
            }
        });

        System.out.println("\n[Com expressão Lambda (apenas nome)]:");
        turma.forEach(e -> System.out.println(" - " + e.getNome()));

        System.out.println("\n[Com Method Reference (toString completo)]:");
        turma.forEach(System.out::println);

        System.out.println("\n==================================================");
        System.out.println("       5. FILTRO: ALUNOS SEM DINHEIRO (DINHEIRO < 30)    ");
        System.out.println("==================================================");
        List<String> nomesLisos = turma.stream()
                .filter(e -> e.getDinheiro() < 30)
                .map(Aluno::getNome)
                .toList();

        nomesLisos.forEach(nome -> System.out.println(" - " + nome));

        System.out.println("\n==================================================");
        System.out.println("         EXERCÍCIO 1: INJEÇÃO DE VERBA            ");
        System.out.println("==================================================");
        double verba = carregarTurma().stream()
                .filter(e -> e.getDinheiro() < 30 && e.getEnergia() < 40)
                .mapToDouble(e -> 100 - e.getDinheiro())
                .sum();

        System.out.printf("Total de verba injetada necessária: R$ %.2f\n", verba);

        System.out.println("\n==================================================");
        System.out.println("    EXERCÍCIO 2: TOP 3 ALUNOS COM MENOR ÍNDICE    ");
        System.out.println("==================================================");
        turma.stream()
                .sorted((e1, e2) -> {
                    double indice1 = (e1.getEnergia() * 0.6) + (e1.getDinheiro() * 0.4);
                    double indice2 = (e2.getEnergia() * 0.6) + (e2.getDinheiro() * 0.4);
                    return Double.compare(indice1, indice2);
                })
                .limit(3)
                .map(Aluno::getNome)
                .forEach(nome -> System.out.println(" * " + nome));

        System.out.println("==================================================");
    }
}