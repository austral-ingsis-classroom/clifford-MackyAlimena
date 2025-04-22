package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import edu.austral.ingsis.clifford.filesystem.node.FileSystemNode;

import java.util.Optional;

public class RmCommand implements Command {
    @Override
    public String execute(FileSystem fileSystem, String argument) {
        DirectoryNode currentDirectory = fileSystem.getCurrent();

        if (argument.startsWith("--recursive")) {
            String childName = argument.substring(12).trim();
            if (currentDirectory.hasChild(childName)) {
                currentDirectory.removeChild(childName);
                return "'" + childName + "' removed";
            } else {
                return "File or directory not found";
            }
        }

        Optional<FileSystemNode> possibleChild = currentDirectory.getChild(argument);
        if (possibleChild.isPresent()) {
            FileSystemNode child = possibleChild.get();
            if (child.isDirectory()) {
                return "Cannot remove '" + argument + "', is a directory";
            }
            currentDirectory.removeChild(argument);
            return "'" + argument + "' removed";
        }
        return "File or directory not found";
    }
}
