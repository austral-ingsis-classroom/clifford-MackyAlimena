package edu.austral.ingsis;

import edu.austral.ingsis.clifford.commands.CommandParser;

import java.util.ArrayList;
import java.util.List;

public class CommandRunner implements FileSystemRunner {
    private final CommandParser parser = new CommandParser();

    @Override
    public List<String> executeCommands(List<String> commands) {
        List<String> result = new ArrayList<>(commands.size());
        for (String command : commands) {
            result.add(parser.parse(command));
        }
        return result;
    }
}

