package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.util.List;

/**
 * Ponto de entrada (método principal) e classe de demonstração do sistema acadêmico.
 * <p>
 * Executa um roteiro interativo de testes no console validando o ciclo de vida completo:
 * <ul>
 *   <li>Criação de tabelas DDL no banco de dados em memória H2</li>
 *   <li>Operações CRUD (Create, Read, Update, Delete) com {@link Aluno}</li>
 *   <li>Controle transacional ACID com commit e rollback (transferência PIX)</li>
 *   <li>Atualizações em lote (<em>batch processing</em>)</li>
 *   <li>Relacionamento 1:N entre {@link Aluno} e {@link HistoricoPerrengues}</li>
 * </ul>
 * </p>
 *
 * @author Henrique Gabriel
 * @version 1.0
 * @see AlunoDAO
 * @see AlunoRegular
 * @see HistoricoPerrengues
 * @see StatusMatricula
 */
public class Main {

    /**
     * Método principal que inicializa e orquestra o fluxo de execução e testes da aplicação.
     *
     * @param args argumentos de linha de comando passados durante a invocação da JVM (não utilizados)
     */
    public static void main(String[] args) {
        AlunoDAO dao = new AlunoDAO();
        dao.inicializarTabela();
        dao.criacaoTabelaHistorico();

        System.out.println("\n=== 1. POPULANDO O BANCO H2 (CREATE) ===");
        Aluno e1 = new AlunoRegular("20261EC001", "Lucas Santos",  50.0, 100.0, StatusMatricula.ATIVO,    null);
        Aluno e2 = new AlunoRegular("20261EC002", "Ana Vilela",    80.0,  20.0, StatusMatricula.ATIVO,    null);
        Aluno e3 = new AlunoRegular("20261EC005", "Dario Jubilado", 0.0,  10.0, StatusMatricula.JUBILADO, null);

        dao.salvar(e1);
        dao.salvar(e2);
        dao.salvar(e3);

        System.out.println("\n=== 2. LISTANDO DADOS INICIAIS (READ) ===");
        imprimirTurma(dao.listarTodos());

        System.out.println("\n=== 3. ATUALIZANDO DADOS (UPDATE) ===");
        e1.realizarAtividadeDiaria(); 
        dao.atualizar(e1);

        System.out.println("\n=== 4. TESTANDO TRANSAÇÃO FINANCEIRA COM SUCESSO (PIX) ===");
        dao.transferirDinheiro("20261EC001", "20261EC002", 30.0); 

        System.out.println("\n=== 5. TESTANDO TRANSAÇÃO COM FALHA E ROLLBACK ===");
        dao.transferirDinheiro("20261EC002", "20261EC001", 500.0);

        System.out.println("\n=== 6. REMOVENDO REGISTRO (DELETE) ===");
        dao.deletar("20261EC005"); 

        System.out.println("\n=== 7. ESTADO FINAL DO BANCO DE DADOS H2 ===");
        imprimirTurma(dao.listarTodos());

        System.out.println("\n=== 8. CONCEDENDO AUXÍLIO EM LOTE ===");
        List<String> matriculas = List.of("20261EC001", "20261EC002", "20261EC999");
        dao.concederAuxilioEmLote(matriculas, 250.0);

        System.out.println("\n=== 9. ESTADO APÓS AUXÍLIO EM LOTE ===");
        imprimirTurma(dao.listarTodos());

        System.out.println("\n=== 10. REGISTRANDO PERRENGUES NO HISTÓRICO ===");
        dao.adicionarPerrengue("20261EC001", "Perdeu a prova por atraso do ônibus",       "2024-08-15");
        dao.adicionarPerrengue("20261EC001", "Computador quebrou antes de entregar o TP", "2024-09-01");
        dao.adicionarPerrengue("20261EC001", "Esqueceu o carregador no dia da prova",     "2024-10-01");
        dao.adicionarPerrengue("20261EC002", "Ficou sem internet na semana das entregas", "2024-09-20");

        System.out.println("\n=== 11. HISTÓRICO DE PERRENGUES POR ALUNO ===");
        System.out.println("  >> Perrengues de Lucas Santos (20261EC001):");
        imprimirHistorico(dao.listarPerrengues("20261EC001"));

        System.out.println("  >> Perrengues de Ana Vilela (20261EC002):");
        imprimirHistorico(dao.listarPerrengues("20261EC002"));
    }

    /**
     * Imprime no console a lista de alunos com suas principais informações formatadas
     * (matrícula, nome, status, porcentagem de energia e saldo em reais).
     *
     * @param turma lista de instâncias de {@link Aluno} a serem exibidas
     */
    private static void imprimirTurma(List<Aluno> turma) {
        for (Aluno e : turma) {
            System.out.println("  • " + e.getMatricula() + " | " + e.getNome() +
                               " [" + e.getStatus() + "] | Energia: " + e.getEnergia() +
                               "% | Saldo: R$ " + e.getDinheiro());
        }
    }

    /**
     * Imprime no console os perrengues registrados no histórico de um aluno.
     * Caso a lista esteja vazia, exibe uma mensagem informativa.
     *
     * @param historico lista de instâncias de {@link HistoricoPerrengues} a serem exibidas
     */
    private static void imprimirHistorico(List<HistoricoPerrengues> historico) {
        if (historico.isEmpty()) {
            System.out.println("    Nenhum perrengue registrado.");
            return;
        }
        for (HistoricoPerrengues h : historico) {
            System.out.println("    • " + h);
        }
    }
}