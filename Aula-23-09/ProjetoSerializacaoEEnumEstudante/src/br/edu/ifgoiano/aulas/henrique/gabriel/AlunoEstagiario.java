package br.edu.ifgoiano.aulas.henrique.gabriel;

class AlunoEstagiario extends Aluno {
  private double bolsaEstagio;
  
  public AlunoEstagiario(String matricula, String nome, double energia, double dinheiro, StatusMatricula status, String tokenMoodle, double bolsaEsragio) {
    super(matricula, nome, energia, dinheiro, status, tokenMoodle);
    this.bolsaEstagio = bolsaEstagio;
  }

  @Override
  public void realizarAtividadeDiaria() {
      this.energia -= 35.0;
      this.dinheiro += (bolsaEstagio / 30.0);
      System.out.println(getNome() + " trabalhou e estudou. Energia: " + this.energia + "% | Saldo: R$ " + this.dinheiro);
  }
}