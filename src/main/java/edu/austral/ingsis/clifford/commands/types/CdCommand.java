package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.commands.result.ResultType;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import edu.austral.ingsis.clifford.filesystem.node.FileSystemNode;
import java.util.Optional;

public class CdCommand implements Command {
    @Override
    public Result execute(FileSystem fileSystem, String argument) {
        if (argument.equals("..")) {
            return handleParentDirectory(fileSystem);
        } else if (argument.equals(".")) {
            return Result.info(fileSystem, "moved to directory '" + fileSystem.getCurrent().getName() + "'");
        } else if (argument.startsWith("/")) {
            return handleRootPath(fileSystem, argument);
        } else {
            return handleChildDirectory(fileSystem, argument);
        }
    }

    private Result handleParentDirectory(FileSystem fileSystem) {
        DirectoryNode parent = fileSystem.getCurrent().getParent();
        if (parent == null) {
            return Result.success(fileSystem.setCurrent(fileSystem.getRoot()), "moved to directory '/'");
        } else {
            return Result.success(fileSystem.setCurrent(parent), "moved to directory '" + parent.getName() + "'");
        }
    }

    private Result handleRootPath(FileSystem fileSystem, String argument) {
        String[] parts = argument.split("/");
        DirectoryNode current = fileSystem.getRoot();

        for (String part : parts) {
            if (part.isEmpty()) continue;
            Optional<FileSystemNode> childOpt = current.getChild(part);

            Result result = validateAndMove(fileSystem, childOpt, argument);
            if (result.getType() == ResultType.ERROR) {
                return result;
            }
            current = (DirectoryNode) childOpt.get();
        }

        return Result.success(fileSystem.setCurrent(current),
                "moved to directory '" + (current == fileSystem.getRoot() ? "/" : current.getName()) + "'");
    }


    private Result validateAndMove(FileSystem fileSystem, Optional<FileSystemNode> nodeOpt, String argument) {
        if (nodeOpt.isEmpty()) {
            return Result.error(fileSystem, "'" + argument + "' directory does not exist");
        }
        FileSystemNode node = nodeOpt.get();
        if (!node.isDirectory()) {
            return Result.error(fileSystem, "cannot move to file");
        }
        DirectoryNode newCurrent = (DirectoryNode) node;
        return Result.success(fileSystem.setCurrent(newCurrent), "moved to directory '" + node.name() + "'");
    }

    private Result handleChildDirectory(FileSystem fileSystem, String argument) {
        Optional<FileSystemNode> nodeOpt = fileSystem.getCurrent().getChild(argument);
        return validateAndMove(fileSystem, nodeOpt, argument);
    }
}
