package edu.austral.ingsis.clifford.command;

import edu.austral.ingsis.clifford.filesystem.FileSystem;

public class PwdCommand implements Command {
    @Override
    public String execute(FileSystem fileSystem, String argument) {
        return fileSystem.getCurrentPath();
    }
}
