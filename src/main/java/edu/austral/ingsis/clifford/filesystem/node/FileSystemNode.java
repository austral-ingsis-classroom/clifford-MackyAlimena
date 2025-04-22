package edu.austral.ingsis.clifford.filesystem.node;

public interface FileSystemNode extends Comparable<FileSystemNode> {
  String name();

  boolean isDirectory();

  default int compareTo(FileSystemNode o) {
    return name().compareTo(o.name());
  }
}
