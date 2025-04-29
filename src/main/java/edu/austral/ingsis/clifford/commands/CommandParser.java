package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.commands.types.*;
import edu.austral.ingsis.clifford.filesystem.FileSystem;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private FileSystem fileSystem;
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandParser() {
        this.fileSystem = FileSystem.init();
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

    public String parse(String input) {
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
            return "Command not found";
        }

        Result result = command.execute(fileSystem, argument);
        this.fileSystem = result.getFileSystem();
        return result.getMessage();
    }
}
