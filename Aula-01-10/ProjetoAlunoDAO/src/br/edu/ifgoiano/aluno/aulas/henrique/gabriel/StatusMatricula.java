package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

/**
 * Define os possíveis estados da situação de matrícula de um aluno na instituição,
 * associando cada situação a uma descrição legível e a um multiplicador de consumo de energia.
 *
 * @author Henrique Gabriel
 * @version 1.0
 * @see Aluno
 * @see AlunoRegular
 */
public enum StatusMatricula {

    /**
     * Indica que o aluno está com a matrícula regular e apto a participar
     * das atividades acadêmicas diárias com consumo integral de energia (fator 1.0).
     */
    ATIVO("Matrícula Regular", 1.0),

    /**
     * Indica que a matrícula do aluno está temporariamente suspensa/trancada,
     * impedindo a realização de atividades e sem consumo de energia (fator 0.0).
     */
    TRANCADO("Matrícula Trancada", 0.0),

    /**
     * Indica que o vínculo acadêmico do aluno foi encerrado por esgotamento de prazo ou exaustão,
     * não permitindo mais atividades acadêmicas (fator 0.0).
     */
    JUBILADO("Jubilado por Exaustão", 0.0);

    /**
     * Descrição textual legível do status da matrícula.
     */
    private final String descricao;

    /**
     * Fator multiplicador aplicado sobre o consumo de energia nas atividades diárias.
     */
    private final double fatorGastoEnergia;

    /**
     * Construtor interno para parametrizar as constantes da enumeração.
     *
     * @param descricao          descrição amigável do status
     * @param fatorGastoEnergia  multiplicador de consumo de energia associado
     */
    StatusMatricula(String descricao, double fatorGastoEnergia) {
        this.descricao = descricao;
        this.fatorGastoEnergia = fatorGastoEnergia;
    }

    /**
     * Obtém a descrição amigável do status da matrícula.
     *
     * @return texto descritivo do status
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Obtém o fator multiplicador de consumo de energia.
     *
     * @return o multiplicador de gasto de energia
     */
    public double getFatorGastoEnergia() {
        return fatorGastoEnergia;
    }
}