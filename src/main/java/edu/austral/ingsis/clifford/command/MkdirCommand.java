package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import edu.austral.ingsis.clifford.filesystem.node.FileNode;

public class MkdirCommand implements Command {
    @Override
    public String execute(FileSystem fileSystem, String argument) {
        if (!isValidName(argument)) {
            return "Invalid directory name";
        }

        DirectoryNode currentDir = fileSystem.getCurrent();

        if (alreadyExists(currentDir, argument)) {
            return "Directory already exists";
        }

        DirectoryNode newDirectory = new DirectoryNode(argument, currentDir);
        currentDir.addChild(newDirectory);

        return "'" + argument + "' directory created";
    }

    private boolean isValidName(String name) {
        return name != null && !name.contains("/") && !name.contains(" ");
    }

    private boolean alreadyExists(DirectoryNode directory, String name) {
        return directory.hasChild(name);
    }
}
