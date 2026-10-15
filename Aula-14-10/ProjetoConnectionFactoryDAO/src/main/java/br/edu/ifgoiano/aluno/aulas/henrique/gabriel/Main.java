package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== UNIVERSITÁRIO PERRENGUE SIMULATOR v2 (REFATORADO) ===");

        // 1. Instanciação da camada de persistência orientada a contrato (Interface AlunoDAO)
        AlunoDAO dao = new AlunoDAOJDBC();
        dao.inicializarTabela();

        // 2. Instanciação de objetos do domínio Aluno no Heap
        Aluno a1 = new AlunoRegular("20261EC001", "Lucas Santos", 50.0, 15.0, StatusMatricula.ATIVO);
        Aluno a2 = new AlunoRegular("20261EC005", "Dario Jubilado", 0.0, 10.0, StatusMatricula.JUBILADO);

        // 3. Persistência transparente (sem código SQL na classe executável)
        dao.salvar(a1);
        dao.salvar(a2);

        // 4. Leitura e exibição dos registros
        System.out.println("\n--- TURMA CARREGADA DO BANCO H2 ---");
        List<Aluno> lista = dao.listarTodos();
        for (Aluno a : lista) {
            System.out.println("• " + a.getMatricula() + " | " + a.getNome() + " [" + a.getStatus().getDescricao() + "]");
        }
    }
}