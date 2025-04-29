package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.*;

import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import edu.austral.ingsis.clifford.filesystem.node.FileSystemNode;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileSystemTests {

  private DirectoryNode root;
  private FileSystem fileSystem;

  @BeforeEach
  void setUp() {
    root = new DirectoryNode("/", null, Collections.emptyList());
    fileSystem = new FileSystem(root, root);
  }

  @Test
  void givenEmptyRoot_whenAddingChild_thenChildIsPresent() {
    DirectoryNode child = new DirectoryNode("child", root, Collections.emptyList());
    DirectoryNode updatedRoot = root.addChild(child);

    assertTrue(updatedRoot.hasChild("child"));
    assertEquals(1, updatedRoot.getChildren().size());
  }

  @Test
  void givenDirectoryWithChild_whenRemovingChild_thenChildIsAbsent() {
    DirectoryNode child = new DirectoryNode("child", root, Collections.emptyList());
    DirectoryNode updatedRoot = root.addChild(child);

    DirectoryNode finalRoot = updatedRoot.removeChild("child");

    assertFalse(finalRoot.hasChild("child"));
    assertEquals(0, finalRoot.getChildren().size());
  }

  @Test
  void givenNestedDirectories_whenGettingChildByPath_thenCorrectNodeIsReturned() {
    DirectoryNode child = new DirectoryNode("child", root, Collections.emptyList());
    DirectoryNode grandChild = new DirectoryNode("grandChild", child, Collections.emptyList());
    DirectoryNode updatedChild = child.addChild(grandChild);
    DirectoryNode updatedRoot = root.addChild(updatedChild);

    Optional<FileSystemNode> retrieved = updatedRoot.getChild("child/grandChild");

    assertTrue(retrieved.isPresent());
    assertEquals("grandChild", retrieved.get().name());
  }

  @Test
  void givenEmptyDirectory_whenGetChildNonExistent_thenEmptyOptional() {
    Optional<FileSystemNode> retrieved = root.getChild("nonexistent");

    assertTrue(retrieved.isEmpty());
  }
}
