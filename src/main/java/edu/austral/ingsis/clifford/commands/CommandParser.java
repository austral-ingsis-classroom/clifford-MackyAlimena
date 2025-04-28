package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.commands.result.Result;
import edu.austral.ingsis.clifford.commands.types.*;
import edu.austral.ingsis.clifford.filesystem.FileSystem;

public class CommandParser {
    private FileSystem fileSystem;

    public CommandParser() {
        this.fileSystem = FileSystem.init();
    }
    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public String parse(String argument) {
        int endIndex = argument.indexOf(" ");
        String commandArgument;
        String commandInput;

        if (endIndex == -1) {
            commandArgument = argument;
            commandInput = "";
        } else {
            commandArgument = argument.substring(0, endIndex);
            commandInput = argument.substring(endIndex + 1);
        }

        Command command;
        switch (commandArgument) {
            case "ls":
                command = new LsCommand();
                break;
            case "cd":
                command = new CdCommand();
                break;
            case "touch":
                command = new TouchCommand();
                break;
            case "mkdir":
                command = new MkdirCommand();
                break;
            case "rm":
                command = new RmCommand();
                break;
            case "pwd":
                command = new PwdCommand();
                break;
            default:
                return "Command not found";
        }

        Result result = command.execute(fileSystem, commandInput);
        this.fileSystem = result.getFileSystem();
        return result.getMessage();
    }
}
