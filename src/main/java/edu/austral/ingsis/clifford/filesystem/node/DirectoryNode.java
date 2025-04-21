package edu.austral.ingsis.clifford.filesystem.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DirectoryNode implements FileSystemNode {
    private final String name;
    private final DirectoryNode parent;
    private final List<FileSystemNode> children;

    public DirectoryNode(String name, DirectoryNode parent) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public DirectoryNode getParent() {
        return parent;
    }

    public void addChild(FileSystemNode child) {
        children.add(child);
    }
    public boolean hasChild(String child) {
        return children.stream().anyMatch(node -> node.name().equals(child));
    }

    public void removeChild(String child) {
        children.removeIf(node -> node.name().equals(child));
    }

    public Optional<FileSystemNode> getChild(String name) {
        return children.stream()
                .filter(child -> child.name().equals(name))
                .findFirst();
    }

    public List<FileSystemNode> getChildren() {
        return children;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }
}
