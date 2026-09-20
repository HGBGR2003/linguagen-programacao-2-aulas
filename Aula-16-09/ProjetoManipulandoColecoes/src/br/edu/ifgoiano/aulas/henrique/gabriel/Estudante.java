package br.edu.ifgoiano.aulas.henrique.gabriel;

import java.util.Objects;

/**
 * Representa a estrutura base abstrata de um Estudante universitário.
 * Gerencia os dados cadastrais (matrícula e nome) e os recursos vitais (energia e saldo financeiro).
 * Define o contrato para a execução da rotina diária e implementa critérios de unicidade
 * baseados exclusivamente no número de matrícula.
 *
 * @author Henrique Gabriel Barbosa Guida Rodrigues
 * @version 1.0
 */
public abstract class Estudante {

    /**
     * Identificador único de matrícula do estudante.
     */
    private String matricula;

    /**
     * Nome completo do estudante.
     */
    private String nome;

    /**
     * Nível atual de energia do estudante (escala de 0.0 a 100.0).
     */
    protected double energia;

    /**
     * Saldo financeiro disponível do estudante em reais (R$).
     */
    protected double dinheiro;

    /**
     * Construtor da classe abstrata Estudante.
     *
     * @param matricula Código ou número de matrícula único.
     * @param nome      Nome do estudante.
     * @param energia   Porcentagem de energia inicial (0.0 a 100.0).
     * @param dinheiro  Saldo financeiro inicial em reais.
     */
    public Estudante(String matricula, String nome, double energia, double dinheiro) {
        this.matricula = matricula;
        this.nome = nome;
        this.energia = energia;
        this.dinheiro = dinheiro;
    }

    /**
     * Obtém o número de matrícula do estudante.
     *
     * @return A matrícula do estudante.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Obtém o nome do estudante.
     *
     * @return O nome do estudante.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém a quantidade atual de energia do estudante.
     *
     * @return A energia atual (0.0 a 100.0).
     */
    public double getEnergia() {
        return energia;
    }

    /**
     * Obtém o saldo financeiro do estudante em reais.
     *
     * @return O valor em dinheiro disponível.
     */
    public double getDinheiro() {
        return dinheiro;
    }

    /**
     * Executa a rotina diária característica de cada perfil de estudante.
     * Método abstrato que força cada subclasse a definir suas próprias regras de consumo e ganho.
     */
    public abstract void realizarAtividadeDiaria();

    /**
     * Compara a igualdade deste estudante com outro objeto com base na {@code matricula}.
     *
     * @param o Objeto a ser comparado com a instância atual.
     * @return {@code true} se ambos possuírem a mesma matrícula, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudante estudante = (Estudante) o;
        return Objects.equals(matricula, estudante.matricula);
    }

    /**
     * Gera o código hash correspondente à matrícula do estudante para uso em estruturas como {@link java.util.HashSet} ou {@link java.util.HashMap}.
     *
     * @return O valor hash calculado a partir da matrícula.
     */
    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    /**
     * Retorna uma representação textual formatada com os dados essenciais do estudante.
     *
     * @return String com nome, matrícula, energia e dinheiro.
     */
    @Override
    public String toString() {
        return nome + " (Matrícula: " + matricula + " | Energia: " + energia + "% | R$ " + dinheiro + ")";
    }
}