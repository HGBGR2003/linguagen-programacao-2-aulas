package br.edu.ifgoiano.aulas.henrique.gabriel;

import java.util.*;

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
        System.out.println("Turma carregada com " + turma.size() + " alunos.");

        // for (int i = 0; i < turma.size(); i++) {
        // String aluno = turma.get(i).toString();
        // System.out.println(aluno);
        // }

        // for (br.edu.ifgoiano.aulas.henrique.gabriel.Aluno aluno : turma) {
        // System.out.println(aluno);
        // }

        // List<Double> notas = Arrays.asList(7.3, 5.8, 9.2, 6.4, 8.0, 10.0);

        // double soma = 0;
        // double media = 0;

        // for (Double double1 : notas) {
        // soma += double1;
        // }

        // media = soma / notas.size();

        // System.out.println("Somatorio " + Math.floor(soma));
        // System.out.println("Média " + Math.floor(media));

        // Usando uma List, adiciono todos os valores na lista, depois removo,
        // Todos usando o forEach, 
        // List<br.edu.ifgoiano.aulas.henrique.gabriel.Aluno> alunosRemover = new ArrayList<>();

        // for (br.edu.ifgoiano.aulas.henrique.gabriel.Aluno aluno : turma) {
        //     if (aluno.getEnergia() <= 0) {
        //         alunosRemover.add(aluno);
        //     }
        // }

        // turma.removeAll(alunosRemover);

        // System.out.println("Turma atual " + turma.size());

        Iterator<Aluno> al = turma.iterator();

        while (al.hasNext()) {
            Aluno aluno = al.next();
            if (aluno.getEnergia() <= 0.0) {
                al.remove();
            }
        }

        //Conteúdo Novo
        turma.forEach(System.out::println);

    }
}