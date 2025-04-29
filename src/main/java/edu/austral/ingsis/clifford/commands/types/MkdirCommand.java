package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.FileSystemUpdater;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import java.util.Collections;

public class MkdirCommand implements Command {
  @Override
  public Result execute(FileSystem fileSystem, String argument) {
    if (!isValidName(argument)) {
      return Result.error(fileSystem, "Invalid directory name");
    }

    DirectoryNode currentDir = fileSystem.getCurrent();

    if (currentDir.hasChild(argument)) {
      return Result.error(fileSystem, "Directory already exists");
    }

    DirectoryNode newDirectory = new DirectoryNode(argument, currentDir, Collections.emptyList());
    DirectoryNode updatedCurrentDir = currentDir.addChild(newDirectory);

    // Propagar cambios y actualizar FileSystem completo.
    FileSystem updatedFileSystem =
        FileSystemUpdater.replaceAndPropagate(fileSystem, updatedCurrentDir);

    // Aseguramos que el current sea el directorio actualizado (con el nuevo hijo)
    updatedFileSystem = new FileSystem(updatedFileSystem.getRoot(), updatedCurrentDir);

    updatedFileSystem
        .getRoot()
        .getChildren()
        .forEach(child -> System.out.println("  - " + child.name()));

    return Result.success(updatedFileSystem, "'" + argument + "' directory created");
  }

  private boolean isValidName(String name) {
    return name != null && !name.contains("/") && !name.contains(" ");
  }
}
