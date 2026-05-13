package br.com.sasr.view;

import br.com.sasr.dao.*;
import br.com.sasr.model.*;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AgendamentoView {
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("DD/MM/YYYY");
    private static DateTimeFormatter hf = DateTimeFormatter.ofPattern("HH:MM");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            AgendamentoDao dao = new AgendamentoDao();
            int opcao = -1;

            while (opcao != 0) {
                System.out.println("\n--- GERENCIAMENTO DE AGENDAMENTOS ---");
                System.out.println("1 - Novo Agendamento");
                System.out.println("2 - Alterar Agendamento");
                System.out.println("3 - Listar Todos");
                System.out.println("4 - Pesquisar por ID");
                System.out.println("5 - Cancelar Agendamento");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1: realizarAgendamento(sc, dao, null); break;
                    case 2:
                        System.out.print("ID do Agendamento para alterar: ");
                        long idAlt = sc.nextLong(); sc.nextLine();
                        realizarAgendamento(sc, dao, idAlt);
                        break;
                    case 3:
                        List<Agendamento> lista = dao.getAll();
                        if (lista.isEmpty()) System.out.println("Nenhum agendamento.");
                        else lista.forEach(AgendamentoView::exibirAgendamento);
                        break;
                    case 4:
                        System.out.print("ID para pesquisa: ");
                        Agendamento buscado = dao.searchById(sc.nextLong());
                        if (buscado != null) exibirAgendamento(buscado);
                        else System.out.println("Não encontrado.");
                        break;
                    case 5:
                        System.out.print("ID para cancelar: ");
                        dao.delete(sc.nextLong());
                        System.out.println("Agendamento cancelado.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void realizarAgendamento(Scanner sc, AgendamentoDao dao, Long idExistente) throws SQLException {
        try {
            System.out.print("Título: "); String titulo = sc.nextLine();
            System.out.print("Data (DD/MM/YYYY): "); LocalDate data = LocalDate.parse(sc.nextLine(), df);
            System.out.print("Início (HH:MM): "); LocalTime inicio = LocalTime.parse(sc.nextLine(), hf);
            System.out.print("Fim (HH:MM): "); LocalTime fim = LocalTime.parse(sc.nextLine(), hf);

            if (inicio.isBefore(LocalTime.of(8, 0)) || fim.isAfter(LocalTime.of(18, 0))) {
                System.out.println("[!] Erro: Somente em horário comercial (08h às 18h).");
                return;
            }
            if (Duration.between(inicio, fim).toHours() > 4) {
                System.out.println("[!] Erro: Duração máxima de 4 horas permitida.");
                return;
            }

            System.out.print("ID da Sala: "); long idS = sc.nextLong();
            System.out.print("ID do Colaborador: "); long idC = sc.nextLong(); sc.nextLine();

            Sala sala = new SalaDao().searchById(idS);
            Colaborador col = new ColaboradorDao().searchById(idC);

            if (sala == null || col == null) {
                System.out.println("[!] Erro: Sala ou Colaborador inválidos.");
                return;
            }

            Agendamento conflito = dao.buscarConflito(idS, java.sql.Date.valueOf(data),
                    java.sql.Timestamp.valueOf(data.atTime(inicio)),
                    java.sql.Timestamp.valueOf(data.atTime(fim)), idExistente);

            if (conflito != null) {
                System.out.printf("[!] A sala %s já está reservada para o horário das %s às %s no dia %s.\n",
                        sala.getNumeroSala(), conflito.getHoraInicio(), conflito.getHoraFim(), data.format(df));
            } else {
                Agendamento a = new Agendamento();
                if (idExistente != null) a.setId(idExistente);
                a.setTitulo(titulo); a.setData(data); a.setHoraInicio(inicio); a.setHoraFim(fim);
                a.setSala(sala); a.setColaborador(col);

                if (idExistente == null) dao.insert(a); else dao.update(a);
                System.out.println("Agendamento registrado com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("\n[DEBUG] Ocorreu um erro técnico:");
            e.printStackTrace();
        }
    }

    private static void exibirAgendamento(Agendamento a) {
        System.out.println("\n-------------------------------------------");
        System.out.printf("ID: %d | Título: %s\n", a.getId(), a.getTitulo());
        System.out.printf("Data: %s | Horário: %s - %s\n", a.getData().format(df), a.getHoraInicio(), a.getHoraFim());
        System.out.printf("Sala: %s | Colaborador: %s\n", a.getSala().getNumeroSala(), a.getColaborador().getNome());
    }
}