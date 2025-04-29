package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.FileSystemUpdater;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import edu.austral.ingsis.clifford.filesystem.node.FileSystemNode;
import java.util.Optional;

public class CdCommand implements Command {
  @Override
  public Result execute(FileSystem fileSystem, String argument) {
    if (argument.equals("..")) {
      return handleParentDirectory(fileSystem);
    } else if (argument.equals(".")) {
      return Result.info(fileSystem, "moved to directory '" + fileSystem.getCurrent().name() + "'");
    } else if (argument.startsWith("/")) {
      return handleRootPath(fileSystem, argument);
    } else {
      return handleChildDirectory(fileSystem, argument);
    }
  }

  private Result handleParentDirectory(FileSystem fileSystem) {
    DirectoryNode parent = fileSystem.getCurrent().getParent();
    if (parent == null) {
      return Result.success(
          new FileSystem(fileSystem.getRoot(), fileSystem.getRoot()), "moved to directory '/'");
    } else {
      FileSystem updatedFs =
          FileSystemUpdater.replaceAndPropagate(fileSystem, fileSystem.getCurrent());
      updatedFs = new FileSystem(updatedFs.getRoot(), parent);
      return Result.success(updatedFs, "moved to directory '" + parent.name() + "'");
    }
  }

  private Result handleRootPath(FileSystem fileSystem, String argument) {
    String[] parts = argument.split("/");
    DirectoryNode current = fileSystem.getRoot();
    for (String part : parts) {
      if (part.isEmpty()) continue;
      Optional<FileSystemNode> childOpt = current.getChild(part);
      if (childOpt.isEmpty() || !childOpt.get().isDirectory()) {
        return Result.error(fileSystem, "'" + argument + "' directory does not exist");
      }
      current = (DirectoryNode) childOpt.get();
    }
    FileSystem updatedFs = FileSystemUpdater.replaceAndPropagate(fileSystem, current);
    return Result.success(
        updatedFs,
        "moved to directory '" + (current == fileSystem.getRoot() ? "/" : current.name()) + "'");
  }

  private Result handleChildDirectory(FileSystem fileSystem, String argument) {
    Optional<FileSystemNode> nodeOpt = fileSystem.getCurrent().getChild(argument);
    if (nodeOpt.isEmpty()) {
      return Result.error(fileSystem, "'" + argument + "' directory does not exist");
    }
    FileSystemNode node = nodeOpt.get();
    if (!node.isDirectory()) {
      return Result.error(fileSystem, "cannot move to file");
    }
    DirectoryNode newCurrent = (DirectoryNode) node;
    FileSystem updatedFs = FileSystemUpdater.replaceAndPropagate(fileSystem, newCurrent);
    return Result.success(updatedFs, "moved to directory '" + node.name() + "'");
  }
}
