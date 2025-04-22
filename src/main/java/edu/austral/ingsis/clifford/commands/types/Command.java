package edu.austral.ingsis.clifford.commands.types;

import edu.austral.ingsis.clifford.filesystem.FileSystem;

public interface Command {
  String execute(FileSystem fileSystem, String argument);
}
