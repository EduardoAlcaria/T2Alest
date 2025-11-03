package domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GenericTree {
    private GenericNode root;

    public GenericTree() {
        this.root = null;
    }

    public GenericTree(String rootElement) {
        this.root = new GenericNode(rootElement);
    }

    public GenericNode getRoot() {
        return root;
    }

    public void setRoot(GenericNode root) {
        this.root = root;
    }

    public GenericNode findNode(String element) {
        return findNodeRecursive(root, element);
    }

    private GenericNode findNodeRecursive(GenericNode node, String element) {
        if (node == null) return null;
        if (node.element.equals(element)) return node;

        for (GenericNode child : node.children) {
            GenericNode found = findNodeRecursive(child, element);
            if (found != null) return found;
        }
        return null;
    }

    public boolean insertItem(String parentElement, String newElement) {
        GenericNode parent = findNode(parentElement);
        if (parent == null) return false;

        GenericNode newNode = new GenericNode(newElement);
        parent.addChild(newNode);
        return true;
    }

    public boolean moveSubtree(String itemToMove, String newParentElement) {
        GenericNode nodeToMove = findNode(itemToMove);
        GenericNode newParent = findNode(newParentElement);

        if (nodeToMove == null || newParent == null) return false;
        if (nodeToMove == root) return false;
        if (isAncestor(nodeToMove, newParent)) return false;

        GenericNode oldParent = nodeToMove.parent;
        if (oldParent != null) {
            oldParent.removeChild(nodeToMove);
        }

        newParent.addChild(nodeToMove);
        return true;
    }

    private boolean isAncestor(GenericNode ancestor, GenericNode descendant) {
        GenericNode current = descendant;
        while (current != null) {
            if (current == ancestor) return true;
            current = current.parent;
        }
        return false;
    }

    public boolean removeSubtree(String element) {
        if (element.equals(root.element)) {
            root = null;
            return true;
        }

        GenericNode node = findNode(element);
        if (node == null) return false;

        GenericNode parent = node.parent;
        if (parent != null) {
            parent.removeChild(node);
        }
        return true;
    }

    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(GenericNode node) {
        if (node == null) return -1;
        if (node.children.isEmpty()) return 0;

        int maxHeight = -1;
        for (GenericNode child : node.children) {
            int childHeight = heightRecursive(child);
            if (childHeight > maxHeight) {
                maxHeight = childHeight;
            }
        }
        return maxHeight + 1;
    }

    public int maxDegree() {
        return maxDegreeRecursive(root);
    }

    private int maxDegreeRecursive(GenericNode node) {
        if (node == null) return 0;

        int max = node.children.size();
        for (GenericNode child : node.children) {
            int childMax = maxDegreeRecursive(child);
            if (childMax > max) {
                max = childMax;
            }
        }
        return max;
    }

    public int countLeaves() {
        return countLeavesRecursive(root);
    }

    private int countLeavesRecursive(GenericNode node) {
        if (node == null) return 0;
        if (node.children.isEmpty()) return 1;

        int count = 0;
        for (GenericNode child : node.children) {
            count += countLeavesRecursive(child);
        }
        return count;
    }

    public int countInternalNodes() {
        return countInternalNodesRecursive(root);
    }

    private int countInternalNodesRecursive(GenericNode node) {
        if (node == null) return 0;
        if (node.children.isEmpty()) return 0;

        int count = 1;
        for (GenericNode child : node.children) {
            count += countInternalNodesRecursive(child);
        }
        return count;
    }

    public List<String> preOrder() {
        List<String> result = new ArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }

    private void preOrderRecursive(GenericNode node, List<String> result) {
        if (node == null) return;
        result.add(node.element);
        for (GenericNode child : node.children) {
            preOrderRecursive(child, result);
        }
    }

    public List<String> postOrder() {
        List<String> result = new ArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }

    private void postOrderRecursive(GenericNode node, List<String> result) {
        if (node == null) return;
        for (GenericNode child : node.children) {
            postOrderRecursive(child, result);
        }
        result.add(node.element);
    }

    public List<String> levelOrder() {
        List<String> result = new ArrayList<>();
        if (root == null) return result;

        Queue<GenericNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            GenericNode current = queue.poll();
            result.add(current.element);

            for (GenericNode child : current.children) {
                queue.add(child);
            }
        }

        return result;
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


    public String lca(String element1, String element2) {
        GenericNode node1 = findNode(element1);
        GenericNode node2 = findNode(element2);

        if (node1 == null || node2 == null) return null;

        List<GenericNode> path1 = getPathFromRoot(node1);
        List<GenericNode> path2 = getPathFromRoot(node2);

        GenericNode lca = null;
        int minSize = Math.min(path1.size(), path2.size());

        for (int i = 0; i < minSize; i++) {
            if (path1.get(i) == path2.get(i)) {
                lca = path1.get(i);
            } else {
                break;
            }
        }

        return lca != null ? lca.element : null;
    }

    private List<GenericNode> getPathFromRoot(GenericNode node) {
        List<GenericNode> path = new ArrayList<>();
        GenericNode current = node;

        while (current != null) {
            path.add(0, current);
            current = current.parent;
        }

        return path;
    }

    public List<String> path(String element1, String element2) {
        GenericNode node1 = findNode(element1);
        GenericNode node2 = findNode(element2);

        if (node1 == null || node2 == null) return null;

        List<GenericNode> path1 = getPathFromRoot(node1);
        List<GenericNode> path2 = getPathFromRoot(node2);

        int lcaIndex = -1;
        int minSize = Math.min(path1.size(), path2.size());

        for (int i = 0; i < minSize; i++) {
            if (path1.get(i) == path2.get(i)) {
                lcaIndex = i;
            } else {
                break;
            }
        }

        List<String> result = new ArrayList<>();

        for (int i = path1.size() - 1; i > lcaIndex; i--) {
            result.add(path1.get(i).element);
        }

        for (int i = lcaIndex; i < path2.size(); i++) {
            result.add(path2.get(i).element);
        }

        return result;
    }

    public boolean checkConsistency() {
        if (root == null) return true;

        if (!hasSingleRoot()) return false;
        if (hasCycle()) return false;

        return true;
    }

    private boolean hasSingleRoot() {
        return root.parent == null;
    }

    private boolean hasCycle() {
        return hasCycleRecursive(root, new ArrayList<>());
    }

    private boolean hasCycleRecursive(GenericNode node, List<GenericNode> visited) {
        if (node == null) return false;

        if (visited.contains(node)) return true;

        visited.add(node);

        for (GenericNode child : node.children) {
            if (hasCycleRecursive(child, new ArrayList<>(visited))) {
                return true;
            }
        }

        return false;
    }
}