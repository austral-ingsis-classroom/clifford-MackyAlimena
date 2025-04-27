package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;

public class FileSystem {
    private final DirectoryNode root;
    private final DirectoryNode current;

    public FileSystem(DirectoryNode root, DirectoryNode current) {
        this.root = root;
        this.current = current;
    }

    public static FileSystem init() {
        DirectoryNode rootNode = new DirectoryNode("/", null, java.util.Collections.emptyList());
        return new FileSystem(rootNode, rootNode);
    }

    public DirectoryNode getCurrent() {
        return current;
    }

    public DirectoryNode getRoot() {
        return root;
    }

    public FileSystem setCurrent(DirectoryNode newCurrent) {
        return new FileSystem(this.root, newCurrent);
    }
}

// Parseo de comandos
// que el execute devuelva un Result en vez de String
