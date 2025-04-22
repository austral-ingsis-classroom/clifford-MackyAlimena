package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import edu.austral.ingsis.clifford.filesystem.node.FileSystemNode;

import java.util.Optional;

public class CdCommand implements Command{
    @Override
    public String execute(FileSystem fileSystem, String argument) {
        if (argument.equals("..")) {
            return handleParentDirectory(fileSystem);
        } else if (argument.equals(".")) {
            return handleCurrentDirectory(fileSystem);
        } else if (argument.startsWith("/")) {
            return handleRootPath(fileSystem, argument);
        } else {
            return handleChildDirectory(fileSystem, argument);
        }
    }


    private String handleParentDirectory(FileSystem fileSystem) {
        DirectoryNode parent  = fileSystem.getCurrent().getParent();
        if (parent == null){
            return "moved to directory '/'";
        } else {
            fileSystem.setCurrent(parent);
            return "moved to directory '" + parent.getName() + "'";
        }
    }

    private String handleCurrentDirectory(FileSystem fileSystem) {
        return "moved to directory '" + fileSystem.getCurrent().getName() + "'";
    }

    private String handleRootPath(FileSystem fileSystem, String argument) {
        String[] parts = argument.split("/");
        DirectoryNode current = fileSystem.getRoot();
        for (String part : parts) {
            if (part.isEmpty()) continue;
            Optional<FileSystemNode> childOpt = current.getChild(part);
            if (childOpt.isEmpty() || !childOpt.get().isDirectory()) {
                return "'" + argument + "' directory does not exist";
            }
            current = (DirectoryNode) childOpt.get();
        }
        fileSystem.setCurrent(current);
        return "moved to directory '" + (current == fileSystem.getRoot() ? "/" : current.getName()) + "'";
    }

    private String validateAndMove(FileSystem fileSystem, Optional<FileSystemNode> nodeOpt, String argument) {
        if (nodeOpt.isEmpty()){
            return "'" + argument + "' directory does not exist";
        }
        FileSystemNode node = nodeOpt.get();
        if (!node.isDirectory()){
            return "cannot move to file";
        }
        fileSystem.setCurrent((DirectoryNode) node);
        return "moved to directory '" + node.name() + "'";
    }

    private String handleChildDirectory(FileSystem fileSystem, String argument) {
        Optional<FileSystemNode> nodeOpt = fileSystem.getCurrent().getChild(argument);
        return validateAndMove(fileSystem, nodeOpt, argument);
    }
}
