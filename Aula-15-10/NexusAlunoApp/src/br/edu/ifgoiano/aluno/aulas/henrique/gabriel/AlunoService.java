package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.util.List;

// CAMADA MODEL: LÓGICA DE NEGÓCIO + INTERAÇÃO COM DAO
public class AlunoService {
    
    private final AlunoDAO alunoDAO;

    public AlunoService(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    public void cadastrarNovoAluno(Aluno aluno) {
        if (aluno.getMatricula() == null || aluno.getMatricula().isBlank()) {
            throw new IllegalArgumentException("A matrícula é obrigatória.");
        }
        if (alunoDAO.buscarPorMatricula(aluno.getMatricula()) != null) {
            throw new IllegalStateException("Já existe um aluno cadastrado com esta matrícula!");
        }
        alunoDAO.salvar(aluno);
    }

    public void executarRotinaEstudo(String matricula) {
        Aluno aluno = alunoDAO.buscarPorMatricula(matricula);
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não localizado para a matrícula informada.");
        }
        if (aluno.getStatus() != StatusMatricula.ATIVO) {
            throw new IllegalStateException("Apenas alunos com matrícula ATIVA podem estudar.");
        }
        
        // Aplica a regra de negócio do domínio e atualiza no banco H2
        aluno.realizarAtividadeDiaria();
        alunoDAO.atualizar(aluno);
    }

    public List<Aluno> consultarTurma() {
        return alunoDAO.listarTodos();
    }
}