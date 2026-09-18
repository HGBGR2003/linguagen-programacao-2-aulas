package br.edu.ifgoiano.aulas.henrique.gabriel;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static List<Estudante> filtrar(List<Estudante> lista, Predicate<Estudante> predicado) {
        List<Estudante> resultado = new ArrayList<>();
        for (Estudante e : lista) {
            if (predicado.test(e)) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    static void main() {
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

        System.out.println("=== COM FOR-EACH ===");
        try {
            for (Estudante e : turma) {
                if (e.getEnergia() <= 0.0) {
                    turma.remove(e);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("SISTEMA CAIU! O laço for-each não permite modificar a lista enquanto a lê.");
        }

//        //Exercicio 1, use double se quiser converter para double.
//        double totalInjetado = turma.stream()
//                .filter(e -> e.getDinheiro() < 30 && e.getEnergia() < 40)
//                .mapToDouble(e -> 100 - e.getDinheiro())
//                .sum();
//
//        System.out.println("Total a ser injetado pela universidade: R$ " + totalInjetado);

//        // Exercicio 2
//        turma.stream()
//                .sorted((e1,e2) -> {
//                    double indiceAluno1 = (e1.getEnergia() * 0.6) + (e1.getDinheiro() * 0.4);
//                    double indiceAluno2 = (e2.getEnergia() * 0.6) + (e2.getDinheiro() * 0.4);
//                    return Double.compare(indiceAluno1, indiceAluno2);
//                })
//                .limit(3)
//                .map(e -> e.getNome().toUpperCase())
//                .forEach(System.out::println);

        //Exercicio 3
        Map<String, List<Estudante>> alunoRisco = turma.stream()
                .collect(Collectors.groupingBy(e ->{
                    if (e.getEnergia() <= 20 || e.getDinheiro() <= 10) {
                        return "Critico";
                    }else if (e.getEnergia() <= 50) {
                        return "Alerta";
                    }else{
                        return "Estável";
                    }
                }));

        alunoRisco.forEach((categoria, lista) -> {
            System.out.println("\n=== " + categoria + " (" + lista.size() + " alunos) ===");
            lista.forEach(e -> System.out.printf("- %s [Energia: %.1f%%, Dinheiro: R$ %.2f]%n",
                    e.getNome(), e.getEnergia(), e.getDinheiro()));
        });
    }
}
