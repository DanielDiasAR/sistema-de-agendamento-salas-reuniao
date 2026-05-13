package br.com.sasr.view;

import br.com.sasr.dao.SalaDao;
import br.com.sasr.model.Sala;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SalaView {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            SalaDao dao = new SalaDao();
            int opcao = -1;
            while (opcao != 0) {
                System.out.println("\n--- GERENCIAMENTO DE SALAS ---");
                System.out.println("1 - Cadastrar Sala");
                System.out.println("2 - Alterar Dados da Sala");
                System.out.println("3 - Listar Todas as Salas");
                System.out.println("4 - Pesquisar Sala por Id");
                System.out.println("5 - Remover Sala por Id");
                System.out.println("0 - Sair");
                System.out.println("Escolha uma Opção: ");

                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.println("\n--- Nova Sala ---");
                        System.out.println("Número da Sala: ");
                        String numeroSala = sc.nextLine();
                        System.out.println("Capacidade da Sala: ");
                        int capacidade = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Recursos da Sala: ");
                        String recursos = sc.nextLine();

                        Sala novaSala = new Sala();
                        novaSala.setNumeroSala(numeroSala);
                        novaSala.setCapacidade(capacidade);
                        novaSala.setRecursos(recursos);
                        dao.insert(novaSala);
                        System.out.println("Sala Cadastrada com sucesso!");
                        break;

                        case 2:
                            System.out.println("\n--- Alterar Dados da Sala ---");
                            System.out.println("Id da Sala que deseja alterar: ");
                            long idSala = sc.nextLong();
                            sc.nextLine();
                            System.out.println("Digite o Novo Número da Sala: ");
                            String numeroSala2 = sc.nextLine();
                            System.out.println("Digite a Nov Capacidade da Sala: ");
                            int capacidade2 = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Digite o Novo Recurso da Sala: ");
                            String recurso2 = sc.nextLine();

                            Sala salaAtual = new Sala();
                            salaAtual.setId(idSala);
                            salaAtual.setNumeroSala(numeroSala2);
                            salaAtual.setCapacidade(capacidade2);
                            salaAtual.setRecursos(recurso2);

                            dao.update(salaAtual);
                            System.out.println("Dados da Sala Atualizados com sucesso!");
                            break;

                        case 3:
                            System.out.println("\n--- Listando Todas as Salas ---");

                            List<Sala> lista = dao.getAll();
                            if (lista.isEmpty()) {
                                System.out.println("Nenhum Sala encontrada!");
                            } else {
                                for (Sala sala : lista) {
                                    System.out.println("\n-------------------");
                                    System.out.println("ID: " + sala.getId());
                                    System.out.println("Número da Sala: " + sala.getNumeroSala());
                                    System.out.println("Capacidade da Sala: " + sala.getCapacidade());
                                    System.out.println("Recursos da Sala: " + sala.getRecursos());
                                }
                            }
                            break;

                        case 4:
                            System.out.println("\n--- Pesquisar por ID ---");
                            System.out.println("Id da Sala que deseja pesquisar: ");
                            long  idSalaPesquisa = sc.nextLong();
                            sc.nextLine();

                            Sala salaEncontrada = dao.searchById(idSalaPesquisa);

                            if (salaEncontrada != null) {
                                System.out.println("\n--- Registro Localizado ---");
                                System.out.println("ID: " + salaEncontrada.getId());
                                System.out.println("Número da Sala: " + salaEncontrada.getNumeroSala());
                                System.out.println("Capacidade da Sala: " + salaEncontrada.getCapacidade());
                                System.out.println("Recursos da Sala: " + salaEncontrada.getRecursos());
                            } else  {
                                System.out.println("[!] Sala com ID: " +  idSalaPesquisa + " não encontrada [!]");
                            }
                            break;

                            case 5:
                                System.out.println("\n--- Remoção de Cadastros da Sala ---");
                                System.out.println("Id da Sala que deseja remover: ");
                                long idSalaRemover = sc.nextLong();
                                dao.delete(idSalaRemover);
                                System.out.println("Sala removida com sucesso!");
                                break;

                            case 0:
                                System.out.println("Encerrando módulo de Salas...");
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
