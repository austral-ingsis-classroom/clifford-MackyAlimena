package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;

public class FileSystemUpdater {

  public static FileSystem replaceAndPropagate(FileSystem fs, DirectoryNode updatedNode) {

    // Si el updatedNode ya es root, no propagues nada
    if (updatedNode.getParent() == null) {

      return new FileSystem(updatedNode, updatedNode);
    }

    DirectoryNode updatedRoot = propagateUpdate(updatedNode);

    return new FileSystem(updatedRoot, updatedNode);
  }

  private static DirectoryNode propagateUpdate(DirectoryNode node) {
    DirectoryNode parent = node.getParent();
    if (parent == null) {

      return node;
    }

    DirectoryNode updatedParent = parent.removeChild(node.name()).addChild(node);

    return propagateUpdate(updatedParent);
  }
}
