package br.edu.ifgoiano.aulas.henrique.gabriel;

/**
 * Representa um Aluno Regular que estende a classe base {@link Estudante}.
 * Dedica-se exclusivamente às rotinas acadêmicas, consumindo energia durante
 * as sessões de estudo para a disciplina de LP2.
 *
 * @author Henrique Gabriel Barbosa Guida Rodrigues
 * @version 1.0
 * @see Estudante
 */
public class AlunoRegular extends Estudante {

    /**
     * Construtor da classe AlunoRegular.
     *
     * @param matricula Código ou número de matrícula do aluno.
     * @param nome      Nome do aluno regular.
     * @param energia   Porcentagem de energia inicial.
     * @param dinheiro  Saldo financeiro inicial em reais.
     */
    public AlunoRegular(String matricula, String nome, double energia, double dinheiro) {
        super(matricula, nome, energia, dinheiro);
    }

    /**
     * Executa a rotina de estudos do aluno regular para a disciplina de Linguagem de Programação 2 (LP2).
     * Consome 20% da sua energia vital acumulada sem alterar o saldo financeiro.
     */
    @Override
    public void realizarAtividadeDiaria() {
        this.energia -= 20.0; // Estudar consome energia
        System.out.println(getNome() + " estudou para LP2. Energia atual: " + this.energia + "%");
    }
}