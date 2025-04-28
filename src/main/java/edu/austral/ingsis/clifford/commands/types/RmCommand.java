package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.FileSystemUpdater;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import edu.austral.ingsis.clifford.filesystem.node.FileSystemNode;

import java.util.Optional;

public class RmCommand implements Command {
    @Override
    public Result execute(FileSystem fileSystem, String argument) {
        DirectoryNode currentDirectory = fileSystem.getCurrent();

        if (argument.startsWith("--recursive")) {
            String childName = argument.substring(12).trim();
            if (currentDirectory.hasChild(childName)) {
                DirectoryNode updatedDir = currentDirectory.removeChild(childName);
                FileSystem updatedFS = FileSystemUpdater.replaceAndPropagate(fileSystem, updatedDir);
                return Result.success(updatedFS, "'" + childName + "' removed");
            } else {
                return Result.error(fileSystem, "file not found");
            }
        }

        Optional<FileSystemNode> possibleChild = currentDirectory.getChild(argument);
        if (possibleChild.isPresent()) {
            FileSystemNode child = possibleChild.get();
            if (child.isDirectory()) {
                return Result.error(fileSystem, "cannot remove '" + argument + "', is a directory");
            }
            DirectoryNode updatedDir = currentDirectory.removeChild(argument);
            FileSystem updatedFS = FileSystemUpdater.replaceAndPropagate(fileSystem, updatedDir);
            return Result.success(updatedFS, "'" + argument + "' removed");
        }
        return Result.error(fileSystem, "file not found");
    }
}
