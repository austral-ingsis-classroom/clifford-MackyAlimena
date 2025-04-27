package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;

import java.util.List;


public class MkdirCommand implements Command {
    @Override
    public Result execute(FileSystem fileSystem, String argument) {
        if (!isValidName(argument)) {
            return Result.error(fileSystem, "Invalid directory name");
        }

        DirectoryNode currentDir = fileSystem.getCurrent();

        if (alreadyExists(currentDir, argument)) {
            return Result.error(fileSystem, "Directory already exists");
        }

        DirectoryNode newDirectory = new DirectoryNode(argument, currentDir, List.of());
        DirectoryNode updatedDir = currentDir.addChild(newDirectory);
        FileSystem updatedFs = fileSystem.setCurrent(updatedDir);

        return Result.success(updatedFs, "'" + argument + "' directory created");
    }

    private boolean isValidName(String name) {
        return name != null && !name.contains("/") && !name.contains(" ");
    }

    private boolean alreadyExists(DirectoryNode directory, String name) {
        return directory.hasChild(name);
    }
}


