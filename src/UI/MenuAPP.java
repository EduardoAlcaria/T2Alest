package UI;

import domain.GenericNode;
import domain.GenericTree;


import java.util.List;
import java.util.Scanner;

public class MenuAPP{
    private GenericTree menu;
    private Scanner scanner;

    public MenuAPP() {
        this.menu = new GenericTree("App");
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;


        while (running) {
            showMainMenu();
            int option = readInt();


            switch (option) {

                case 1 -> insertItem();
                case 2 -> moveSubtree();
                case 3 -> removeSubtree();
                case 4 -> showQueries();
                case 5 -> showTraversals();
                case 6 -> findLCA();
                case 7 -> findPath();
                case 8 -> checkConsistency();
                case 9 -> showTree();
                case 0 -> {
                    running = false;
                    System.out.println("Encerrando...");
                }

                default -> System.out.println("Opcao invalida!");
            }
        }

    }

    private void showMainMenu() {
        System.out.println("\n=== MENU DE APLICATIVO ===");
        System.out.println("1. Inserir item");
        System.out.println("2. Mover subarvore");
        System.out.println("3. Remover subarvore");
        System.out.println("4. Consultas estruturais");
        System.out.println("5. Percursos");
        System.out.println("6. LCA (item comum mais baixo)");
        System.out.println("7. Caminho entre itens");
        System.out.println("8. Verificar consistencia");
        System.out.println("9. Mostrar arvore");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }


    private void insertItem() {

        System.out.print("Nome do item pai (ou deixe vazio para raiz): ");
        String parent = scanner.nextLine().trim();
        System.out.print("Nome do novo item: ");
        String newItem = scanner.nextLine().trim();


        if (menu.getRoot() == null) {
            menu.setRoot(new GenericNode(newItem));
            System.out.println("Raiz criada com sucesso: " + newItem);
            return;
        }


        if (parent.isEmpty()) {
            parent = menu.getRoot().element;
        }

        if (menu.insertItem(parent, newItem)) {
            System.out.println("Item inserido com sucesso!");
        } else {
            System.out.println("Item pai nao encontrado! Confira o nome exatamente.");
            System.out.println("Itens atuais: " + menu.levelOrder());
        }
    }

    private void moveSubtree() {
        System.out.print("Nome do item a mover: ");
        String item = scanner.nextLine();
        System.out.print("Nome do novo pai: ");
        String newParent = scanner.nextLine();

        if (menu.moveSubtree(item, newParent)) {
            System.out.println("Subarvore movida com sucesso!");
        } else {
            System.out.println("Erro ao mover subarvore!");
        }
    }

    private void removeSubtree() {
        System.out.print("Nome do item a remover: ");
        String item = scanner.nextLine();

        if (menu.removeSubtree(item)) {
            System.out.println("Subarvore removida com sucesso!");
        } else {
            System.out.println("Item nao encontrado!");
        }
    }

    private void showQueries() {
        System.out.println("\n--- Consultas Estruturais ---");
        System.out.println("Altura: " + menu.height());
        System.out.println("Grau maximo: " + menu.maxDegree());
        System.out.println("Numero de folhas: " + menu.countLeaves());
        System.out.println("Numero de nos internos: " + menu.countInternalNodes());
    }

    private void showTraversals() {
        System.out.println("\n--- Percursos ---");

        System.out.println("Pre-ordem: " + menu.preOrder());
        System.out.println("Pos-ordem: " + menu.postOrder());
        System.out.println("Largura: " + menu.levelOrder());
    }

    private void findLCA() {
        System.out.print("Primeiro item: ");
        String item1 = scanner.nextLine();
        System.out.print("Segundo item: ");
        String item2 = scanner.nextLine();

        String lca = menu.lca(item1, item2);
        if (lca != null) {
            System.out.println("Item comum mais baixo: " + lca);
        } else {
            System.out.println("Um ou ambos os itens nao encontrados!");
        }
    }

    private void findPath() {
        System.out.print("Item de origem: ");
        String item1 = scanner.nextLine();
        System.out.print("Item de destino: ");
        String item2 = scanner.nextLine();

        List<String> path = menu.path(item1, item2);
        if (path != null) {
            System.out.println("Caminho: " + path);
        } else {
            System.out.println("Um ou ambos os itens nao encontrados!");
        }
    }

    private void checkConsistency() {
        if (menu.checkConsistency()) {
            System.out.println("Arvore consistente: raiz unica e sem ciclos!");
        } else {
            System.out.println("Arvore inconsistente!");
        }
    }

    private void showTree() {
        System.out.println("\n--- Estrutura da Arvore ---");
        if (menu.getRoot() == null) {
            System.out.println("A árvore está vazia.");
            return;
        }
        System.out.println(treeWithParentheses(menu.getRoot()));
    }

    private String treeWithParentheses(GenericNode node) {
        if (node == null) return "";
        if (node.children.isEmpty()) return node.element;

        StringBuilder sb = new StringBuilder();
        sb.append(node.element).append("(");
        for (int i = 0; i < node.children.size(); i++) {
            sb.append(treeWithParentheses(node.children.get(i)));
            if (i < node.children.size() - 1) sb.append(" ");
        }
        sb.append(")");
        return sb.toString();
    }

    private int readInt() {

        while (true) {
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Numero invalido! Tente novamente: ");
            }
        }
    }
}