package ui;

import service.TournamentService;
import domain.Tournament;
import java.util.LinkedList;
import java.util.Scanner;

public class TournamentApp {
    private final TournamentService service = new TournamentService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║   TORNEIO ELIMINATÓRIO - ÁRVORE BINÁRIA   ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("1. Criar torneio");
            System.out.println("2. Registrar vencedor");
            System.out.println("3. Percursos");
            System.out.println("4. Consultas estruturais");
            System.out.println("5. LCA");
            System.out.println("6. Caminho até a final");
            System.out.println("7. Visualizar árvore");
            System.out.println("8. Listar participantes");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> createTournament();
                case 2 -> registerWinner();
                case 3 -> showTraversals();
                case 4 -> showStructure();
                case 5 -> showLCA();
                case 6 -> showPath();
                case 7 -> service.getTournament().printTree();
                case 8 -> service.getTournament().listPlayers();
                case 0 -> {
                    System.out.println("\nEncerrando. Ate logo!");
                    return;
                }
                default -> System.out.println("Opcao invalida!");
            }
        }
    }

    private void createTournament() {
        System.out.print("\nQuantos participantes (8, 16 ou 32)? ");
        int n = scanner.nextInt();
        scanner.nextLine();
        if (n != 8 && n != 16 && n != 32) {
            System.out.println("Número invalido!");
            return;
        }
        LinkedList<String> participants = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            System.out.print("Participante " + (i + 1) + ": ");
            participants.add(scanner.nextLine());
        }
        service.createTournament(participants);
        System.out.println("Torneio criado!");
    }

    private void registerWinner() {
        System.out.print("\nNome do vencedor: ");
        String name = scanner.nextLine();
        if (service.registerWinner(name))
            System.out.println("Vencedor registrado!");
        else
            System.out.println("Jogador não encontrado!");
    }

    private void showTraversals() {
        Tournament t = service.getTournament();
        if (t.isEmpty()) {
            System.out.println("Crie um torneio primeiro!");
            return;
        }
        System.out.println("\nPré-ordem:");
        System.out.println(t.positionsPre());
        System.out.println("\nPós-ordem:");
        System.out.println(t.positionsPos());
        System.out.println("\nLargura:");
        System.out.println(t.positionsWidth());
    }

    private void showStructure() {
        Tournament t = service.getTournament();
        System.out.println("\nAltura: " + t.height());
        System.out.println("Folhas: " + t.countLeaves());
        System.out.println("Nós internos: " + t.countInternalNodes());
        System.out.println("Total de nós: " + t.size());
    }

    private void showLCA() {
        Tournament t = service.getTournament();
        System.out.print("\nJogador 1: ");
        String p1 = scanner.nextLine();
        System.out.print("Jogador 2: ");
        String p2 = scanner.nextLine();
        String lca = t.findLCA(p1, p2);
        System.out.println(lca != null ? "Primeira partida: " + lca : "Jogador nao encontrado!");
    }

    private void showPath() {
        Tournament t = service.getTournament();
        System.out.print("\nJogador: ");
        String p = scanner.nextLine();
        System.out.println("Caminho ate a final: " + t.pathToRoot(p));
    }
}
