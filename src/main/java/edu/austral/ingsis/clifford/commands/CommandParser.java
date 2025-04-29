package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.commands.types.*;
import edu.austral.ingsis.clifford.filesystem.FileSystem;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private final FileSystem fileSystem;
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandParser() {
        this.fileSystem = FileSystem.init();
        initializeCommands();
    }

    public CommandParser(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        initializeCommands();
    }

    private void initializeCommands() {
        commandMap.put("ls", new LsCommand());
        commandMap.put("cd", new CdCommand());
        commandMap.put("touch", new TouchCommand());
        commandMap.put("mkdir", new MkdirCommand());
        commandMap.put("rm", new RmCommand());
        commandMap.put("pwd", new PwdCommand());
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public Result parse(String input) {
        int spaceIndex = input.indexOf(" ");
        String commandName;
        String argument;

        if (spaceIndex == -1) {
            commandName = input;
            argument = "";
        } else {
            commandName = input.substring(0, spaceIndex);
            argument = input.substring(spaceIndex + 1);
        }

        Command command = commandMap.get(commandName);
        if (command == null) {
            return Result.error(fileSystem,"Command not found");
        }

        return command.execute(fileSystem, argument);

    }
}
