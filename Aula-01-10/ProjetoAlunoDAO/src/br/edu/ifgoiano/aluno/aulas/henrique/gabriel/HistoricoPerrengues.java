package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

/**
 * Representa um registro individual no histórico de imprevistos ("perrengues")
 * vivenciados por um aluno durante a sua trajetória acadêmica.
 * <p>
 * Esta classe é utilizada como modelo de dados (POJO) para transferir informações
 * entre a aplicação e a tabela {@code historico_perrengues} no banco de dados.
 * </p>
 *
 * @author Henrique Gabriel
 * @version 1.0
 * @see Aluno
 * @see AlunoDAO
 */
public class HistoricoPerrengues {

    /**
     * Identificador único do registro no banco de dados (chave primária autoincrementada).
     */
    private int id;

    /**
     * Matrícula do aluno ao qual o perrengue está associado (chave estrangeira).
     */
    private String alunoMatricula;

    /**
     * Descrição detalhada do perrengue ou imprevisto ocorrido.
     */
    private String descricao;

    /**
     * Data da ocorrência do perrengue em formato textual (ex.: "DD/MM/AAAA").
     */
    private String data;

    /**
     * Construtor para criação de novo registro de perrengue sem identificador.
     * <p>
     * Utilizado principalmente antes da persistência no banco de dados,
     * onde o identificador ({@code id}) será gerado automaticamente.
     * </p>
     *
     * @param alunoMatricula matrícula do aluno envolvido
     * @param descricao      detalhes do perrengue
     * @param data           data da ocorrência
     */
    public HistoricoPerrengues(String alunoMatricula, String descricao, String data) {
        this.alunoMatricula = alunoMatricula;
        this.descricao = descricao;
        this.data = data;
    }

    /**
     * Construtor completo para reconstituição de um registro já existente no banco de dados.
     *
     * @param id             identificador único (chave primária)
     * @param alunoMatricula matrícula do aluno associado
     * @param descricao      detalhes do perrengue
     * @param data           data da ocorrência
     */
    public HistoricoPerrengues(int id, String alunoMatricula, String descricao, String data) {
        this.id = id;
        this.alunoMatricula = alunoMatricula;
        this.descricao = descricao;
        this.data = data;
    }

    /**
     * Obtém o identificador único do perrengue.
     *
     * @return o identificador {@code id} do registro
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador único do perrengue.
     *
     * @param id o novo identificador a ser atribuído
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém a matrícula do aluno associado a este registro.
     *
     * @return a matrícula do aluno
     */
    public String getAlunoMatricula() {
        return alunoMatricula;
    }

    /**
     * Define a matrícula do aluno associado a este registro.
     *
     * @param alunoMatricula a nova matrícula a ser vinculada
     */
    public void setAlunoMatricula(String alunoMatricula) {
        this.alunoMatricula = alunoMatricula;
    }

    /**
     * Obtém a descrição do perrengue.
     *
     * @return o texto descritivo do imprevisto
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do perrengue.
     *
     * @param descricao o novo texto descritivo
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a data da ocorrência do perrengue.
     *
     * @return a data em formato textual
     */
    public String getData() {
        return data;
    }

    /**
     * Define a data da ocorrência do perrengue.
     *
     * @param data a nova data no formato textual
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Retorna a representação textual formatada do registro de perrengue,
     * contendo data, matrícula do aluno e descrição.
     *
     * @return string formatada representando o perrengue
     */
    @Override
    public String toString() {
        return "[" + data + "] (Matricula: " + alunoMatricula + ") " + descricao;
    }
}