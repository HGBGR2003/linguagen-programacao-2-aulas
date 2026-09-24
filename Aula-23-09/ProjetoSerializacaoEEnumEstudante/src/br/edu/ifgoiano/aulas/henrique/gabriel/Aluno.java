package br.edu.ifgoiano.aulas.henrique.gabriel;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Objects;

abstract class Aluno implements Serializable {
  private static final long serialVersionUID = 1L;
  private String matricula;
  private String nome;
  protected double energia;
  protected double dinheiro;
  private StatusMatricula status;

  // Atributo transient: ignorado durante a serialização em disco
  private transient String tokenMoodle;

  public Aluno(String matricula, String nome, double energia, double dinheiro, StatusMatricula status, String tokenMoodle) {
      this.matricula = matricula;
      this.nome = nome;
      this.energia = energia;
      this.dinheiro = dinheiro;
      this.status = status;
      this.tokenMoodle = tokenMoodle;
  }
  
  public String getMatricula() { return matricula; }
  public String getNome() { return nome; }
  public double getEnergia() { return energia; }
  public double getDinheiro() { return dinheiro; }
  public StatusMatricula getStatus() { return status; }
  public String getTokenMoodle() { return tokenMoodle; }

  // Método abstrato - precisa ser implementado em todos herdeiros
  public abstract void realizarAtividadeDiaria();

  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Aluno aluno = (Aluno) o;
      return Objects.equals(matricula, aluno.matricula);
  }

  @Override
  public int hashCode() {
      return Objects.hash(matricula);
  }

  @Override
  public String toString() {
      return nome + " (" + matricula + ")[" + status + "]\n - Energia: " + energia + "%\n - R$ " + dinheiro + "\n";
  }

  // readObject: chamado automaticamente pelo Java durante a desserialização.
  // Como tokenMoodle é transient (não salvo no .dat), precisamos recriá-lo aqui.
  private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject(); // restaura todos os campos não-transient normalmente
      this.tokenMoodle = "TOK-" + this.matricula; // regenera o token com base na matrícula
  }
}
