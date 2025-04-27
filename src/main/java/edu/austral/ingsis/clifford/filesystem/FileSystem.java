package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSystem {
  private final DirectoryNode root = new DirectoryNode("/", null);

  private DirectoryNode current;

  public FileSystem() {
    this.current = root;
  }

  public DirectoryNode getCurrent() {
    return current;
  }

  public DirectoryNode getRoot() {
    return root;
  }

  public void setCurrent(DirectoryNode directoryNode) {
    if (directoryNode != null) {
      this.current = directoryNode;
    }
  }
}

// Parseo de comandos
// que el execute devuelva un Result en vez de String
//Mover getCurrentPath a PwdCommand
