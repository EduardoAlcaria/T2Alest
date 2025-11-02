package domain;

public class Node {
    public Node father;
    public String element;
    public Node left;
    public Node right;

    public Node(String element) {
        this.element = element;
        this.father = null;
        this.left = null;
        this.right = null;
    }
}
