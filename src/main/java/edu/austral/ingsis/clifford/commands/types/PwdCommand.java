package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;
import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PwdCommand implements Command {
    @Override
    public Result execute(FileSystem fileSystem, String argument) {
        DirectoryNode dir = fileSystem.getCurrent();
        List<String> pathParts = new ArrayList<>();

        while (dir != null && dir.getParent() != null) {
            pathParts.add(dir.name());
            dir = dir.getParent();
        }

        Collections.reverse(pathParts);
        String path = "/" + String.join("/", pathParts);
        return Result.success(fileSystem, path);
    }
}
