package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

public class AlunoController {

    private final AlunoView view;
    private final AlunoService service;

    public AlunoController(AlunoView view, AlunoService service) {
        this.view = view;
        this.service = service;
    }

    public void iniciarAplicacao() {
        int opcao;
        do {
            opcao = view.exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> processarCadastro();
                case 2 -> processarListagem();
                case 3 -> processarEstudo();
                case 0 -> view.exibirMensagemSucesso("Encerrando o simulador. Até logo!");
                default -> view.exibirMensagemErro("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void processarCadastro() {
        try {
            Aluno novoAluno = view.lerDadosNovoAluno();
            service.cadastrarNovoAluno(novoAluno);
            view.exibirMensagemSucesso("Aluno cadastrado e persistido no banco H2!");
        } catch (Exception e) {
            view.exibirMensagemErro(e.getMessage());
        }
    }

    private void processarListagem() {
        try {
            var turma = service.consultarTurma();
            view.exibirTurma(turma);
        } catch (Exception e) {
            view.exibirMensagemErro("Erro ao consultar o banco H2: " + e.getMessage());
        }
    }

    private void processarEstudo() {
        try {
            String mat = view.lerMatriculaParaEstudo();
            service.executarRotinaEstudo(mat);
            view.exibirMensagemSucesso("Rotina de estudo concluída! Energia atualizada no H2.");
        } catch (Exception e) {
            view.exibirMensagemErro(e.getMessage());
        }
    }
}