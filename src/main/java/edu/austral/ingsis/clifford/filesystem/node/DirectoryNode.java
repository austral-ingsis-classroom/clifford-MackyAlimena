package edu.austral.ingsis.clifford.filesystem.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DirectoryNode implements FileSystemNode {
  private final String name;
  private final DirectoryNode parent;
  private final List<FileSystemNode> children;

  public DirectoryNode(String name, DirectoryNode parent, List<FileSystemNode> children) {
    this.name = name;
    this.parent = parent;
    this.children = Collections.unmodifiableList(children);
  }

  public String getName() {
    return name;
  }

  public DirectoryNode getParent() {
    return parent;
  }

  public DirectoryNode addChild(FileSystemNode child) {
    FileSystemNode updatedChild;
    if (child instanceof DirectoryNode) {
      DirectoryNode dirChild = (DirectoryNode) child;
      updatedChild = new DirectoryNode(dirChild.name(), this, dirChild.getChildren());
    } else {
      updatedChild = child;
    }

    List<FileSystemNode> newChildren = new ArrayList<>(children);
    newChildren.add(updatedChild);

    return new DirectoryNode(this.name, this.parent, newChildren);
  }

  public DirectoryNode removeChild(String childName) {
    List<FileSystemNode> newChildren =
        children.stream()
            .filter(node -> !node.name().equals(childName))
            .collect(Collectors.toList());

    return new DirectoryNode(this.name, this.parent, newChildren);
  }

  public boolean hasChild(String child) {
    return children.stream().anyMatch(node -> node.name().equals(child));
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
    return children.stream().filter(node -> node.name().equals(path)).findFirst();
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
