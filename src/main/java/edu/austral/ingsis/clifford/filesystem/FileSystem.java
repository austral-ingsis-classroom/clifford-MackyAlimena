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

  // Returns the full path from root to the current directory
  public String getCurrentPath() {
    DirectoryNode dir = current;
    List<String> pathParts = new ArrayList<>();

    // Traverses upward to root, collecting each directory name
    while (dir != null && dir.getParent() != null) {
      pathParts.add(dir.getName());
      dir = dir.getParent();
    }

    // Reverses the list to build the path root - current
    Collections.reverse(pathParts);

    // Joins the parts with / into a string
    return "/" + String.join("/", pathParts);
  }
}
