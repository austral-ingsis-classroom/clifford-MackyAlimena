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

  public Optional<FileSystemNode> getChild(String path) {
    int nextDir = path.indexOf("/");
    // No more directories to go
    if (nextDir == -1) {
      return getFromName(path);
    }
    String next = path.substring(0, nextDir);
    String remaining = path.substring(nextDir + 1);
    if (remaining.isEmpty()) {
      return getFromName(next);
    } else {
      Optional<FileSystemNode> child = getFromName(next);
      if (child.isEmpty() || !child.get().isDirectory()) {
        return Optional.empty();
      }
      DirectoryNode child1 = (DirectoryNode) child.get();
      return child1.getChild(remaining);
    }
  }

  private Optional<FileSystemNode> getFromName(String path) {
    for (FileSystemNode node : children) {
      if (node.name().equals(path)) {
        return Optional.of(node);
      }
    }
    return Optional.empty();
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
