package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.util.List;
import java.util.Scanner;

// CAMADA VIEW: INTERFACE DE INTERAÇÃO VIA TERMINAL (CLI)
public class AlunoView {
    
    private final Scanner scanner = new Scanner(System.in);

    public int exibirMenuPrincipal() {
        System.out.println("\n=================================================");
        System.out.println("   UNIVERSITÁRIO PERRENGUE SIMULATOR — (MVC)     ");
        System.out.println("=================================================");
        System.out.println("1. Cadastrar Novo Aluno");
        System.out.println("2. Listar Turma Cadastrada no H2");
        System.out.println("3. Executar Rotina Diária de Estudo");
        System.out.println("0. Sair da Aplicação");
        System.out.print("Selecione a opção desejada: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public Aluno lerDadosNovoAluno() {
        System.out.println("\n--- CADASTRO DE ALUNO ---");
        System.out.print("Matrícula: ");
        String mat = scanner.nextLine();
        System.out.print("Nome Completo: ");
        String nome = scanner.nextLine();
        System.out.print("Energia Inicial (0 a 100): ");
        double energia = Double.parseDouble(scanner.nextLine());
        System.out.print("Dinheiro Inicial (R$): ");
        double dinheiro = Double.parseDouble(scanner.nextLine());

        return new AlunoRegular(mat, nome, energia, dinheiro, StatusMatricula.ATIVO);
    }

    public String lerMatriculaParaEstudo() {
        System.out.print("\nInforme a matrícula do aluno que vai estudar: ");
        return scanner.nextLine();
    }

    public void exibirTurma(List<Aluno> turma) {
        System.out.println("\n--- RELATÓRIO DE ALUNOS NO BANCO H2 ---");
        if (turma.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado até o momento.");
        } else {
            for (Aluno a : turma) {
                System.out.printf("• %s | %-20s | Energia: %5.1f%% | Saldo: R$ %6.2f | [%s]%n",
                        a.getMatricula(), a.getNome(), a.getEnergia(), a.getDinheiro(), a.getStatus());
            }
        }
    }

    public void exibirMensagemSucesso(String msg) {
        System.out.println("\n[SISTEMA - SUCESSO] " + msg);
    }

    public void exibirMensagemErro(String erro) {
        System.err.println("\n[SISTEMA - ERRO] " + erro);
    }
}