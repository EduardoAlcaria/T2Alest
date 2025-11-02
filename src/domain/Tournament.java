package domain;

import java.util.LinkedList;

public class Tournament {
    private Node root;
    private int count;
    private int matchCounter;

    public Tournament() {
        root = null;
        count = 0;
    }

    public void clear() {
        root = null;
        count = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return count;
    }

    private Node searchNodeRef(String element, Node n) {
        if (n == null) return null;
        if (element != null && element.equals(n.element)) return n;

        Node left = searchNodeRef(element, n.left);
        if (left != null) return left;
        return searchNodeRef(element, n.right);
    }

    public boolean contains(String element) {
        return searchNodeRef(element, root) != null;
    }


    public void addRoot(String element) {
        if (root != null) throw new IllegalStateException("Arvore ja possui raiz");
        root = new Node(element);
        count++;
    }

    public boolean addLeft(String element, String father) {
        Node nodeFather = searchNodeRef(father, root);
        if (nodeFather == null || nodeFather.left != null) return false;

        Node newNode = new Node(element);
        newNode.father = nodeFather;
        nodeFather.left = newNode;
        count++;
        return true;
    }

    public boolean addRight(String element, String father) {
        Node nodeFather = searchNodeRef(father, root);
        if (nodeFather == null || nodeFather.right != null) return false;

        Node newNode = new Node(element);
        newNode.father = nodeFather;
        nodeFather.right = newNode;
        count++;
        return true;
    }

    public boolean set(String oldElement, String newElement) {
        Node node = searchNodeRef(oldElement, root);
        if (node == null) return false;
        node.element = newElement;
        return true;
    }


    public LinkedList<String> positionsPre() {
        LinkedList<String> list = new LinkedList<>();
        positionsPreAux(root, list);
        return list;
    }

    private void positionsPreAux(Node n, LinkedList<String> list) {
        if (n != null) {
            list.add(n.element);
            positionsPreAux(n.left, list);
            positionsPreAux(n.right, list);
        }
    }

    public LinkedList<String> positionsPos() {
        LinkedList<String> list = new LinkedList<>();
        positionsPosAux(root, list);
        return list;
    }

    private void positionsPosAux(Node n, LinkedList<String> list) {
        if (n != null) {
            positionsPosAux(n.left, list);
            positionsPosAux(n.right, list);
            list.add(n.element);
        }
    }

    public LinkedList<String> positionsWidth() {
        LinkedList<String> list = new LinkedList<>();
        if (root != null) {
            QueueDOM<Node> queue = new QueueDOM<>();
            queue.enqueue(root);
            while (!queue.isEmpty()) {
                Node n = queue.dequeue();
                list.add(n.element);
                if (n.left != null) queue.enqueue(n.left);
                if (n.right != null) queue.enqueue(n.right);
            }
        }
        return list;
    }


    public int height() {
        return heightAux(root);
    }

    private int heightAux(Node n) {
        if (n == null) return -1;
        int leftHeight = heightAux(n.left);
        int rightHeight = heightAux(n.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    public int countLeaves() {
        return countLeavesAux(root);
    }

    private int countLeavesAux(Node n) {
        if (n == null) return 0;
        if (n.left == null && n.right == null) return 1;
        return countLeavesAux(n.left) + countLeavesAux(n.right);
    }

    public int countInternalNodes() {
        return count - countLeaves();
    }

    public String findLCA(String elem1, String elem2) {
        Node node1 = searchNodeRef(elem1, root);
        Node node2 = searchNodeRef(elem2, root);
        if (node1 == null || node2 == null) return null;

        Node lca = findLCAAux(root, node1, node2);
        return lca != null ? lca.element : null;
    }

    private Node findLCAAux(Node n, Node n1, Node n2) {
        if (n == null) return null;
        if (n == n1 || n == n2) return n;

        Node left = findLCAAux(n.left, n1, n2);
        Node right = findLCAAux(n.right, n1, n2);

        if (left != null && right != null) return n;
        return left != null ? left : right;
    }

    public LinkedList<String> pathToRoot(String element) {
        Node node = searchNodeRef(element, root);
        LinkedList<String> path = new LinkedList<>();
        if (node == null) return path;

        while (node != null) {
            path.addFirst(node.element);
            node = node.father;
        }
        return path;
    }

    public String getFather(String element) {
        Node node = searchNodeRef(element, root);
        if (node == null || node.father == null) return null;
        return node.father.element;
    }

    public void buildTournament(LinkedList<String> participants) {
        clear();
        matchCounter = 1;
        if (participants.isEmpty()) return;

        root = buildSubtree(participants);
        root.element = "Final";
        count = countNodesFromRoot(root);
    }

    private Node buildSubtree(LinkedList<String> participants) {
        if (participants.size() == 1) return new Node(participants.get(0));

        int mid = participants.size() / 2;
        LinkedList<String> leftParticipants = new LinkedList<>(participants.subList(0, mid));
        LinkedList<String> rightParticipants = new LinkedList<>(participants.subList(mid, participants.size()));

        Node leftSubtree = buildSubtree(leftParticipants);
        Node rightSubtree = buildSubtree(rightParticipants);

        Node match = new Node("Partida_" + matchCounter++);
        match.left = leftSubtree;
        match.right = rightSubtree;
        leftSubtree.father = match;
        rightSubtree.father = match;

        return match;
    }

    private int countNodesFromRoot(Node n) {
        if (n == null) return 0;
        return 1 + countNodesFromRoot(n.left) + countNodesFromRoot(n.right);
    }

    public boolean registerWinner(String winner) {
        if (!contains(winner)) return false;

        String father = getFather(winner);
        if (father == null) return true;

        set(father, winner);
        return true;
    }

    public void listPlayers() {
        LinkedList<String> leaves = new LinkedList<>();
        collectLeaves(root, leaves);
        System.out.println("\n=== PARTICIPANTES ===");
        for (int i = 0; i < leaves.size(); i++) {
            System.out.println((i + 1) + ". " + leaves.get(i));
        }
    }

    private void collectLeaves(Node n, LinkedList<String> leaves) {
        if (n == null) return;
        if (n.left == null && n.right == null) leaves.add(n.element);
        else {
            collectLeaves(n.left, leaves);
            collectLeaves(n.right, leaves);
        }
    }

    public void printTree() {
        System.out.println("\n=== ESTRUTURA DO TORNEIO ===");
        if (root == null) {
            System.out.println("Arvore vazia!");
            return;
        }
        printTreeAux(root, "", true);
    }

    private void printTreeAux(Node n, String prefix, boolean isTail) {
        if (n == null) return;
        System.out.println(prefix + (isTail ? "└── " : "├── ") + n.element);
        if (n.left != null || n.right != null) {
            if (n.left != null) printTreeAux(n.left, prefix + (isTail ? "    " : "│   "), n.right == null);
            if (n.right != null) printTreeAux(n.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
