package br.edu.ifgoiano.aulas.henrique.gabriel;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.Predicate;

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
                    turma.remove(e); // ESTOURA ConcurrentModificationException!
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("SISTEMA CAIU! O laço for-each não permite modificar a lista enquanto a lê.");
        }
    }


}
