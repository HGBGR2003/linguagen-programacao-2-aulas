package br.edu.ifgoiano.aulas.henrique.gabriel;

import java.util.Objects;

public abstract class Estudante {
    private String matricula;
    private String nome;
    protected double energia;  // 0.0 a 100.0
    protected double dinheiro; // Saldo em R$

    public Estudante(String matricula, String nome, double energia, double dinheiro) {
        this.matricula = matricula;
        this.nome = nome;
        this.energia = energia;
        this.dinheiro = dinheiro;
    }

    // Métodos Getters e Setters
    public String getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public double getEnergia() { return energia; }
    public double getDinheiro() { return dinheiro; }

    // Método Abstrato: Força cada tipo de aluno a definir sua rotina diária
    public abstract void realizarAtividadeDiaria();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudante estudante = (Estudante) o;
        return Objects.equals(matricula, estudante.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    @Override
    public String toString() {
        return nome + " (Matrícula: " + matricula + " | Energia: " + energia + "% | R$ " + dinheiro + ")";
    }
}
