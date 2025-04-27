package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import edu.austral.ingsis.clifford.filesystem.node.FileNode;

public class TouchCommand implements Command {
    @Override
    public Result execute(FileSystem fileSystem, String argument) {
        if (!isValidName(argument)) {
            return Result.error(fileSystem, "Invalid file name");
        }

        DirectoryNode currentDir = fileSystem.getCurrent();

        if (alreadyExists(currentDir, argument)) {
            return Result.error(fileSystem, "File already exists");
        }

        FileNode newFile = new FileNode(argument);
        DirectoryNode updatedDir = currentDir.addChild(newFile);
        FileSystem updatedFs = fileSystem.setCurrent(updatedDir);

        return Result.success(updatedFs, "'" + argument + "' file created");
    }

    private boolean isValidName(String name) {
        return name != null && !name.contains("/") && !name.contains(" ");
    }

    private boolean alreadyExists(DirectoryNode currentDir, String argument) {
        return currentDir.hasChild(argument);
    }
}
