package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializa a camada de persistência e cria a tabela no banco H2
        AlunoDAO alunoDAO = new AlunoDAOJDBC();
        alunoDAO.inicializarTabela();

        // 2. Instancia a camada de serviços do Model com injeção do DAO
        AlunoService alunoService = new AlunoService(alunoDAO);

        // 3. Instancia a camada de apresentação (View)
        AlunoView alunoView = new AlunoView();

        // 4. Instancia o Controller injetando a View e o Service
        AlunoController controller = new AlunoController(alunoView, alunoService);

        // 5. Inicia a malha de execução do sistema
        controller.iniciarAplicacao();
    }
}