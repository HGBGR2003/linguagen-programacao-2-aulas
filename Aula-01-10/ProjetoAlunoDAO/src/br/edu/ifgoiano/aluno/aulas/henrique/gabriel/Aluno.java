package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representa a estrutura base abstrata de um Aluno no sistema acadêmico.
 * <p>
 * Esta classe define atributos essenciais compartilhados por qualquer tipo de aluno,
 * como dados de identificação, recursos vitais (energia e dinheiro) e status de matrícula.
 * Por ser abstrata, deve ser estendida para implementar comportamentos específicos,
 * como a rotina diária em {@link #realizarAtividadeDiaria()}.
 * </p>
 *
 * @author Henrique Gabriel
 * @version 1.0
 * @see java.io.Serializable
 * @see StatusMatricula
 */
abstract class Aluno implements Serializable {

    /**
     * Número de versão para controle de serialização da classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de matrícula do aluno.
     */
    private String matricula;

    /**
     * Nome completo do aluno.
     */
    private String nome;

    /**
     * Nível de energia atual do aluno (em porcentagem).
     */
    protected double energia;

    /**
     * Quantidade de dinheiro disponível para o aluno (em R$).
     */
    protected double dinheiro;

    /**
     * Situação cadastral atual da matrícula do aluno.
     */
    private StatusMatricula status;

    /**
     * Token de autenticação para integração com o ambiente virtual (Moodle).
     * <p>
     * Declarado como {@code transient} para que não seja persistido
     * durante o processo de serialização do objeto em disco.
     * </p>
     */
    private transient String tokenMoodle;

    /**
     * Construtor para inicialização completa de uma instância de Aluno.
     *
     * @param matricula   identificador de matrícula do aluno
     * @param nome        nome completo do aluno
     * @param energia     nível inicial de energia
     * @param dinheiro    saldo monetário inicial
     * @param status      estado inicial da matrícula (ex.: ativa, trancada)
     * @param tokenMoodle token de sessão ou acesso ao Moodle
     */
    public Aluno(String matricula, String nome, double energia, double dinheiro, StatusMatricula status, String tokenMoodle) {
        this.matricula = matricula;
        this.nome = nome;
        this.energia = energia;
        this.dinheiro = dinheiro;
        this.status = status;
        this.tokenMoodle = tokenMoodle;
    }

    /**
     * Obtém a matrícula do aluno.
     *
     * @return a matrícula do aluno
     */
    public String getMatricula() { 
        return matricula; 
    }

    /**
     * Obtém o nome completo do aluno.
     *
     * @return o nome do aluno
     */
    public String getNome() { 
        return nome; 
    }

    /**
     * Obtém o nível atual de energia do aluno.
     *
     * @return a energia atual (em %)
     */
    public double getEnergia() { 
        return energia; 
    }

    /**
     * Obtém a quantidade atual de dinheiro do aluno.
     *
     * @return o saldo em dinheiro (R$)
     */
    public double getDinheiro() { 
        return dinheiro; 
    }

    /**
     * Obtém a situação da matrícula do aluno.
     *
     * @return o {@link StatusMatricula} do aluno
     */
    public StatusMatricula getStatus() { 
        return status; 
    }

    /**
     * Obtém o token de autenticação do Moodle.
     *
     * @return o token do Moodle, ou {@code null} se o objeto tiver sido desserializado
     */
    public String getTokenMoodle() { 
        return tokenMoodle; 
    }

    /**
     * Executa a rotina ou atividade diária do aluno.
     * <p>
     * Método abstrato cuja lógica específica (gastos/ganhos de energia, 
     * dinheiro e atividades realizadas) deve ser implementada por cada subclasse.
     * </p>
     */
    public abstract void realizarAtividadeDiaria();

    /**
     * Compara este aluno com outro objeto para verificar igualdade.
     * Dois alunos são considerados iguais se possuírem a mesma matrícula.
     *
     * @param o objeto a ser comparado com esta instância
     * @return {@code true} se os objetos forem iguais; {@code false} caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(matricula, aluno.matricula);
    }

    /**
     * Retorna o código hash do aluno calculado com base em sua matrícula.
     *
     * @return valor hash gerado para o objeto
     */
    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    /**
     * Retorna a representação em formato de texto dos dados do aluno,
     * incluindo nome, matrícula, status, porcentagem de energia e saldo.
     *
     * @return string formatada com os dados do aluno
     */
    @Override
    public String toString() {
        return nome + " (" + matricula + ")[" + status + "]\n - Energia: " + energia + "%\n - R$ " + dinheiro + "\n";
    }
}