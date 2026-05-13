package br.com.sasr.view;

import br.com.sasr.dao.ColaboradorDao;
import br.com.sasr.model.Colaborador;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ColaboradorView {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            ColaboradorDao dao = new ColaboradorDao();
            int opcao = -1;

            while (opcao != 0) {
                System.out.println("\n--- GERENCIAMENTO DE COLABORADORES ---");
                System.out.println("1 - Cadastrar Colaborador");
                System.out.println("2 - Alterar Dados do Colaborador");
                System.out.println("3 - Listar Todos os Colaboradores");
                System.out.println("4 - Pesquisar Colaborador por Id");
                System.out.println("5 - Remover Colaborador por Id");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma Opção: ");

                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.println("\n--- Novo Colaborador ---");
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Departamento: ");
                        String depto = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        Colaborador novoColab = new Colaborador();
                        novoColab.setNome(nome);
                        novoColab.setDepartamento(depto);
                        novoColab.setEmail(email);

                        dao.insert(novoColab);
                        System.out.println("Colaborador cadastrado com sucesso!");
                        break;

                    case 2:
                        System.out.println("\n--- Alterar Dados do Colaborador ---");
                        System.out.print("Id do Colaborador que deseja alterar: ");
                        long idAlterar = sc.nextLong();
                        sc.nextLine();

                        System.out.print("Digite o Novo Nome: ");
                        String novoNome = sc.nextLine();
                        System.out.print("Digite o Novo Departamento: ");
                        String novoDepto = sc.nextLine();
                        System.out.print("Digite o Novo Email: ");
                        String novoEmail = sc.nextLine();

                        Colaborador colabAtual = new Colaborador();
                        colabAtual.setId(idAlterar);
                        colabAtual.setNome(novoNome);
                        colabAtual.setDepartamento(novoDepto);
                        colabAtual.setEmail(novoEmail);

                        dao.update(colabAtual);
                        System.out.println("Dados atualizados com sucesso!");
                        break;

                    case 3:
                        System.out.println("\n--- Listando Todos os Colaboradores ---");
                        List<Colaborador> lista = dao.getAll();

                        if (lista.isEmpty()) {
                            System.out.println("Nenhum colaborador encontrado!");
                        } else {
                            for (Colaborador c : lista) {
                                System.out.println("\n-------------------");
                                System.out.println("ID: " + c.getId());
                                System.out.println("Nome: " + c.getNome());
                                System.out.println("Departamento: " + c.getDepartamento());
                                System.out.println("Email: " + c.getEmail());
                            }
                        }
                        break;

                    case 4:
                        System.out.println("\n--- Pesquisar por ID ---");
                        System.out.print("Id do Colaborador que deseja pesquisar: ");
                        long idPesquisa = sc.nextLong();
                        sc.nextLine();

                        Colaborador colabEncontrado = dao.searchById(idPesquisa);

                        if (colabEncontrado != null) {
                            System.out.println("\n--- Registro Localizado ---");
                            System.out.println("ID: " + colabEncontrado.getId());
                            System.out.println("Nome: " + colabEncontrado.getNome());
                            System.out.println("Departamento: " + colabEncontrado.getDepartamento());
                            System.out.println("Email: " + colabEncontrado.getEmail());
                        } else {
                            System.out.println("[!] Colaborador com ID " + idPesquisa + " não encontrado [!]");
                        }
                        break;

                    case 5:
                        System.out.println("\n--- Remoção de Colaborador ---");
                        System.out.print("Id do Colaborador que deseja remover: ");
                        long idRemover = sc.nextLong();

                        dao.delete(idRemover);
                        System.out.println("Colaborador removido com sucesso!");
                        break;

                    case 0:
                        System.out.println("Encerrando módulo de Colaboradores...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro de Banco de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}