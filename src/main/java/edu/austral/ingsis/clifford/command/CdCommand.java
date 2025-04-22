package edu.austral.ingsis.clifford.command;

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
            return "Already in root directory";
        } else {
            fileSystem.setCurrent(parent);
            return "Moved to directory: '" + parent.getName() + "'";
        }
    }

    private String handleCurrentDirectory(FileSystem fileSystem) {
        return "Moved to directory: '" + fileSystem.getCurrent().getName() + "'";
    }

    private String handleRootPath(FileSystem fileSystem, String argument) {
        String path = argument.substring(1);
        if (path.isEmpty()) {
            fileSystem.setCurrent(fileSystem.getRoot());
            return "Moved to directory: '/'";
        }
        Optional<FileSystemNode> nodeOpt = fileSystem.getRoot().getChild(path);
        return validateAndMove(fileSystem, nodeOpt);    }

    private String validateAndMove(FileSystem fileSystem, Optional<FileSystemNode> nodeOpt) {
        if (nodeOpt.isEmpty()){
            return "Directory not found";
        }
        FileSystemNode node = nodeOpt.get();
        if (!node.isDirectory()){
            return "Cannot move to file";
        }
        fileSystem.setCurrent((DirectoryNode) node);
        return "Moved to directory: '" + node.name() + "'";
    }

    private String handleChildDirectory(FileSystem fileSystem, String argument) {
        Optional<FileSystemNode> nodeOpt = fileSystem.getCurrent().getChild(argument);
        return validateAndMove(fileSystem, nodeOpt);
    }
}
