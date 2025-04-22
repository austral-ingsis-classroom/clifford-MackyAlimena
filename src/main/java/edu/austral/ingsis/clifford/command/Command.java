package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.FileSystem;

public interface Command {
    String execute(FileSystem fileSystem,String argument);
}
