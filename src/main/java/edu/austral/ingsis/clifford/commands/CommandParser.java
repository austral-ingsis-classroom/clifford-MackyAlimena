package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.commands.types.CdCommand;
import edu.austral.ingsis.clifford.commands.types.Command;
import edu.austral.ingsis.clifford.commands.types.LsCommand;
import edu.austral.ingsis.clifford.commands.types.TouchCommand;
import edu.austral.ingsis.clifford.commands.types.MkdirCommand;
import edu.austral.ingsis.clifford.commands.types.RmCommand;
import edu.austral.ingsis.clifford.commands.types.PwdCommand;
import edu.austral.ingsis.clifford.filesystem.FileSystem;

public class CommandParser {
    private final FileSystem fileSystem;

    public CommandParser() {
        this.fileSystem = new FileSystem();
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

        return command.execute(fileSystem, commandInput);
    }
}

