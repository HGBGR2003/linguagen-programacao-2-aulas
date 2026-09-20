package br.edu.ifgoiano.aulas.henrique.gabriel;

import java.util.Objects;

/**
 * Representa a estrutura base abstrata de um Aluno no contexto acadêmico.
 * Gerencia dados cadastrais como matrícula e nome, além de recursos essenciais
 * de energia e dinheiro. Define a obrigatoriedade da atividade diária específica
 * e implementa critérios de igualdade baseados unicamente na matrícula.
 *
 * @author Henrique Gabriel Barbosa Guida Rodrigues
 * @version 1.0
 */
public abstract class Aluno {

    /**
     * Identificador único de matrícula do aluno.
     */
    private String matricula;

    /**
     * Nome completo do aluno.
     */
    private String nome;

    /**
     * Porcentagem atual de energia do aluno (0% a 100%).
     */
    protected double energia;

    /**
     * Saldo financeiro disponível do aluno em reais (R$).
     */
    protected double dinheiro;

    /**
     * Construtor da classe abstrata Aluno.
     *
     * @param matricula Número ou código de matrícula do aluno.
     * @param nome      Nome do aluno.
     * @param energia   Porcentagem de energia inicial.
     * @param dinheiro  Saldo financeiro inicial em reais.
     */
    public Aluno(String matricula, String nome, double energia, double dinheiro) {
        this.matricula = matricula;
        this.nome = nome;
        this.energia = energia;
        this.dinheiro = dinheiro;
    }

    /**
     * Obtém o número de matrícula do aluno.
     *
     * @return A matrícula do aluno.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Obtém o nome completo do aluno.
     *
     * @return O nome do aluno.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém a porcentagem atual de energia do aluno.
     *
     * @return A energia atual.
     */
    public double getEnergia() {
        return energia;
    }

    /**
     * Obtém o saldo financeiro atual do aluno em reais.
     *
     * @return O valor em dinheiro.
     */
    public double getDinheiro() {
        return dinheiro;
    }

    /**
     * Executa a rotina da atividade diária característica do tipo de aluno.
     * Deve ser implementado pelas subclasses para refletir o consumo ou ganho de energia e dinheiro.
     */
    public abstract void realizarAtividadeDiaria();

    /**
     * Compara a igualdade entre este aluno e outro objeto com base na {@code matricula}.
     *
     * @param o Objeto a ser comparado com a instância atual.
     * @return {@code true} se ambos os objetos possuírem a mesma matrícula, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(matricula, aluno.matricula);
    }

    /**
     * Gera o código hash do aluno a partir da sua matrícula para uso em coleções hash.
     *
     * @return O valor de hash code correspondente à matrícula.
     */
    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    /**
     * Retorna a representação textual formatada contendo os dados principais do aluno.
     *
     * @return String formatada com nome, matrícula, energia e saldo financeiro.
     */
    @Override
    public String toString() {
        return nome + " (" + matricula + ")\n - Energia: " + energia + "%\n - R$ " + dinheiro + "\n";
    }
}