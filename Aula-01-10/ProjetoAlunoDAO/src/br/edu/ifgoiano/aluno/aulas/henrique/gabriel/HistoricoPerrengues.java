package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

public class HistoricoPerrengues {
    private int id;
    private String alunoMatricula;
    private String descricao;
    private String data;

    public HistoricoPerrengues(String aluoMatricula, String descricao, String data){
        this.alunoMatricula = aluoMatricula;
        this.descricao = descricao;
        this.data = data;
    }

    public HistoricoPerrengues(int id, String alunoMatricula, String descricao, String data){
        this.id = id;
        this.alunoMatricula = alunoMatricula;
        this.descricao = descricao;
        this.data = data;
    }
}
