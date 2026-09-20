package br.edu.ifgoiano.aulas.henrique.gabriel;

/**
 * Representa um Aluno Estagiário que combina atividades acadêmicas com jornada de estágio remunerado.
 * Estende a classe {@link Aluno}, aplicando regras de consumo de energia e ganho financeiro diário
 * proporcional ao valor da sua bolsa de estágio.
 *
 * @author Henrique Gabriel Barbosa Guida Rodrigues
 * @version 1.0
 * @see Aluno
 */
public class AlunoEstagiario extends Aluno {

    /**
     * Valor mensal em reais (R$) da bolsa de estágio recebida pelo aluno.
     */
    private double bolsaEstagio;

    /**
     * Construtor da classe AlunoEstagiario.
     *
     * @param matricula    Número de matrícula do aluno.
     * @param nome         Nome do aluno estagiário.
     * @param energia      Nível inicial de energia.
     * @param dinheiro     Saldo financeiro inicial em reais.
     * @param bolsaEstagio Valor mensal da bolsa de estágio em reais.
     */
    public AlunoEstagiario(String matricula, String nome, double energia, double dinheiro, double bolsaEstagio) {
        super(matricula, nome, energia, dinheiro);
        this.bolsaEstagio = bolsaEstagio;
    }

    /**
     * Executa a rotina diária de trabalho e estudo do estagiário.
     * Reduz a energia em 35% e credita a fração diária da bolsa de estágio (bolsa / 30) ao saldo financeiro.
     */
    @Override
    public void realizarAtividadeDiaria() {
        this.energia -= 35.0;
        this.dinheiro += (bolsaEstagio / 30.0);
        System.out.println(getNome() + " trabalhou e estudou. Energia: " + this.energia + "% | Saldo: R$" + this.dinheiro);
    }

    /**
     * Obtém o valor mensal da bolsa de estágio.
     *
     * @return O valor da bolsa em reais.
     */
    public double getBolsaEstagio() {
        return bolsaEstagio;
    }

    /**
     * Define ou atualiza o valor da bolsa de estágio.
     *
     * @param bolsaEstagio Novo valor mensal da bolsa em reais.
     */
    public void setBolsaEstagio(double bolsaEstagio) {
        this.bolsaEstagio = bolsaEstagio;
    }
}