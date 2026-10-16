package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.util.List;
// INTERFACE DO CONTRATO DAO (INDEPENDENTE DE TECNOLOGIA)
public interface AlunoDAO {
    void inicializarTabela();
    void salvar(Aluno aluno);
    Aluno buscarPorMatricula(String matricula);
    List<Aluno> listarTodos();
    void atualizar(Aluno aluno);
    void deletar(String matricula);
}