package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

/**
 * Representa a implementação concreta de um aluno regular da instituição.
 * <p>
 * Um aluno regular tem como rotina diária os estudos acadêmicos padrão,
 * com consumo de energia ponderado pelo fator associado ao seu {@link StatusMatricula}.
 * </p>
 *
 * @author Henrique Gabriel
 * @version 1.0
 * @see Aluno
 * @see StatusMatricula
 */
class AlunoRegular extends Aluno {

    /**
     * Construtor para instanciar um aluno regular com todos os seus atributos fundamentais.
     *
     * @param matricula identificador único de matrícula
     * @param nome      nome completo do aluno
     * @param energia   nível inicial de energia (em porcentagem)
     * @param dinheiro  saldo monetário inicial
     * @param status    situação inicial da matrícula
     * @param token     token de autenticação do Moodle
     */
    public AlunoRegular(String matricula, String nome, double energia, double dinheiro, StatusMatricula status, String token) {
        super(matricula, nome, energia, dinheiro, status, token);
    }

    /**
     * Executa a rotina diária de estudos do aluno regular.
     * <p>
     * Se o aluno estiver com status {@link StatusMatricula#ATIVO}, realiza o estudo
     * consumindo {@code 20.0} pontos de energia multiplicados pelo fator de gasto
     * da matrícula. Caso contrário, a ação é impedida devido ao status irregular.
     * </p>
     */
    @Override
    public void realizarAtividadeDiaria() {
        if (getStatus() == StatusMatricula.ATIVO) {
            this.energia -= (20.0 * getStatus().getFatorGastoEnergia());
            System.out.println(getNome() + " estudou. Energia restante: " + this.energia + "%");
        } else {
            System.out.println(getNome() + " impedido de estudar. Status: " + getStatus());
        }
    }

    /**
     * Aplica um desconto manual direto sobre a energia do aluno.
     *
     * @param valor quantidade de pontos de energia a ser subtraída
     * @deprecated Este método não considera os fatores de gasto do {@link StatusMatricula}
     *             e será removido em versões futuras. Recomenda-se utilizar o método
     *             {@link #realizarAtividadeDiaria()}.
     */
    @Deprecated
    public void aplicarDescontoEnergiaManual(double valor) {
        this.energia -= valor;
    }

//     @SuppressWarnings("deprecation") // Silencia o aviso ao chamar o método antigo
//     public void executarRotinaAntiga() {
//         aplicarDescontoEnergiaManual(10.0);
//     }
}