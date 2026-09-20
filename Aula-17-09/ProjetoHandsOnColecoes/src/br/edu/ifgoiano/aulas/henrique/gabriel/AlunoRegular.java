package br.edu.ifgoiano.aulas.henrique.gabriel;

/**
 * Representa um Aluno Regular de graduação dedicado exclusivamente aos estudos acadêmicos.
 * Estende a classe abstrata {@link Aluno}, implementando a rotina diária de estudos
 * com dedicação às disciplinas do curso.
 *
 * @author Henrique Gabriel Barbosa Guida Rodrigues
 * @version 1.0
 * @see Aluno
 */
public class AlunoRegular extends Aluno {

    /**
     * Construtor da classe AlunoRegular.
     *
     * @param matricula Número de matrícula do aluno.
     * @param nome      Nome do aluno regular.
     * @param energia   Nível inicial de energia.
     * @param dinheiro  Saldo financeiro inicial em reais.
     */
    public AlunoRegular(String matricula, String nome, double energia, double dinheiro) {
        super(matricula, nome, energia, dinheiro);
    }

    /**
     * Executa a rotina diária de estudos do aluno regular para a disciplina de LP2.
     * Consome 20% da sua energia atual sem gerar alterações no saldo financeiro.
     */
    @Override
    public void realizarAtividadeDiaria() {
        this.energia -= 20.0;
        System.out.println(getNome() + " estudou para LP2. Energia restante: " + this.energia + "%");
    }
}