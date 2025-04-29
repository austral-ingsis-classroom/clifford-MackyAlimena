package edu.austral.ingsis;

import edu.austral.ingsis.clifford.commands.CommandParser;
import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.filesystem.FileSystem;

import java.util.ArrayList;
import java.util.List;

public class CommandRunner implements FileSystemRunner {
  private CommandParser parser = new CommandParser();

  @Override
  public List<String> executeCommands(List<String> commands) {
    List<String> result = new ArrayList<>(commands.size());
    for (String command : commands) {
        Result  parseCommand= parser.parse(command);
        FileSystem fs = parseCommand.getFileSystem();
        String message = parseCommand.getMessage();
        parser = new CommandParser(fs);
        result.add(message);
    }
    return result;
  }
}
