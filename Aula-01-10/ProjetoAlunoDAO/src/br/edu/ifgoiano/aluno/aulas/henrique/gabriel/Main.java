package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.util.List;

public class Main {
      public static void main(String[] args) {
        AlunoDAO dao = new AlunoDAO();
        dao.inicializarTabela();

        System.out.println("\n=== 1. POPULANDO O BANCO H2 (CREATE) ===");
        Aluno e1 = new AlunoRegular("20261EC001", "Lucas Santos", 50.0, 100.0, StatusMatricula.ATIVO, null);
        Aluno e2 = new AlunoRegular("20261EC002", "Ana Vilela", 80.0, 20.0, StatusMatricula.ATIVO, null);
        Aluno e3 = new AlunoRegular("20261EC005", "Dario Jubilado", 0.0, 10.0, StatusMatricula.JUBILADO, null);
        
        dao.salvar(e1);
        dao.salvar(e2);
        dao.salvar(e3);

        System.out.println("\n=== 2. LISTANDO DADOS INICIAIS (READ) ===");
        imprimirTurma(dao.listarTodos());

        System.out.println("\n=== 3. ATUALIZANDO DADOS (UPDATE) ===");
        e1.realizarAtividadeDiaria(); // Estudou para LP2, reduz energia
        dao.atualizar(e1);

        System.out.println("\n=== 4. TESTANDO TRANSAÇÃO FINANCEIRA COM SUCESSO (PIX) ===");
        dao.transferirDinheiro("20261EC001", "20261EC002", 30.0); // Lucas transfere 30 para Ana

        System.out.println("\n=== 5. TESTANDO TRANSAÇÃO COM FALHA E ROLLBACK ===");
        dao.transferirDinheiro("20261EC002", "20261EC001", 500.0); // Ana tenta transferir mais do que possui (Saldo insuficiente)

        System.out.println("\n=== 6. REMOVENDO REGISTRO (DELETE) ===");
        dao.deletar("20261EC005"); // Remove aluno jubilado

        System.out.println("\n=== 7. ESTADO FINAL DO BANCO DE DADOS H2 ===");
        imprimirTurma(dao.listarTodos());
    }

    private static void imprimirTurma(List<Aluno> turma) {
        for (Aluno e : turma) {
            System.out.println("  • " + e.getMatricula() + " | " + e.getNome() +
                               " [" + e.getStatus() + "] | Energia: " + e.getEnergia() +
                               "% | Saldo: R$ " + e.getDinheiro());
        }
    }
}
