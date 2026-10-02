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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlunoMatricula() {
        return alunoMatricula;
    }

    public void setAlunoMatricula(String alunoMatricula) {
        this.alunoMatricula = alunoMatricula;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return "[" + data + "] (Matricula: " + alunoMatricula + ")" + descricao;
    }

}
