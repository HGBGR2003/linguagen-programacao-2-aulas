package br.edu.ifgoiano.aulas.henrique.gabriel;

import java.util.*;
import java.util.function.Consumer;

public class Main {

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

    static void main(String[] args) {
        List<Aluno> turma = carregarTurma();
        // System.out.println("Turma carregada com " + turma.size() + " alunos.");

        for (int i = 0; i < turma.size(); i++) {
        String aluno = turma.get(i).toString();
        System.out.println(aluno);
        }

        for (br.edu.ifgoiano.aulas.henrique.gabriel.Aluno aluno : turma) {
        System.out.println(aluno);
        }

        List<Double> notas = Arrays.asList(7.3, 5.8, 9.2, 6.4, 8.0, 10.0);

        double soma = 0;
        double media = 0;

        for (Double double1 : notas) {
        soma += double1;
        }

        media = soma / notas.size();

        System.out.println("Somatorio " + Math.floor(soma));
        System.out.println("Média " + Math.floor(media));

        // Usando uma List, adiciono todos os valores na lista, depois removo,
        // Todos usando o forEach,
        List<br.edu.ifgoiano.aulas.henrique.gabriel.Aluno> alunosRemover = new
        ArrayList<>();

        for (br.edu.ifgoiano.aulas.henrique.gabriel.Aluno aluno : turma) {
        if (aluno.getEnergia() <= 0) {
        alunosRemover.add(aluno);
        }
        }

        turma.removeAll(alunosRemover);

        System.out.println("Turma atual " + turma.size());

        Iterator<Aluno> al = turma.iterator();

        while (al.hasNext()) {
        Aluno aluno = al.next();
        if (aluno.getEnergia() <= 0.0) {
        al.remove();
        }
        }

        // //Conteúdo Novo
        // turma.forEach(System.out::println);

        // Jeito Padrão de Escrever, sem ser em expressão lambda.
        turma.forEach(new Consumer<Aluno>() {
            @Override
            public void accept(Aluno aluno) {
                System.out.println(aluno.getNome());
            }
        });

        turma.forEach(e -> System.out.println(e.getNome()));

        // Metódo de referencia
        turma.forEach(System.out::println);
        
        List<String> nomesLisos = turma.stream()
        .filter(e -> e.getDinheiro() < 30)
        .map(Aluno::getNome)
        .toList();

        nomesLisos.forEach(System.out::println);

        //Exercicio 1
        double verba = turma.stream()
        .filter(e -> e.getDinheiro() < 30 && e.getEnergia() < 40)
        .mapToDouble(e -> 100 - e.getDinheiro())
        .sum();

        System.out.println("Dinheiro Ingetado: " + verba);

        System.out.println("------------------------------------------");


        //Exercicio 2
        turma.stream()
        .sorted((e1, e2) -> {
            double indice1 = (e1.getEnergia() * 0.6) + (e1.getDinheiro() * 0.4);
            
            double indice2 = (e2.getEnergia() * 0.6) + (e2.getDinheiro() * 0.4);

            return Double.compare(indice1, indice2);

        })
        .limit(3)
        .map(e -> e.getNome())
        .forEach(System.out::println);

    }
}