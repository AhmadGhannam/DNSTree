import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class DomainNode {
    private String name;
    private Map<String, DomainNode> children;

    public DomainNode(String name) {
        this.name = name;
        this.children = new HashMap<>();
    }

    public DomainNode addChild(String name) {
        return children.computeIfAbsent(name, DomainNode::new);
    }

    public DomainNode getChild(String name) {
        return children.get(name);
    }

    public boolean removeChild(String name) {
        return children.remove(name) != null;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public String getName() {
        return name;
    }

    public Map<String, DomainNode> getChildren() {
        return children;
    }
}

