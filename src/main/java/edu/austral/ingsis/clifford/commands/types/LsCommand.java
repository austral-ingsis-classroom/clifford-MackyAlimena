package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.node.FileSystemNode;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LsCommand implements Command {
    @Override
    public String execute(FileSystem fileSystem, String argument) {
        List<FileSystemNode> children = fileSystem.getCurrent().getChildren();

        if (children.isEmpty()) return "";

        // Parse the argument for sorting order
        if ("--ord=asc".equals(argument)) {
            children.sort(Comparator.naturalOrder());
        } else if ("--ord=desc".equals(argument)) {
            children.sort(Comparator.reverseOrder());
        }
        // Else, return in the creation order
        return children.stream()
                .map(FileSystemNode::name)
                .collect(Collectors.joining(" "));
    }
}
