package domain;

import java.util.ArrayList;
import java.util.List;

public class GenericNode {
    public String element;
    public GenericNode parent;
    public List<GenericNode> children;

    public GenericNode(String element) {
        this.element = element;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public void addChild(GenericNode child) {
        this.children.add(child);
        child.parent = this;
    }

    public void removeChild(GenericNode child) {
        this.children.remove(child);
        child.parent = null;
    }
}