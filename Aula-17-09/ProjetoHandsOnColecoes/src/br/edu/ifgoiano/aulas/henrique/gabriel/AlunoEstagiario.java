package br.edu.ifgoiano.aulas.henrique.gabriel;

public class AlunoEstagiario extends Aluno {
    private double bolsaEstagio;

    public AlunoEstagiario(String matricula, String nome, double energia, double dinheiro, double bolsaEstagio) {
        super(matricula, nome, energia, dinheiro);
        this.bolsaEstagio = bolsaEstagio;
    }

    @Override
    public void realizarAtividadeDiaria() {
        this.energia -= 35.0;
        this.dinheiro += (bolsaEstagio / 30.0);
        System.out.println(getNome() + " trabalhou e estudou. Energia: " + this.energia + "% | Saldo: R$" + this.dinheiro);
    }
}