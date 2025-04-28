package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.FileSystemUpdater;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import edu.austral.ingsis.clifford.filesystem.node.FileNode;

public class TouchCommand implements Command {
    @Override
    public Result execute(FileSystem fileSystem, String argument) {
        if (!isValidName(argument)) {
            return Result.error(fileSystem, "Invalid file name");
        }

        DirectoryNode currentDir = fileSystem.getCurrent();

        if (currentDir.hasChild(argument)) {
            return Result.error(fileSystem, "File already exists");
        }

        FileNode newFile = new FileNode(argument);
        DirectoryNode updatedCurrentDir = currentDir.addChild(newFile);

        FileSystem updatedFileSystem = FileSystemUpdater.replaceAndPropagate(fileSystem, updatedCurrentDir);

        return Result.success(updatedFileSystem, "'" + argument + "' file created");
    }

    private boolean isValidName(String name) {
        return name != null && !name.contains("/") && !name.contains(" ");
    }
}
