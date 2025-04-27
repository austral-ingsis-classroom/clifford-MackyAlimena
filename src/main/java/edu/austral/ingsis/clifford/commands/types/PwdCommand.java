package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PwdCommand implements Command {
    @Override
    public String execute(FileSystem fileSystem, String argument) {
        DirectoryNode dir = fileSystem.getCurrent();
        List<String> pathParts = new ArrayList<>();

        while (dir != null && dir.getParent() != null) {
            pathParts.add(dir.getName());
            dir = dir.getParent();
        }

        Collections.reverse(pathParts);

        return "/" + String.join("/", pathParts);
    }
}
