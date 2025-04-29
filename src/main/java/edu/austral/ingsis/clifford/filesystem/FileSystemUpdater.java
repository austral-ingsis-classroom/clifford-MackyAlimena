package edu.austral.ingsis.clifford.filesystem;

import edu.austral.ingsis.clifford.filesystem.node.DirectoryNode;
import edu.austral.ingsis.clifford.filesystem.node.FileSystemNode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static edu.austral.ingsis.clifford.filesystem.node.DirectoryNode.printChildren;

public class FileSystemUpdater {

    public static FileSystem replaceAndPropagate(FileSystem fs, DirectoryNode updatedNode) {
        System.out.println("=== replaceAndPropagate ===");
        System.out.println("Updated Node: " + updatedNode.name());

        // Si el updatedNode ya es root, no propagues nada
        if (updatedNode.getParent() == null) {
            System.out.println("Updated node is root, skipping propagation.");
            printChildren(updatedNode);
            return new FileSystem(updatedNode, updatedNode);
        }

        DirectoryNode updatedRoot = propagateUpdate(updatedNode);
        System.out.println("Updated Root After Propagation: " + updatedRoot.name());
        printChildren(updatedRoot);
        return new FileSystem(updatedRoot, updatedNode);
    }

    private static DirectoryNode propagateUpdate(DirectoryNode node) {
        DirectoryNode parent = node.getParent();
        if (parent == null) {
            System.out.println("Reached root during propagation: " + node.name());
            System.out.println("Root children now: " + node.getChildren().stream().map(FileSystemNode::name).toList());
            return node;
        }

        DirectoryNode updatedParent = parent.removeChild(node.name()).addChild(node);

        System.out.println("Propagating update - Parent: " + parent.name());
        System.out.println("Parent children before: " + parent.getChildren().stream().map(FileSystemNode::name).toList());
        System.out.println("Parent children after update: " + updatedParent.getChildren().stream().map(FileSystemNode::name).toList());

        return propagateUpdate(updatedParent);
    }

}

